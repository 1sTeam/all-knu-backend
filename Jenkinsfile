pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS') // set timeout 1 hour
    }
    environment {
        REPOSITORY_CREDENTIAL_ID = 'all-knu-backend-jenkins-github-key' // github repository credential name
        REPOSITORY_URL = 'https://github.com/1sTeam/all-knu-backend.git'
        TARGET_BRANCH = 'main'
        IMAGE_NAME = 'all-knu-backend'
        CONTAINER_NAME = 'all-knu-backend'
        PROFILE = 'prod'
	DOCKER_NETWORK = 'haproxy-net'
    }
    stages{
        stage('init') {
            steps {
                echo 'init stage'
                sh '''
                docker rm -f $CONTAINER_NAME
                '''
                deleteDir()
            }
            post {
                success {
                    echo 'success init in pipeline'
                }
                failure {
                    error 'fail init in pipeline'
                    slackSend (channel: '#jenkins-notification', color: '#FF0000', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 실패, 젠킨스 확인 해주세요")
                }
            }
        }
        stage('clone project') {
            steps {
                git url: "$REPOSITORY_URL",
                    branch: "$TARGET_BRANCH",
                    credentialsId: "$REPOSITORY_CREDENTIAL_ID"
                sh "ls -al"
            }
            post {
                success {
                    echo 'success clone project'
                }
                failure {
                    error 'fail clone project' // exit pipeline
                    slackSend (channel: '#jenkins-notification', color: '#FF0000', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 실패, 젠킨스 확인 해주세요")
                }
            }
        }
	stage('create secret file by aws parameter store') {
		steps {
			dir('src/main/resources/secrets') {
				withAWSParameterStore(credentialsId: 'aws-all-knu',
               				path: "/all-knu/jwt/${env.PROFILE}",
               				naming: 'basename',
               				regionName: 'ap-northeast-2') {
                                writeFile file: 'jwt-secrets.properties', text: "${env.KEY}"
            		   }		
			}
           }
	   post {
		success {
		   echo 'success create secret file'
		}
		failure {
		   error 'fail create secret file'
		   slackSend (channel: '#jenkins-notification', color: '#FF0000', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 실패, 젠킨스 확인 해주세요")
		}
	   }
	}
	stage('create application-${env.PROFILE} properties by aws parameter store') {
    		steps {
    			dir('src/main/resources') {
    				withAWSParameterStore(credentialsId: 'aws-all-knu',
                   				path: "/all-knu/properties/${env.PROFILE}",
                   				naming: 'basename',
                   				regionName: 'ap-northeast-2') {
                                    writeFile file: "application-${env.PROFILE}.yml", text: "${env.BACKEND}"
                		   }
    		    }
            }
    	   post {
    		success {
    		   echo 'success create secret file'
    		}
    		failure {
    		   error 'fail create secret file'
    		   slackSend (channel: '#jenkins-notification', color: '#FF0000', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 실패, 젠킨스 확인 해주세요")
    		}
    	   }
    	}
	stage('building by maven') {
		steps{
		 sh '''
		 ./mvnw clean package -DskipTests
		 '''
		}
		post {
		  success {
		    echo 'success build'
		  }
		  failure {
		    error 'fail build'
		    slackSend (channel: '#jenkins-notification', color: '#FF0000', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 실패, 젠킨스 확인 해주세요")
		  }
		}
	}
        stage('dockerizing by Dockerfile') {
            steps {
                sh '''
                docker build -t $IMAGE_NAME .
                '''
            }
            post {
                success {
                    echo 'success dockerizing by Dockerfile'
                }
                failure {
                    error 'fail dockerizing by Dockerfile' // exit pipeline
                    slackSend (channel: '#jenkins-notification', color: '#FF0000', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 실패, 젠킨스 확인 해주세요")
                }
            }
        }
        stage('deploy') {
            steps {
                sh 'docker run --name $CONTAINER_NAME -e "SPRING_PROFILES_ACTIVE=$PROFILE" --net $DOCKER_NETWORK -d -t $IMAGE_NAME'
            }
            
            post {
                success {
                    echo 'success deploying all knu backend spring project'
                    slackSend (channel: '#jenkins-notification', color: '#FFFFFF', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 동작 성공")
                }
                failure {
                    error 'fail deploying all knu backend spring project' // exit pipeline
                    slackSend (channel: '#jenkins-notification', color: '#FF0000', message: "${env.CONTAINER_NAME} CI / CD 파이프라인 구동 실패, 젠킨스 확인 해주세요")
                }
            }
        }
        
    }

}
