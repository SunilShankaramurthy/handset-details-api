# handset-details-api

## Table of Contents

- [Description](#description)
- [Architecture](#architecture)
- [Endpoints and URL](#endpoints-and-url)
- [Supported Query Parameters by Search Service](#supported-query-parameters-by-search-service)
- [Response Codes](#response-codes)
- [Pre-requisite](#pre-requisite)
- [Local Build](#local-build)
- [Code Formatting](#code-formatting)
- [Run Junits](#run-junits)
- [Spring Profile](#spring-profile)
- [Local Run](#local-run)
- [Swagger Page](#swagger-page)


## Description
Rest interface that allows searching of handset details by providing required criteria as part of query parameters.

## Architecture

- This service developed using Spring Boot which allowed easy injection of dependent objects using Spring IOC. Internally uses MongoDB to fetch data upon case-insensitive search with provided search criteria.
Including a DB in design, allowed me to shift all the filtering out of java code.
- Seq-Flow pdf added [here](https://github.com/vishuu1101/handset-details-api/blob/master/Handset-Details-API-Seq-Flow.pdf) will explain briefly how api works.
<br/>

## Endpoints and URL

| Method | Endpoint       |
| ------ | -------------- |
| GET    | /mobile/search?|
<br/>

## Supported Query Parameters by Search Service

| Parameter Name |
| -------------- |
| announceDate   |
| audioJack      |
| battery        |
| brand          |
| gps            |
| id             |
| phone          |
| picture        |
| priceEur       |
| resolution     |
| sim            |

## Response Codes

| Response Code | Description                                                  |
| ------------- | ------------------------------------------------------------ |
| 200           | Successfully processed request and returned.                 |
| 400           | Provided search criteria isn't valid for the data available. |
| 500           | Internal Error in processing request.                        |

## Pre-requisite

- Java 8
- MongoDB either running locally or as a service running in cloud.(I have used (mlab)[https://mlab.com/] provided MongoDB service to implement this functionality.)

This service can run locally after checking out code from github using both **gradle <TASK>** and general **java -jar** as below. As gradle doc suggests, service is build using wrapper to ensure reliable and standardized execution of build. Depending of Operating system we can run service with gradle.bat or gradlew.

## Local Build

Building this API is as simple as running commands below

 `git clone https://github.com/vishuu1101/handset-details-api.git` <br/>
 `cd handset-details-api` <br/>
 `gradle.bat clean build`
 
## Code Formatting

This API implements [Spotless](https://github.com/diffplug/spotless) with [Google java Format](https://github.com/google/google-java-format) which helps in formatting the source code in google java style.
Spotless can be executed manually with the following command:

   `gradle.bat spotlessApply`
   
## Run Junits

When we try to build jar using `gradle.bat clean build` it runs spotless check, junits, code coverage along with prepare archive. But, if we want to 
run JUnits, then use below command(Please note as this will internally call `jacocoTestReport` to generate code coverage report).

`gradle.bat test`

Reports for tests can be found under `build/reports/tests/test` and jacoco code coverage under  `build/reports/jacoco/test/html`

## Spring Profile

Implemented to use Spring Profile capability to map our beans to different profiles based on different environments. In this API, it uses local and prod profiles and other profiles can be added as required.  

## Local Run
Running below commands start the service on port 8080 by default which can be changed if required.

- gradle

 `gradle.bat bootRun -Pprofile=local -Pport=8081`
 
- java -jar
 
  After building the jar navigate to build/libs directory and run the following command

 `java -jar -Dspring.profiles.active=local -Dserver.port=8081 handset-details-api.jar`

## Swagger Page 

Once the API is up and running you can find Swagger JSON document http://`HOST_NAME`:`PORT`/v2/api-docs


