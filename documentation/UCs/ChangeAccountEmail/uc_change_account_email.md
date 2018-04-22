# DigitalJournal
## Use-Case Specification: Change Account Email | Version 1.3

## 1. Use-Case: Change Account Email

### 1.1 Brief Description

The use case describes the procedure when someone wants to alter his email address.

## 2. Flow of Events

![AD_change_account_email](uc_change_account_email.png)


### 2.1 Basic flow

The basic flow of this usecase is that the user visits his profile page to change his email address. He needs to provide his password in order to change his email. After the password was checked and profound as correct an email will be send to both the old and new mail addresses. The user now has to confirm via the links in both mails. After that the old email will be updated to the new one.   

![send_emails](send_emails.png)
![success](email_changed.JPG)

### 2.2 Password is wrong

The user gets prompted to enter the correct password if the entered one was incorrect.

![password_wrong](password_incorrect.png)

### 2.3 Field is empty

If a field was left empty the user is required to fill the empty field as well.

![empty_field](field_empty.png)

### 2.4 New email is invalid

The entered email is not a valid email, so the user gets prompted to enter a valid one.

![email_invalid](email_invalid.PNG)

## 3. Special Requirements

### 3.1 Email address

In order to send a contact request the user needs two valid email addresses. (old one and the new one)

## 4. Preconditions

### 4.1 The user has to be logged in

The user has to be logged in when altering his account details.

## 5. Postconditions

### 5.1 The user has a new mail

After changing his email successfully the users old email gets overridden by his/her new email.

## 6. Function Points

To calulate the function points for a specific use case we used the [TINY TOOLS FP Calculator](http://groups.umd.umich.edu/cis/course.des/cis525/js/f00/harvey/FP_Calc.html).

    Score:      27,28 Function Points. 
    Estimation: 6h 37m.

![domain table](_dct.PNG)

![complexity table](_cat.PNG)
