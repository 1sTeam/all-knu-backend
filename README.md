# all-knu-backend

## maven run
프로파일과 실행 포트 지정
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local -Dspring-boot.run.jvmArguments='-Dserver.port=8080'
```

## maven build
```bash
./mvnw clean package
```
### option
테스트 스킵
```bash
-DskipTests
```

## docker build
이미지 이름을 지정, java.security 이슈 때문에 지정된 자바 버전 사용..!
```bash
maven build 후 진행
docker build -t all-knu-backend .
```

## docker run
프로파일을 지정하여 docker 컨테이너 생성
```bash
docker run -e "SPRING_PROFILES_ACTIVE=local" -d -p 8080:8080 -t all-knu-backend
```
