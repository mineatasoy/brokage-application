BROKAGE APPLICATION

	After cloning the project, it is possible to run the application on an IDE (such as IntelliJ) 
	or it is possible to generate a jar file via mvn package command and run it with a Tomcat server as a test environment.

  
!!PLEASE TAKE INTO CONSIDERATION:

	This application works on the 8082 (configured at application.properties file)

APIs
	The application contains 4 different APIs.

	http://localhost:8082/api/orders/create

	http://localhost:8082/api/orders

	http://localhost:8082/api/assets

	http://localhost:8082/api/orders/delete


SECURITY:

	To be able to call the APIs, basic authorization is required with the below information

		admin.username=admin
		admin.password=hubsAdmin

OpenAPI 
	
	is also configured so it is possible to use SWAGGER-UI (please set the authorization for the user admin)

	http://localhost:8082/swagger-ui/index.html

NOTE:

2 seperated files are added to the repository as the Postman Collection and samples for the API input and outputs.

    Postman Collection: BrokerageAppTests.postman_collection.json
    Samples:		    Inputs_Outputs.docx


H2 DATABASE console is available at 

	http://localhost:8082/h2-console

	with the credentials as stated at application.properties file.

	username=sa
	password=password
	








