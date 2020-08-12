# handset-details-api

## Table of Contents

- [Description](#description)
- [Architecture and Pattern](#architecture-and-pattern)
- [Endpoints and URL](#endpoints-and-url)
- [Response Codes](#response-codes)
- [Pre-requisite](#pre-requisite)
- [Local Build](#local-build)
- [Code Formatting](#code-formatting)
- [Run Junits](#run-junits)
- [Spring Profile](#spring-profile)
- [Local Run](#local-run)
- [Swagger Page](#swagger-page)
- [Supported Query Parameters by Search Service](#supported-query-parameters-by-search-service)


## Description
Rest interface that allows searching of handset details by providing required criteria as part of query parameters.

## Architecture and Pattern

- This service developed using Spring Boot which allows easy injection of dependent objects.

<br/>

## Endpoints and URL

| Method | Endpoint       |
| ------ | -------------- |
| GET    | /mobile/search?|

<br/>

## Response Codes

| Response Code | Description                                                  |
| ------------- | ------------------------------------------------------------ |
| 200           | Successfully processed request and returned.                 |
| 400           | Provided search criteria isn't valid for the data available. |
| 500           | Internal Error in processing request.                        |

## Pre-requisite

- Java 8

This service can run locally after checking out code from github using both **gradle <TASK>** and general **java -jar** as below. As gradle doc suggests, service is build using wrapper to ensure reliable and standardized execution of build. Depending of Operating system we can run serice with gradle.bat or gradlew.

## Local Build

Building this API is as simple as running commands below

 `git clone https://github.com/vishuu1101/handset-details-api.git` <br/>
 `cd handset-details-api`
 `gradle.bat clean build`
 
## Code Formatting

This API implements [Spotless](https://github.com/diffplug/spotless) with [Google java Format](https://github.com/google/google-java-format) which helps in formatting the source code in google java style.
Spotless can be executed manually with the following command:

   `gradle.bat spotlessApply`
   
## Run Junits

When we try to build jar using `gradle.bat clean build` it runs spotless check, junits, code coverage along with prepare archive. But, if we want to 
run JUnits, then use below command(Please note as this will internally call `jacocoTestReport` to generate code coverage report).

`gradle.bat test`

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
