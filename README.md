<div id="badges" align="right">
  <a href="https://www.linkedin.com/in/ajay-vasudevan" target="_blank" rel="noopener noreferrer">
      <img src="https://img.shields.io/badge/LinkedIn-blue?logo=linkedin&logoColor=white&style=plastic" width="100" alt="LinkedIn Badge"/>
  </a>
</div>
<h1 align="left">
  <i>hey there ...</i>
  <img src="https://media.giphy.com/media/w1OBpBd7kJqHrJnJ13/giphy.gif" width="45"/>
</h1>

Micronaut is a JVM-based full-stack framework used to build modular microservices making

* Use of Java Reflections as the last meachanism to build the dependency tree
* Compile-time dependency injection for inline bytecode generation
* Less CPU & memory resource utilizations

The codebase is built on Micronaut to collate interested RSS feeds and render a webpage view from server side, itself.
The RSS
feeds Google alerts have been documented in the .env file. The file has been omitted in the repository, though.

# Features implemented

* Operating-system specific configuration files to adhere to 12-factor app principles
* RSS feeds Google alerts in .env file under boot >> src >> main >> resources
* Thread executor pattern & CountDownLatch to synchronize among parallel threads
* Ensure unique row in H2 database in MySQL mode
* Named Query in repository layer
* Server-side web page rendering Micronaut Thymeleaf
* Run in Raspberry Pi B plus on 1 CPU core | 512 Mb RAM

# Commands

* Run unit tests & build project : `mvn clean package -T 8`
* Start H2 Database (Windows) : `java -Xms16m -Xmx16m -XX:+UseG1GC -cp h2-2.1.214.jar org.h2.tools.Server`
* Start H2 Database (Linux) : `nohup java -Xms16m -Xmx16m -XX:+UseG1GC -cp h2-2.1.214.jar org.h2.tools.Server`
* Run stock feed executable jar (
  Windows) : `nohup java -Xms64m -Xmx64m -Dmicronaut.environments=windows -XX:+UseConcMarkSweepGC -jar stock-feeds.jar`
* Run stock feed executable jar (
  Linux) : `nohup java -Xms64m -Xmx64m -Dmicronaut.environments=linux -XX:+UseConcMarkSweepGC -jar stock-feeds.jar`
* Shell script (Linux) : `nohup ./stock-feed-start-up.sh`

Cheers ...
<br/><br/><img src="https://media.giphy.com/media/1BhVFK6ejcQV86UtHl/giphy.gif" width="100"/>

# stock-feeds