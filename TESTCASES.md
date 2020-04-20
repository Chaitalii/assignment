Installation:
Java
Maven
Chrome/Firefox

To run locally:
mvn clean install 

To create disposable grid infrastructure, run below command from project directory.

docker-compose up -d

To run tests by connecting to the the remote selenium hub:
mvn clean install -DHUB_HOST="IP address"

