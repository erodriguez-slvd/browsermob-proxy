<!-- PROJECT LOGO -->
<div align="center">
  <h1 align="center">BrowserMob Proxy</h1>
  <p align="center">with Carina Framework</p>
</div>

<!-- TABLE OF CONTENTS -->
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#built-with">Built With</a></li>
    <li><a href="#installation">Installation</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#implementation-details">Implementation Details</a></li>
    <li><a href="#useful-documentation">Useful Documentation</a></li>
  </ol>

## About The Project


## Built With
* Programming Language: Java
* Frameworks: Carina, TestNG
* Reporting Tools: Zebrunner

## Installation
- [ ] Download chromedriver for your Chrome browser version, save it in Downloads.
- [ ] Download selenium-server-standalone-3.141.59.jar, save it in Downloads.
- [ ] Clone the repo.
- [ ] Run selenium standalone opening a new terminal in Downloads and run this command: java -jar selenium-server-standalone-3.141.59.jar
- [ ] Modify the -config.properties file with browser-name and version.

## Implementation details
### BrowserMob Proxy
- BrowserMob Proxy allows you to manipulate HTTP requests and responses, capture HTTP content, and export performance data as a HAR file. BMP works well as a standalone proxy server, but it is especially useful when embedded in Selenium tests.
- To use BrowserMob Proxy in your tests or application, add the browsermob-core dependency to your pom:

[//]: # (```)

[//]: # (<!-- https://mvnrepository.com/artifact/net.lightbody.bmp/browsermob-core -->)

[//]: # (<dependency>)

[//]: # (    <groupId>net.lightbody.bmp</groupId>)

[//]: # (    <artifactId>browsermob-core</artifactId>)

[//]: # (    <version>2.1.5</version>)

[//]: # (</dependency>)

[//]: # (```)

[//]: # (- Start the proxy:)

[//]: # (```)

[//]: # (<!-- https://mvnrepository.com/artifact/net.lightbody.bmp/browsermob-core -->)

[//]: # (<dependency>)

[//]: # (    <groupId>net.lightbody.bmp</groupId>)

[//]: # (    <artifactId>browsermob-core</artifactId>)

[//]: # (    <version>2.1.5</version>)

[//]: # (</dependency>)

[//]: # (```)

## Useful Documentation

* [Carina](https://zebrunner.github.io/carina/)
* [Selenium Github Example](https://github.com/SeleniumHQ/seleniumhq.github.io/tree/trunk/examples)
* [BrowserMob Proxy](https://github.com/lightbody/browsermob-proxy)
* [TestNG](https://testng.org/doc/documentation-main.html)




### Getting started
* Install and configure JDK 11
* Install and configure [Apache Maven 3.6.0+](http://maven.apache.org/)
* Download and start the latest [Selenium standalone server](http://www.seleniumhq.org/download/)
* Download the latest version of [Eclipse](http://www.eclipse.org/downloads/) and install [TestNG plugin](http://testng.org/doc/download.html)
* [Read Carina documentation](https://zebrunner.github.io/carina/)

### Import to Eclipse
If generation is successfully complete, you would see a new project folder with a name equal to the artifactId attribute specified during generation, so navigate to that folder (where pom.xml is located) and execute the following Maven task:
```
mvn clean eclipse:eclipse
```
By executing this command, Maven should resolve all dependencies, downloading required libraries to your local repository and generating Eclipse classpath. Before importing new project to Eclipse, you should link your IDE to your Maven repository by executing the following task:
```
mvn -Dworkspace=<path_to_workspace> eclipse:configure-workspace
```
Here you have to specify the absolute path to the Eclipse workspace. After that, restart Eclipse IDE. Now you can import generated projects such as "Existing Java Project" into Eclipse IDE.
Generate Eclipse workspace using command:
```
mvn clean eclipse:eclipse
```

### Run tests
```
mvn clean test -Dsuite=api
```

