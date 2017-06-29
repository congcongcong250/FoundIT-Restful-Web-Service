# An Online Recruitment Service

This is a Java 1.7 RESTFUL Service application using Apache CXF and Spring framework on JAX-RS API. The application runs on Tomcat 8.0 and connects to Mysql.

## Introduction
The Web Service is for management of necessary resources and workflow for a Online Recruitment Service. The basic client side application includes a workflow for employee and employer. 

Function for employees are:
- Search job postings
- Submit application to an job
- Update application
- Cancel appliation
- Accept offer
- Reject offer

Functions for employers are:
- Create job postings
- Update job postings
- Close job postings
- Assign a review team to review candidates applications
- Review candidates' applications,

### Workflow
Whole Workflow
![workflow](https://s3-ap-southeast-1.amazonaws.com/lirenxn-random-img/ClientIT-RESTful/whole.PNG)

Employee Workflow
![workflow](https://s3-ap-southeast-1.amazonaws.com/lirenxn-random-img/ClientIT-RESTful/emloyee.PNG)

Employer Workflow
![workflow](https://s3-ap-southeast-1.amazonaws.com/lirenxn-random-img/ClientIT-RESTful/employer.PNG)
## API and URL Design
### /applications [applicationServiceProvider.java]{https://github.com/congcongcong250/FoundIT-Restful-Web-Service/blob/master/src/main/java/au/edu/unsw/soacourse/foundIT/provider/applicationServiceProvider.java}
#### /applications/{id}
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get application by id|200 application XML|404|
|DELETE|Delete application by id|204 NO CONTENT|404|
#### /applications/count
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get number of applications|200 number|400|
#### /applications/all
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get all applications|200 List of applications XML|404|
#### /applications
|Method|Description|Success|Error|
|---|---|---|---|
|POST|Create a new application|201 CREATED|400|
#### /applications/update
|Method|Description|Success|Error|
|---|---|---|---|
|PUT|Update an application|201 CREATED|404|
### /comProfiles [comProfileServiceProvider](https://github.com/congcongcong250/FoundIT-Restful-Web-Service/blob/master/src/main/java/au/edu/unsw/soacourse/foundIT/provider/comProfileServiceProvider.java)
#### /comProfiles/{id}
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get company profile|200 XML of company details|404|
#### /comProfiles/count
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get number of companys|200 number|400|
#### /comProfiles/all
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get all company profiles|200 List of company details XML|404|
#### /comProfiles
|Method|Description|Success|Error|
|---|---|---|---|
|POST|Create a new company|201 CREATED|400|
#### /comProfiles/update
|Method|Description|Success|Error|
|---|---|---|---|
|PUT|Update an company profile|201 CREATED|404|
### /jobPostings [jobPostingServiceProvider](https://github.com/congcongcong250/FoundIT-Restful-Web-Service/blob/master/src/main/java/au/edu/unsw/soacourse/foundIT/provider/jobPostingServiceProvider.java)
#### /jobPostings/{id}
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get jobPosting by id|200 XML of job posting|404|
#### /jobPostings/all
|Method|Description|Success|Error|
|---|---|---|---|
|GET(Query with job key words)|Get job posting by query|200 XML of jobpostings|404|
#### /jobPostings/count
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get number of job postings|200 number|400|
#### /jobPostings/all
|Method|Description|Success|Error|
|---|---|---|---|
|GET|Get all jobPostings|200 List of job postings XML|404|
#### /jobPostings
|Method|Description|Success|Error|
|---|---|---|---|
|POST|Create a new job posting|201 CREATED|400|
#### /jobPostings/update
|Method|Description|Success|Error|
|---|---|---|---|
|PUT|Update an job posting|201 CREATED|404|
## Authentication

