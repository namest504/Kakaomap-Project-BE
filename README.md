# Kakaomap-BE 토이프로젝트

# **프로젝트 소개**

이 프로젝트는 카카오맵 API와 기상청 공공 API를 활용하여 사용자들에게 건물 정보 및 현 위치 정보와 날씨 정보를 제공하는 서비스 입니다.

# **구현 API**

| 기능                   | 설명                           |
|----------------------|------------------------------|
| 건물 정보 등록             | 건물 정보를 등록하는 기능               |
| 건물 정보 수정             | 특정 건물 정보 내용을 수정하는 기능         |
| 건물 정보 삭제             | 특정 건물 정보를 삭제하는 기능            |
| 건물 조회                | 건물의 이름을 검색할 수 있는 기능          |
| 건물 전체 조회             | DB에 저장된 건물 전체 조회 기능          |
| 현재 기준 6시간 날씨 전체 조회   | 기상청 공공 API로 초단기예보 조회         |
| 현재 기준 6시간 기온, 강수형태 조회 | 기상청 공공 API로 초단기예보 특정 카테고리 조회 |
| 현재 기준 최신 기온, 강수형태 조회 | 기상청 공공 API로 가장 최신 예보 조회      |

# **사용한 기술 스택**

언어 : JAVA

프레임워크 : Spring Boot, JPA, WebFlux(WebClient 사용)

데이터베이스 : H2, MariaDB

DevOps : AWS, Docker

라이브러리 : Lombok, H2, MariaDB JDBC, QueryDSL, Netty, JSON-Simple

# **설치 및 실행 방법**

## 로컬 서버 구축

```text
git clone https://github.com/namest504/Kakaomap-Project-BE.git

cd Kakaomap-Project-BE

./gradlew build

cd build/libs

java -jar kakaoMap-0.0.1-SNAPSHOT.jar
```

## 도커 배포 구축

EC2 - Amazon Linux 2023 기준 배포 환경
### Local 도커 이미지 빌드
```text
./gradlew build

docker build --platform linux/amd64 -t [도커 레포지토리 이름]/[이미지 이름] .

docker push [도커 레포지토리 이름]/[이미지 이름]
```
### EC2 도커 이미지 컨테이너화
```text
docker pull [도커 레포지토리 이름]/[이미지 이름]

docker pull mariadb

docker network create [네트워크 이름]

docker run --name [컨테이너 이름] -p 3306:3306 --network [네트워크 이름] -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=[DB 이름] -e MYSQL_USER=[DB 계정] -e MYSQL_PASSWORD=[DB 계정 비밀번호] -d mariadb

docker run -it -d --name [컨테이너 이름] --network [네트워크 이름] -p 8080:8080 [도커 레포지토리 이름]/[이미지 이름]
```

# **이미지**

<img width="1511" alt="Untitled" src="https://github.com/namest504/Kakaomap-Project-BE/assets/61047602/ccba833a-982e-4ea8-ac7e-ee7a1e0a4299">


---
