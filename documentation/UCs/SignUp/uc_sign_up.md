# DigitalJournal
## Use-Case Specification: Sign Up | Version 1.3

## 1. Use-Case: Sign Up

### 1.1 Brief Description

The use case describes the procedure when someone wants to create a new account in order to gain access to DigitalJournal.

## 2. Flow of Events

![AD_SignUp](Activity_Diagram_SignUp.png)

You can see the .feature file that describes this Use-Case [here](https://github.com/Toaster996/softwareengineering/blob/master/DigitalJournal/src/test/resources/features/SignUp.feature) or on the screenshot below:

![feature file Create Journal](https://github.com/Toaster996/softwareengineering/blob/master/DigitalJournal/src/test/resources/features/SignUp.png?raw=true)


### 2.1 Basic flow

The basic flow of this usecase is that the user enters his credentials, the email address is valid and not already in use and the passwords match. He will then receive an email with a link he needs to click withing 24h. If he does this the account is successfully created.

![success](success.png)

### 2.2 Form is empty

The user did not fill in every entry in the form. A message that the Form is empty will be displayed.

![form_empty](empty_form.png)

### 2.3 Username too long

The usernames are restricted to 20 characters. Longer usernames will not be accepted.

![username_too_long](username_too_long.png)

### 2.4 Username already taken

Usernames are used to identify a user and therefore are unique. If the requested username is already taken the user will be notified.

![username_already_taken](username_already_taken.png)

### 2.5 Email too long

The email has a maximum size of 100 characters. If a user tries to enter a longer email he will be notified, that this is not possible.

![email_too_long](email_too_long.png)

### 2.6 Email invalid

The value entered into email has to have a correct syntax. If it does not a message will be displayed. The user has to reenter this value.

![invalid_email](invalid_email.jpg) 

### 2.7 Email already in use.

If an account already exists with the given email address a message wil be displayed and the user has to enter another email address.

![email_already_in_use](email_already_in_use.png)

### 2.8 Password too short

To ensure a secure journal the minimum password length is 6 characters. If a user tries to create an account with a shorter password a message will be displayed. 

![password_too_short](password_too_short.png)

### 2.9 Password too long

The maximum password length is 42. If the user entered a password longer than 42 a message is displayed stating the password is too long.

![password_too_long](password_too_long.png) 

### 2.10 The passwords do not match

If the entered passwords do not match the user has to reenter them.

![passwords_missmatch](password_missmatch.png)

### 2.11 The user does not click on the link

The user entered everything correctly but does not click on the link in the email sent to him. After 24 hours the system will delete the data, invalidate the link and act like the user never has entered anything. No account will be created 

## 3. Special Requirements

### 3.1 Email address

In order to create an account the user has to have a valid email address which has not been used before.

### 3.1 Encrypted connection

As we are dealing with account information during this use case the connection has to be secured via ssh the whole time.

## 4. Preconditions

**n / a** for this usecase

## 5. Postconditions

### 5.1 Log in

After the successful sign up the user will be able to log in with his credentials, as his data is added to the database.

## 6. Extension Points

**n / a**