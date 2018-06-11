# Installation Guide

## 1. MySQL

### 1.1 Install MySQl

Installing [MySQL](https://dev.mysql.com/downloads/installer/):
-	Choose developer default
-	Click Next
-	Click Next again (ignore warning as we don’t need these requirements)
-	Click Execute
-	Click yes in the migration popup
-	Click Next when finished
-	Click Next again
-	Select Standalone and click next
-	Keep selected values and click next
-	Enter the root password "asdf" and click Next
-	Keep values and Click Next
-	Click Next
-	Click Execute
-	Click Finish
-	Click Next
-	Make sure cluster is not selected and click Finish
-	Click Next
-	Click the check button
-	Click Next
-	Click execute
-	Click Finish
-	Click Next
-	Deselect Start MySQL Shell
-	Click Finish

### 1.2 Setup database schema

-   Open workbench
-   Choose your MySQL Connection
-   Create new schema: Button create schema 
-   enter name "digitaljournal"
- you can close it again but make sure mysql is still running in the background 



## 2. Install the Digital Journal

-   Install a [Java Runtime](https://www.java.com/de/) if you haven't already.
-   Simply run the jar provided by us.
-   go to [localhost:8080](http://localhost:8080/) and you should see the digital journal homepage

