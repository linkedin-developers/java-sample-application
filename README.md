# java-oauth-sample-application
Sample Application for LinkedIn OAuth APIs

# Features
Rest API calls to Oauth and LMS functions. Separate Client and Server components to manage API calls with LinkedIn's API endpoints. Server Creates and stores the access token, Makes API calls with payload on a request from the client.

# The Sample Application uses the below technologies:
- Spring Boot: Used as web server framework
- LinkedIn OAuth 2.0: Authenticate with LinkedIn APIs
- Maven
- Java 7+

# Create a LinkedIn Developer Application
Before you can configure and run this application, you need to have an application registered in LinkedIn Developer Portal. Once you have your application, please obtain the Client ID and Client Secret.

Add http://localhost:8080/login to the Authorized Redirect URLs under the Authentication section.

# Configure MAVEN
To configure the application build download and install MAVEN using this guide :- https://maven.apache.org/install.html

# Configure the Application

Configure Client App: 
Edit /server/src/main/resources/application.properties with your custom values if you want to use another server link or port.


Configure Server App:
Edit /server/src/main/resources/config.properties with your client credentials

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


