# Build/Setup
### Get project from github
- To get the code for our project use the following command:
`git clone https://github.com/PIK-Team/graph-dictionary.git`
## Backend
### Configuration
There are some options to configure the projects resources inside the `<your-path>/graph-dictionary/api/src/main/resources/application.properties` file
- `spring.neo4j.uri` - uri of the neo4j server
- `spring.neo4j.authentication.username` - login
- `spring.neo4j.authentication.password` - password
### Run the application
- To run the application, head over to the `<your-path>/graph-dictionary/api` folder - here you will find the gradle wrapper needed to work with the project
- After a succesful launch, the API should be available at `localhost:9090`
### Build the application
- To run the app, use `gradlew bootrun`
- To build the app, use `gradlew build`, the resulting artifacts should be generated inside `<your-path>/graph-dictionary/api/build/libs`
- You should be able to find two artifacts, but you should be interested in using the one named `Graph-Dictionary_backend-<VERSION>-application`
### Deployment
Deployment is done from the release branch onto the https://graph-dictionary.azurewebsites.net/ server, from which it should be available for public use
- In order to deploy the app, esablish a connection with our CI/CD server
- Next, you should head over to the Jenkins app via the browser
- Go over to the BlueOcean view and start the build pipeline
- On success, the .war file should be available on our Nexus repository
- Next, you should start the release pipeline, which would fetch the latest resoursce from our Nexus repository and deploy it to the azure server
## Frontend
### Run the applcation
- To run the application, use `gatbsy develop` inside the main directory
### Deployment
- Thanks to Gatsby cloud's integration with github, the application effortlessly deploys to the `https://graphdictionary25622.gatsbyjs.io/` website, where it should be available for the end-user
- To deploy, just head over to https://www.gatsbyjs.com/
