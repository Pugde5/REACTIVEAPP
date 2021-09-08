How To run.

1. Unzip into a directory. (
This is a normal Spring Boot App.
Start the Spring boot app with the command
prompt:> mvn spring-boot:run
from the directory into which you unzipped my source.

An issue is that I expect your service to be available at localhost:8080 Your endpoints should be:
http://localhost:8080/prices
http://localhost:8080/track
http://localhost:8080/shipments


My server runs on port 9090. Thus my endpoint is:
http://localhost:9090/aggregation

You may change the addresses if you want. Open the file
src/main/resources/application.properties
and configure the properties:
server.port - the port that my server runs on
your_server - the base address of your server (i will add /prices?q= and so on to this address.)







