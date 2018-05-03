# Test plan

## 1.	Introduction
### 1.1	Purpose
The purpose of the Iteration Test Plan is to gather all of the information necessary to plan and control the test effort for a given iteration. 
It describes the approach to testing the software.
This Test Plan for vnv supports the following objectives:
-	Identifies the items that should be targeted by the tests.
-	Identifies the motivation for and ideas behind the test areas to be covered.
-	Outlines the testing approach that will be used.
-	Identifies the required resources and provides an estimate of the test efforts.

### 1.2	Scope
This document describes the used tests, as they are unittests and functionality testing.

### 1.3	Intended Audience
This document is meant for internal use primarily.

### 1.4	Document Terminology and Acronyms
- **SRS**	Software Requirements Specification
- **n/a**	not applicable
- **tbd**	to be determined

### 1.5	 References
- [GitHub](https://github.com/WMerk/VnVProject)
- [Blog](https://vnvproject.wordpress.com/)
- [Overall Use case diagram](https://github.com/WMerk/VnVProject/blob/master/doc/use%20cases/SRS.png)
- [Software Requirements Specification](SRS.MD)
- [Software Architecture Document](SAD.MD)
- [Function points](https://github.com/WMerk/vnvDoc/blob/master/doc/FP.pdf)
- [UC Delete friend](UC_DeleteFriend.MD)
- [UC Accept friend requests](UC_AcceptFriendRequest.MD)
- [UC List received friend requests](UC_ListReceivedFriendRequests.MD)
- [UC List sent friend requests](UC_ListSentFriendRequests.MD)
- [UC Add friend](UC_AddFriend.MD)
- [UC ChangePassword](UC_ChangePassword.MD)
- [UC Create new request](UC_CreateNewRequest.MD)
- [UC Create new offer](UC_CreateNewOffer.MD)
- [UC List requests](UC_ListRequests.MD)
- [UC List offers](UC_ListOffers.MD)
- [UC Search for offers or requests](UC_SearchOffersRequests.MD)
- [UC Edit status of offer / request](UC_EditStatus.MD)
- [UC DeleteAccount](UC_DeleteAccount.MD)
- [UC EditProfile](UC_EditProfile.MD)
- [UC Login](UC_Login.MD)
- [UC Register](UC_Register.MD)
- [UC Register/Login with Google](UC_RegisterLoginGoogle.MD)

## 2.	Evaluation Mission and Test Motivation
### 2.1	Background
By testing our project, we make sure that all changes to the sourcecode do not break the functionality. Also by integrating the test process in our deployment process, we make sure that only working versions of our project getting deployed. So the web application is always available.
### 2.2	Evaluation Mission
Our motivation in implementing tests came at an early stage to recognize the need for errors and to ensure the functionality and thus the outstanding quality of the software.
### 2.3	Test Motivators
Our testing is motivated by 
- quality risks 
- technical risks, 
- use cases 
- functional requirements

## 3.	Target Test Items
The listing below identifies those test items (software, hardware, and supporting product elements) that have been identified as targets for testing. This list represents what items will be tested. 

Items for Testing:
- java backend
- web frontend
- database operations

## 4.	Outline of Planned Tests
### 4.1	Outline of Test Inclusions
Unit testing the Java backend, functional testing of the Web frontend or Database Integrity Testing
### 4.2	Outline of Other Candidates for Potential Inclusion
Stress testing the application, unit testing the frontend might be 
potential test cases but these are not in scope of our testing process yet.
### 4.3 Outline of Test Exclusions
What we are not planning to do is attack simulation. 

## 5.	Test Approach
### 5.1 Initital Test-Idea Catalogs and Other Reference Sources
**n/a**
### 5.2	Testing Techniques and Types
#### 5.2.1	Database Integrity Testing
|| |
|---|---|
|Technique Objective  	| Exercise database access and observe behaviour |
|Technique 		|  Execute queries and observe desired results with other queries |
|Oracles 		|  Queries will store and delete data and it will be represented in the database   |
|Required Tools 	| JUnit	 |
|Success Criteria	|    Green tests         |
|Special Considerations	|     -          |

#### 5.2.2 Function Testing
|| |
|---|---|
|Technique Objective  	| Exercise target-of-test functionality, including navigation, data entry, processing, and retrieval to observe and log target behavior. |
|Technique 		|  JUnit tests that simulate different values  |
|Oracles 		|  user enter valid data, for example a valid username and a valid password   |
|Required Tools 	| Selenium + Cucumber	 |
|Success Criteria	|    successful senarios         |
|Special Considerations	|     -          |

#### 5.2.3 Business Cycle Testing
**n/a**

#### 5.2.4 User Interface Testing
|| |
|---|---|
|Technique Objective  	| Exercise the following to observe and log standards conformance and target behavior: <br />•	Navigation through the target-of-test reflecting business functions and requirements, including window-to-window, field-to- 	field, and use of access methods (tab keys, mouse movements, accelerator keys).<br />•	Window objects and characteristics can be exercised–such as menus, size, position, state, and focus. |
|Technique 		|  Execute each use-case scenario’s individual use-case flows or functions and features, using valid and invalid data, to verify that: the expected results occur when valid data is used; the appropriate error or warning messages are displayed when invalid data is used; each business rule is properly applied. Selenium can simulate all user interactions like clicks, swipes and more. |
|Oracles 		|  user enter valid data, for example a valid username and a valid password   |
|Required Tools 	| Selenium + Cucumber	 |
|Success Criteria	|    successful senarios         |
|Special Considerations	|     -          |

#### 5.2.5 Performance Profiling 
**n/a**

#### 5.2.6 Load Testing
**n/a**

#### 5.2.7 Stress Testing
**n/a**

#### 5.2.8	Volume Testing
**n/a**

#### 5.2.9	Security and Access Control Testing
**n/a**

#### 5.2.10	Failover and Recovery Testing
**n/a**

#### 5.2.11	Configuration Testing
**n/a**

#### 5.2.12	Installation Testing
**n/a**

## 6.	Entry and Exit Criteria
### 6.1	Test Plan
#### 6.1.1	Test Plan Entry Criteria
Building a new version of the software will execute the testprocess.
#### 6.1.2	Test Plan Exit Criteria
When all tests pass without throwing an exception .
#### 6.1.3 Suspension and Resumption Criteria
It will be possible to skip the tests for faster deployment during development.
Apart from that the tests will always run till the end.

## 7.	Deliverables
### 7.1	Test Evaluation Summaries
Test evaluation will be available online every time the tests are run in travis. This is still to be done.
### 7.2	Reporting on Test Coverage
Test coverage numbers will be generated by travis. This is still to be done
### 7.3	Perceived Quality Reports
Code quality will be measured with codacy. This will be set up later.
### 7.4	Incident Logs and Change Requests
With the help of the above tools we will integrate a check into github for pull requests.
### 7.5	Smoke Test Suite and Supporting Test Scripts
For each pull request on the server project, a codecov report is generated that shows the new test
 coverage. This allows to check for any regressions. The report will be added later.
### 7.6	Additional Work Products
#### 7.6.1	Detailed Test Results
to be added 
#### 7.6.2	Additional Automated Functional Test Scripts
**n/a**
#### 7.6.3	Test Guidelines
In general all testable code should be tested. Due to time ocnstraint this is of course not always possible. Therefore we set a bound of 30% for coverage
#### 7.6.4	Traceability Matrices
**n/a**

## 8.	Testing Workflow
Every developer can run the tests in his IDE and is required to do so on a regular basis. Furthermore tests will be run automatically on  
## 9.	Environmental Needs
This section presents the non-human resources required for the Test Plan.
### 9.1	Base System Hardware
The following table sets forth the system resources for the test effort presented in this Test Plan.

| Resource | Quantity | Name and Type |
|---|---|---|
| Integration Server | 1 | Debian Server |
| Server Name |  	| minerlevel.de |
| Development Server	| 1 | <Server>	|
| Server Name |  | localhost |
| Database | 2 | <Name>	|
| Database Name |  | minerlevel.de |
| Database Name |  | localhost |

### 9.2	Base Software Elements in the Test Environment
The following base software elements are required in the test environment for this Test Plan.

| Software Element Name | Version | Type and Other Notes |
|---|---|---|
| Windows | 10 | Operating System |
| Firefox |  58	| Internet Browser |
| Geckodriver |  0.19.1 | Application |
| MySQL | 8.0.11 | Database |

### 9.3	Productivity and Support Tools
The following tools will be employed to support the test process for this Test Plan.

| Tool Category or Type | Tool Brand Name                              |
|-----------------------|----------------------------------------------|
| Code Hoster           | [github.com](http://github.com/)             |
| Test Coverage Monitor | [codecov.io](https://codecov.io)             |
| CI Service            | [Travis CI](http://travis-ci.org/)           |
| Metrics Tool          | [codacy](https://app.codacy.com/)            |

### 9.4	Test Environment Configurations
In order to be able to run the tests you need to have a database set up and running with the specification in the spring database file.

## 10.	Responsibilities, Staffing, and Training Needs
### 10.1	People and Roles
This table shows the staffing assumptions for the test effort.

Human Resources


| Role | Minimum Resources Recommended (number of full-time roles allocated) |	Specific Responsibilities or Comments |
|---|---|---|
| Test Manager | 1 | Provides management oversight. <br> Responsibilities include: <br> planning and logistics <br> agree mission <br> identify motivators<br> acquire appropriate resources<br> present management reporting<br> advocate the interests of test<br>evaluate effectiveness of test effort |
| Test Designer | 1 | Defines the technical approach to the implementation of the test effort. <br> Responsibilities include:<br> define test approach<br> define test automation architecture<br> verify test techniques<br> define testability elements<br> structure test implementation|
| Tester | 1 |	Implements and executes the tests.<br> Responsibilities include:<br> implement tests and test suites<br> execute test suites<br> log results<br> analyze and recover from test failures<br> document incidents|
| Test System Administrator | 1 | Ensures test environment and assets are managed and maintained.<br> Responsibilities include:<br> 	administer test management system<br> install and support access to, and recovery of, test environment configurations and test labs | 
| Database Administrator, Database Manager | 1 | Ensures test data (database) environment and assets are managed and maintained.<br> Responsibilities include:<br> support the administration of test data and test beds (database). |
| Implementer | 3 | Implements and unit tests the test classes and test packages.<br> Responsibilities include:<br> creates the test components required to support testability requirements as defined by the designer |

### 10.2	Staffing and Training Needs
**n/a**
## 11.	Iteration Milestones

| Milestone | Planned Start Date | Actual Start Date | Planned End Date | Actual End Date |
|---|---|---|---|---|
| Have Unit Tests | 23.4.2018 | 23.4.2018 | 4.5.2018 | 3.5.2018 |
| 30% coverage | 04.5.2018 |  | 14.5.2018 |  |
| Tests integrated in CI | 14.05.2018 | 14.05.2018 | 21.05.2018 |  |
| Iteration ends |  | |  | |

## 12.	Risks, Dependencies, Assumptions, and Constraints
| Risk | Mitigation Strategy	| Contingency (Risk is realized) |
|---|---|---|
| Untestable features in <br/>the framework | Cannot be avoided | Try to test it with integration tests |
| Testing scenario is not <br/>covered | Carefully design tests | Add scenario | 
## 13. Management Process and Procedures
**n/a**