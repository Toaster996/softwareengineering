# DigitalJournal
## Use-Case Specification: <NAME>  v. <1.0>

## 1. Use-Case <NAME>

### 1.1 Brief Description

The user does not remember his password and wants to reset it

## 2. Flow of Events

![AD_recover_password](AD_recover_password.png)

### 2.1 Basic flow

The user entered his email correctly.

![recover_email](recover_email.png)

He therefore receives an email with a link to recover his password.

![email_sent](email_sent.png)

If he clicks on the link an entered a new password twice, the password is 6 to 42 characters long and the password matches then his password will be reset

![successful_reset](successful_reset.png)

### 2.2 The provided email is invalid

The user entered an email address that has no valid syntax.

### 2.3 The user does not click on the link in time

The uses entered his email correctly and receives an email with a link to recover his password.

![email_sent](email_sent.png)
 
However if he does not do it within 1h. The request will be deleted an the link is no longer valid.

### 2.4 The user typed his email wrong/the email has no account 

The same message will be display in order to avoid bruteforcing of mails.

![email_sent](email_sent.png)

### 2.5 The user clicked on the link but the password is not at least 6 characters long

The uses entered his email correctly and receives an email with a link to recover his password.

![email_sent](email_sent.png)

The user entered a password that is not at least 6 characters long.

![password_too_short](password_too_short.png)

### 2.6 The user clicked on the link but the password is longer than 42 characters long

The uses entered his email correctly and receives an email with a link to recover his password.

![email_sent](email_sent.png)

The user entered a password that is longer than 42 characters long.

![password_too_long](password_too_long.png)

### 2.7 The user clicked on the link but the passwords do not match

The uses entered his email correctly and receives an email with a link to recover his password.

![email_sent](email_sent.png)

The user entered a passwords that do not match.

![password_do_not_match](password_do_not_match.png)

## 3. Special Requirements

**n / a**

## 4. Preconditions

**n / a**

## 5. Postconditions

### 5.1 The password is changed

After successfully changing your password, you can now log in with the new password.

## 6. Function Points

To calulate the function points for a specific use case we used the [TINY TOOLS FP Calculator](http://groups.umd.umich.edu/cis/course.des/cis525/js/f00/harvey/FP_Calc.html).

    Score:      37,84 Function Points. 
    Time spent: 429min.

![domain table](_dct.PNG)

![complexity table](_cat.PNG)
