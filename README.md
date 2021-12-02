# java-oauth-sample-application
Sample Application for LinkedIn OAuth APIs

# Features
Rest API calls to Oauth and LMS functions. Separate Client and Server components to manage API calls with LinkedIn's API endpoints. Server Creates and stores the access token, Makes API calls with payload on a request from the client.

# The Sample Application uses the below technologies:
- Spring Boot: Used as web server framework [ https://spring.io/projects/spring-boot ]
- LinkedIn OAuth 2.0: Authenticate with LinkedIn APIs
- Maven
- Java 7+

# Create a LinkedIn Developer Application
Before you can configure and run this application, you need to have an application registered in LinkedIn Developer Portal. 
Link: https://developer.linkedin.com/
Once you have your application, please obtain the Client ID and Client Secret.


Add http://localhost:8080/login to the Authorized Redirect URLs under the Authentication section.

# Configure MAVEN
To configure the application build download and install MAVEN using this guide :- https://maven.apache.org/install.html

# Configure the Application

Configure Client App:
Edit /server/src/main/resources/application.properties with your custom values if you want to edit server link or port,
server.port = <replace_with_port_no>
SERVER_URL = <replace_with_server_url>

Configure Server App:
Edit /server/src/main/resources/config.properties with your client credentials,
clientId=<replace_with_client_id>
clientSecret=<replace_with_client_secret>
redirectUri=<replace_with_redirect_url_set_in_developer_portal>
scope=<replace_with_api_scope>
client_url=<replace_with_client_url>

# Start the application

To start server:
Navigate into the server folder
1. Run mvn install (To install all dependencies)
2. Run mvn spring-boot:run (To run spring-boot server)
The server will be running on http://localhost:8080/

To start client:
Navigate into the client folder
1. Run mvn install (To install all dependencies)
2. Run mvn spring-boot:run (To run spring-boot server)
The client will be running on http://localhost:8989/

# List of dependencies
| Component Name                                                                                                                                                                  | License    | Linked | Modified |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------- | ------ | -------- |
| [org.springframework.boot:spring-boot-starter-parent:2.5.2](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent/2.5.2)                       | Apache 2.0 | Static | No       |
| [org.springframework.boot:spring-boot-starter-thymeleaf:2.2.2.RELEASE](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf/2.2.2.RELEASE) | Apache 2.0 | Static | No       |
| [org.springframework.boot:spring-boot-devtools:2.6.0                ](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools/2.6.0)                   | Apache 2.0 | Static | No       |
| [com.fasterxml.jackson.core:jackson-databind:2.13.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.13.0)                                     | Apache 2.0 | Static | No       |
| [com.fasterxml.jackson.core:jackson-core:2.13.0 ](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core/2.13.0)                                            | Apache 2.0 | Static | No       |
| [org.springframework.boot:spring-boot-starter-web:2.5.2](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web/2.5.2)                             | Apache 2.0 | Static | No       |
| [org.springframework.boot:spring-boot-starter-test:2.6.0 ](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test/2.6.0)                          | Apache 2.0 | Static | No       |
| [org.springframework:spring-core:5.3.13](https://mvnrepository.com/artifact/org.springframework/spring-core/5.3.13)                                                             | Apache 2.0 | Static | No       |


