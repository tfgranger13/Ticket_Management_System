# Ticket Management System

The purpose of this website is to create a platform that will allow customers to register for the service and allow employees to communicate with the customers 
to assist them in resolving technical issues.

## Installation

This service runs on Java 8 using Spring Boot 2.1.0.RELEASE.\
All required dependencies can be found in the pom.xml file.

## Usage

Customers must register for the site. They can then log in and submit tickets.\
In a future build, Employees will also be able to log in to view tickets they have been assigned.\
In a future build, Managers will be able to assist with tickets for all employees in their department.

## User Stories

>"As a customer, I want my password secured so no one can access my information."

> "As a customer, I want to be able to send and receive messages with an employee to better understand the solution to my problem."

> "As an employee, I want a dashboard that will show me all of my tickets so I can work more efficiently to resolve the issues."

> "As a manager, I want to be able to see all tickets from my department so I can better assist employees in need."

> "As an administrator, I want full CRUD control for entities using the site so I can manually update their information."

## Technical Challenges

The most challenging component of this project was the security. I spent about three days working through the JavadevJournal tutorial on their website and the files from their Github repo to try to get 
a decent understanding of how Spring Security works, and I still barely scratched the surface of what is possible.

Another challenge I faced was dealing with different versions of components. Many resources I found online described how to complete the task I wanted to do, 
but did not state which version they were using. This became somewhat of an issue when trying to implement certain features in Thymeleaf, Spring Security, and Junit testing. 
I would try to construct codes with a similar structure, but I would get errors stating that certain methods I was calling did not exist, or certain annotations 
would not be recognized by Spring. I was able to dig deeper and find resources that did work for my project, it just took perseverance and grit.

## Support

If you notice a bug or have an issue with the site, please submit a ticket through the site.\
Just kidding, send me a message on Github.

## Contributing

This is my project, so I would like to manage it accordingly.\
If you think you have a good idea for the site, please let me know and I will take it into consideration.

## License

[MIT](https://choosealicense.com/licenses/mit/)

Security aspects of this project were developed with the help of\
the JavadevJournal tutorial found at:\
https://www.javadevjournal.com/spring-security-tutorial
Their Github repo can be found at:\
https://github.com/javadevjournal/javadevjournal/tree/master/spring-security/spring-security-series

## Project Status

This project is a work in progress.\
There are many features I would still like to implement if given the chance.