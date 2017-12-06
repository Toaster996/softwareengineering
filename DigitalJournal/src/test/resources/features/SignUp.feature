Feature:
  As a not registered user
  I want to register
  so that i have an account

  Scenario: Form is Empty
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I click on element having id "btn_reg_submit"
    Then I should see modal "emptyform"

  Scenario: Username too long
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "thisnameistoolong123456" into input field having id "lbl_reg_name"
    And I enter "jonas@example.com" into input field having id "lbl_reg_email"
    And I enter "password" into input field having id "lbl_reg_password"
    And I enter "password" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see bootstrapalert "nametoolong"


  Scenario: Username already taken
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "duplicate" into input field having id "lbl_reg_name"
    And I enter "jonas@example.com" into input field having id "lbl_reg_email"
    And I enter "password" into input field having id "lbl_reg_password"
    And I enter "password" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see modal "useduser"

  Scenario: Email too long
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "duplicate" into input field having id "lbl_reg_name"
    And I enter "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890@example.com" into input field having id "lbl_reg_email"
    And I enter "password" into input field having id "lbl_reg_password"
    And I enter "password" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see bootstrapalert "emailtoolong"

  Scenario: Email invalid
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "Jonas" into input field having id "lbl_reg_name"
    And I enter "jonas@example" into input field having id "lbl_reg_email"
    And I enter "password" into input field having id "lbl_reg_password"
    And I enter "password" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see bootstrapalert "emailinvalid" 
    
  Scenario: Email already taken
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "jonas" into input field having id "lbl_reg_name"
    And I enter "duplicate@example.com" into input field having id "lbl_reg_email"
    And I enter "password" into input field having id "lbl_reg_password"
    And I enter "password" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see modal "usedemail"

  Scenario: Password too short
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "Jonas" into input field having id "lbl_reg_name"
    And I enter "jonas@example.com" into input field having id "lbl_reg_email"
    And I enter "1" into input field having id "lbl_reg_password"
    And I enter "1" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see bootstrapalert "pwtoologn"

  Scenario: Successful registration
    And I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "Jonas" into input field having id "lbl_reg_name"
    And I enter "jonas@example.com" into input field having id "lbl_reg_email"
    And I enter "password123456789123456789123456789123456789" into input field having id "lbl_reg_password"
    And I enter "password123456789123456789123456789123456789" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see bootstrapalert "pwtoologn"

  Scenario: Passwords do not match
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "jonas" into input field having id "lbl_reg_name"
    And I enter "jonas@example.com" into input field having id "lbl_reg_email"
    And I enter "password" into input field having id "lbl_reg_password"
    And I enter "123" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then I should see bootstrapalert "pwmissmatch"
    
  Scenario: Successful registration
    Given I navigate to ""
    And I click on element having id "btn_log-in"
    When I enter "Jonas" into input field having id "lbl_reg_name"
    And I enter "jonas@example.com" into input field having id "lbl_reg_email"
    And I enter "password" into input field having id "lbl_reg_password"
    And I enter "password" into input field having id "lbl_reg_passwordconf"
    And I click on element having id "btn_reg_submit"
    Then element having id "sucreg" should be present
