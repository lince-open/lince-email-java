# lince-email-java
Microservice responsável pela centralização do envio de email no plataforma. Permite a criação de rascunhos e a adição/remoção incremental de anexos.

* Solução
* Autenticação com Spring Security e uso de Header
* Testes Unitários com JUnit/Spock/Groove
* Testes Funcionais com JUnit/Spock/Groove
* Swagger2
* Docker

![](https://github.com/lince-open/lince-email-java/workflows/Java%20CI/badge.svg)
[![Known Vulnerabilities](https://snyk.io/test/github/lince-open/lince-email-java/badge.svg)](https://snyk.io/test/github/pedrozatta/lince-email-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=lince-open_lince-email-java&metric=coverage)](https://sonarcloud.io/dashboard?id=lince-open_lince-email-java)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lince-open_lince-email-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=lince-open_lince-email-java)

## Docker Hub

https://hub.docker.com/repository/docker/linceopen/lince-email

mvn clean package dockerfile:build

docker run --name lince-email \
-e LINCE_EMAIL_SMTP_USER='user@gmail.com' \
-e LINCE_EMAIL_SMTP_PASS='pass' \
-e LINCE_JAVA_OPT='-Xms256m -Xmx256m' \
-p 8080:8080 \
-t lince-open/lince-email:latest

docker tag lince-open/lince-email:latest linceopen/lince-email:0.0.1

docker push linceopen/lince-email:0.0.1

docker tag lince-open/lince-email:latest linceopen/lince-email:latest

docker push linceopen/lince-email:latest

## Execução

export LINCE_EMAIL_SMTP_USER=user@gmail.com
export LINCE_EMAIL_SMTP_PASS=pass
mvn spring-boot:run

