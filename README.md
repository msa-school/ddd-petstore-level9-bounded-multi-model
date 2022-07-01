# ddd-petstore

## GitPod 접속
https://gitpod.io/#https://github.com/msa-school/ddd-petstore-level9-bounded-multi-model

- 접속 후 "Terminal > Terminal열기"

## 유틸리티 설치(httpie)

- httpie (curl / POSTMAN 대용)
```
pip install httpie
```

## Kafka 의 접속
### Docker Compose 이용 (도커 있을 때 강추)
- Kafka 의 실행 (Docker Compose)
```
cd kafka
docker-compose up -d     # docker-compose 가 모든 kafka 관련 리소스를 받고 실행할 때까지 기다림
```
- Kafka 정상 실행 확인
```
$ docker-compose logs kafka | grep -i started    

kafka-kafka-1  | [2022-04-21 22:07:03,262] INFO [KafkaServer id=1] started (kafka.server.KafkaServer)
```
- Kafka consumer 접속
```
docker-compose exec -it kafka /bin/bash   # kafka docker container 내부 shell 로 진입

[appuser@e23fbf89f899 bin]$ cd /bin
[appuser@e23fbf89f899 bin]$ ./kafka-console-consumer --bootstrap-server localhost:9092 --topic petstore
```

### 로컬 설치 (비추)
- Kafka Download
```
wget https://dlcdn.apache.org/kafka/3.1.0/kafka_2.13-3.1.0.tgz
tar -xf kafka_2.13-3.1.0.tgz
```

- Run Kafka
```
cd kafka_2.13-3.1.0/
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
```

- Kafka Event 컨슈밍하기 (별도 터미널)
```
cd kafka_2.13-3.1.0/
bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic petstore
```

## Pet Service 기동

- 새로운 터미널을 열어 Pet 서비스를 기동한다. (8081)
```
cd pet-domain
mvn install
mvn spring-boot:run
```
- 새로운 터미널을 열어 pet을 한 마리 등록해 줍니다.

```javasciprt
http :8081/cats name="몽이" energy=1
```

- Pet 에 먹이를 한번 줘봅니다.
```javascript
http PUT "http://localhost:8081/cats/1/feed"
```

- Pet 의 에너지가 상승함을 확인합니다.
```javascript
http "http://localhost:8081/cats/1"
```

- Pet 의 털도 한번 가꿔봅니다:
```javascript
http PUT "http://localhost:8081/cats/1/groom"
```


- Pet 의 외모지수가 상승함을 확인합니다.
```javascript
http "http://localhost:8081/cats/1"
```
<br>

## Store Service 기동

- 또 다른 터미널을 열어서 Store 서비스를 기동합니다. (8083)
```
cd store-domain
mvn spring-boot:run
```
- 유저를 한 명 등록해 줍니다.

```javascript
http localhost:8083/customers id="park@naver.com" address[zipcode]="123" address[detail]="용인"
```
![8083 유저 등록](https://user-images.githubusercontent.com/59447401/147196678-eab14a04-e885-4922-8b9c-9d8f3fdb6a9c.png)

<br>

## 입양하기
- items 에 Pet 도메인에서 등록된 애완동물들이 동기화 되어 있는 것을 확인한다:
```javascript
http :8083/items
```
- 자동으로 등록이 안되었다면 수동으로 등록을 해줘야 한다 (두마리를 등록해본다):
```javascript
http "http://localhost:8083/items" apperance=1 health=2 price[currency]="KR_WON" price[amount]="100000"
http "http://localhost:8083/items" apperance=2 health=1 price[currency]="EURO" price[amount]=200
```

- 가격을 매겨준다
```
http PATCH "http://localhost:8083/items/1" price[amount]=1000 price[currrency]=WON
```

- 아까 등록한 두마리를 입양해본다. 이때는 하나의 Aggregate 이므로 한번에 등록이 되어야 한다. 
```javascript
http :8083/cartItems customer="http://localhost:8083/customers/park@naver.com" items[]="http://localhost:8083/items/1" items[]="http://localhost:8083/items/2"
```
![8083 입양](https://user-images.githubusercontent.com/59447401/147196784-6281b8a0-822a-47cb-9ef1-7632b2d509be.png)





# Kafka Event 송출/수신과 관련된 파일들
## 설정관련
- pom.xml
- Application.java 의 @EnableBinding(KafkaProcessor.java)
- kafka/KafkaProcessor.java
- resources/application.yaml
## 구현관련
- AbstractEvent.java
- PolicyHandler.java
- domain event 들: PetReserved.java / PetUpdated.java



# Gateway 를 통한 진입점 통일(8080)

```
cd gateway
mvn spring-boot:run
```
서비스가 기동된 후, gateway 로 단일화된 주소로 접근이 가능함을 확인합니다:

```
http localhost:8088/pets         # service url of pet domain
http localhost:8088/cartItems    # service url of store domain
```

# Micro Frontends 를 통한 접근
## pet domain 의 front-end 실행
- 코드 다운로드
```
git clone https://github.com/msa-school/ddd-petstore-frontend-pet-domain
```
- Port 설정변경 (package.json)
```
    "serve": "vue-cli-service serve --port 8084",
```
- 실행
```
cd ddd-petstore-frontend-pet-domain/
npm i
npm run serve


# 성공시
 DONE  Compiled successfully in 12131ms                                                                                  11:19:42 PM


  App running at:
  - Local:   http://localhost:8084/ 
  - Network: http://10.0.5.2:8084/

  Note that the development build is not optimized.
  To create a production build, run npm run build.


```
- 브라우저에서 접속 확인시 "Invalid Host Header" 인 경우 (Gitpod 인 경우)

vue.config.js 설정 변경:
```
module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    allowedHosts: [
      '8080-msaschool-dddpetstorele-unxdrc0wqhx.ws-us41.gitpod.io',  // 접속 URL (gitpod 팝업 url, http:// 부분, 끝의 / 부분 제외)
    ],
  },
}
```
- Gateway 설정 (gateway/../application.yml)
```
        - id: pet-front
          uri: http://localhost:8084
          predicates:
            - Path=/**
```

## store domain 의 front-end 실행
```
git clone https://github.com/msa-school/ddd-petstore-frontend-store-domain
npm i
npm run serve
```
- Gateway 설정 (gateway/../application.yml)
```
        - id: store-front
          uri: http://localhost:8085
          predicates:
            - Path=/shop/**, /shop/js/
        - id: pet-front
          uri: http://localhost:8084
          predicates:
            - Path=/**
```

# Kubernetes 에 배포

# Docker 배포 관련

각 프로젝트 내에는 Dockerfile 이 포함되어있습니다. 이것을 빌드하기 위해서는 우선 maven 빌드로 jar 를 만들어준 후, jar 를 Dockerfile 로 다시 빌드해줍니다:

```
cd pet-domain
docker build -t <도커허브계정이름>/pet:vmvn package -B
1 .
docker run <도커허브계정이름>/pet:v1

docker login
docker push <도커허브계정이름>/pet:v1 
```


## kafka 설치하기

### Helm 

Helm(패키지 인스톨러) 설치
- Helm 3.x 설치(권장)
```bash
curl https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 > get_helm.sh
chmod 700 get_helm.sh
./get_helm.sh
```

### Kafka 를 helm 으로 설치
```bash
helm repo update
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install my-kafka bitnami/kafka
```



# 다른 도메인서비스가 없는 경우
```
docker-compose exec -it kafka /bin/bash
[appuser@01d073b35598 ~]$ cd /bin
[appuser@01d073b35598 bin]$ ./kafka-console-producer --bootstrap-server localhost:9092 --topic petstore

>{"eventType":"PetReserved","timestamp":1656637224644,"id":2,"appearance":0,"name":"멍이","type":"Cat","energy":1}
>{"eventType":"PetUpdated","timestamp":1656637577180,"id":2,"appearance":5,"name":"멍이","type":"Cat","energy":1}
```