# Sample Application for LinkedIn APIs

> Please take a 1-minute survey to help us help you, with more Sample Apps for LinkedIn APIs.
> Go to **www.slido.com** and use the code **SampleApp** to answer the survey

## Overview

Sample Application is a ready-to-use code example that enables you to try out RESTful calls to LinkedIn's APIs. The application provides scalable and customizable code for your requirements as you begin API development with LinkedIn.

The sample application contains the client and server component you can use to manage your requests to LinkedIn's APIs. The server creates and stores your access token and invokes APIs upon request from the client application. You can download or clone the OAuth sample application and try out these APIs.

> **Note**: For a detailed demo, please visit LinkedIn's public documentation page

The sample application uses the following development tools:

* Spring Boot: Used as web server framework [<https://spring.io/projects/spring-boot>]
* LinkedIn OAuth 2.0: user authorization and API authentication
* Maven: app building and management
* Java: SE 7 or later versions are required for development

## Prerequisites

* Ensure that you have an application registered in [LinkedIn Developer Portal](https://developer.linkedin.com/).
Once you have your application, note down the Client ID and Client Secret
* Add <http://localhost:8080/login> to the Authorized Redirect URLs under the **Authentication** section
* Configure the application build by installing MAVEN using [Installing Apache Maven](https://maven.apache.org/install.html)

## Configure the application

**Configure the client app:**

 1. Navigate to the **application.properties** file. You can find this file under: **/client/src/main/resources/application.properties**
 1. To edit server link or port with custom values modify the following values:

    > server.port = <replace_with_required_port_no>

    > SERVER_URL = <replace_with_required_server_url>

 1. Save the changes.

**Configure the server app:**

 1. Navigate to the **config.properties** file. You can find this file under: **/server/src/main/resources/config.properties**
 2. Edit the following properties in the file with your client credentials:

    > clientId = <replace_with_client_id>

    > clientSecret = <replace_with_client_secret>

    > redirectUri = <replace_with_redirect_url_set_in_developer_portal>

    > scope = <replace_with_api_scope>
    client_url = <replace_with_client_url>

 3. Save the changes.
  
## Start the application

To start the server:

1. Navigate to the server folder.
2. Open the terminal and run the following command to install dependencies:
`mvn install`
3. Execute the following command to run the spring-boot server:
`mvn spring-boot:run`

> **Note:** The server will be running on <http://localhost:8080/>

To start the client:

1. Navigate to the client folder.
2. Open the terminal and run the following command to install dependencies:
 `mvn install`
3. Execute the following command to run the spring-boot server:
`mvn spring-boot:run`

> **Note**: The client will be running on <http://localhost:8989/>

## List of dependencies

|Component Name |License |Linked |Modified |
|---------------|--------|--------|----------|
|[boot:spring-boot-starter-parent:2.5.2](<https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent/2.5.2>) |Apache 2.0 |Static |No |
|[boot:spring-boot-starter-parent:2.5.2](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent/2.5.2) |Apache 2.0 |Static |No |
|[org.springframework.boot:spring-boot-starter-thymeleaf:2.2.2.RELEASE](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf/2.2.2.RELEASE) |Apache 2.0 |Static |No |
|[org.springframework.boot:spring-boot-devtools:2.6.0](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools/2.6.0) |Apache 2.0 |Static |No |
|[com.fasterxml.jackson.core:jackson-databind:2.13.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.13.0)                                     |Apache 2.0 |Static |No |
|[com.fasterxml.jackson.core:jackson-core:2.13.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core/2.13.0) |Apache 2.0 |Static |No |
|[org.springframework.boot:spring-boot-starter-web:2.5.2](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web/2.5.2) |Apache 2.0 |Static |No |
| [org.springframework.boot:spring-boot-starter-test:2.6.0](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test/2.6.0) |Apache 2.0 |Static |No |
|[org.springframework:spring-core:5.3.13](https://mvnrepository.com/artifact/org.springframework/spring-core/5.3.13) |Apache 2.0 |Static |No |
