## Currency Converter

A currency conversion application built with Spring Boot and deployed to EC2


#### 1. Deployment details

| Service | Address |
| ------- |:-------:|
| Spring Boot App | http://44.193.212.220:8080 |
| Prometheus | http://44.193.212.220:9090 |
| Grafana | http://44.193.212.220:3000 |

#### 2. Requirements checklist -

| Description | Status | Comments |
|:-----------:|:------:|:--------:|
| Basic UI | ✔️ | Deployed at http://44.193.212.220:8080 |
| REST endpoint with `source`, `target`, `amount` as params | ✔️ | Example - `/api/convert?source=EUR&target=USD&amount=100` |
| Leverage ExchangeRates API | ✔️ | https://exchangeratesapi.io/ |
| Validation of input | ✔️ | - |
| Unit & Integration Tests | ✔️ | - |
| Cache responses from https://exchangerates.io | ✔️ | Used Caffeine with TTL of 1 min |
| Format result using Web i18n | ✔️ | - |
| Server-Timing header in response | ✔️ | - |
| CSRF and CSP | ✔️ | - |
| Deployed to EC2 | ✔️ | http://44.193.212.220:8080 |
| Add instrumentation and forward to Grafana | ✔️ | Dashboard - http://44.193.212.220:3000/d/hyXnxCi7k/jvm-micrometer |

#### 3. Run project locally

##### 3.1 Quick run from Docker Hub

```
docker run -p 8080:8080 hiscodesmells/nosto-currency-converter:latest
```
Note: This command only runs the Spring Boot application

##### 3.2 Complete setup (Spring Boot + Prometheus + Grafana)

```
git clone https://github.com/hiscodesmells/currency-converter.git
cd currency-converter
docker-compose up
```

| Service | Address |
| ------- |:-------:|
| Spring Boot App | http://localhost:8080 |
| Prometheus | http://localhost:9090 |
| Grafana Dashboard | http://localhost:3000 |
