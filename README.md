# all-knu-backend

## 기본 secret 환경 구성
### resources/secrets/jwt-secrets.properties
```properties
jwt.secret={secret}
```
### resources/secrets/personal-account-secrets.properties
```properties
knu.id={secret}
knu.password={secret}
```

### resources/secrets/api-endpoint-secrets.properties
```properties
제공받은 파일 주입
```

## gradle build
```bash
./gradlew bootJar -x test
```

## docker build
이미지 이름을 지정, java.security 이슈 때문에 지정된 자바 버전 사용..!
```bash
gradle build 후 진행
docker build -t all-knu-backend .
```

## docker run
프로파일을 지정하여 docker 컨테이너 생성
```bash
docker run -e "SPRING_PROFILES_ACTIVE=local" -d -p 8080:8080 -t all-knu-backend
```
### option
- 컨테이너 이름 지정 `--name`
- docker network 지정 `--net`
