Feature:
  As a not registered user
  I want to register
  so that i have an account

  Scenario: Successful registration
    Given I navigate to "index.html"
    And I click on element having id "btn_log-in"
    When I enter "Jonas" into input field having id "txt_name"
    And I enter "jonas@example.com" into input field having id "txt_email"
    And I enter "password" into input field having id "txt_pw"
    And I enter "password" into input field having id "txt_conf_pw"
    And I click on element having id "btn_register"
    Then I should see modal "mdl_sucrec"

  Scenario: Username already taken
    Given I navigate to "index.html"
    And I click on element having id "btn_log-in"
    And user "duplicate" exists
    When I enter "duplicate" into input field having id "txt_name"
    And I enter "jonas@example.com" into input field having id "txt_email"
    And I enter "password" into input field having id "txt_pw"
    And I enter "password" into input field having id "txt_conf_pw"
    And I click on element having id "btn_register"
    Then I should see modal "mdl_useduser"

  Scenario: Email already taken
    Given I navigate to "index.html"
    And I click on element having id "btn_log-in"
    And email "duplicate@example.com" taken
    When I enter "jonas" into input field having id "txt_name"
    And I enter "duplicate@example.com" into input field having id "txt_email"
    And I enter "password" into input field having id "txt_pw"
    And I enter "password" into input field having id "txt_conf_pw"
    And I click on element having id "btn_register"
    Then I should see modal "mdl_usedemail"

  Scenario: Email has invalid format
    Given I navigate to "index.html"
    And I click on element having id "btn_log-in"
    When I enter "jonas" into input field having id "txt_name"
    And I enter "error" into input field having id "txt_email"
    And I enter "password" into input field having id "txt_pw"
    And I enter "password" into input field having id "txt_conf_pw"
    And I click on element having id "btn_register"
    Then element having id "alt_email" should be present

  Scenario: Passwords do not match
    Given I navigate to "index.html"
    And I click on element having id "btn_log-in"
    When I enter "jonas" into input field having id "txt_name"
    And I enter "jonas@example.com" into input field having id "txt_email"
    And I enter "password" into input field having id "txt_pw"
    And I enter "123" into input field having id "txt_conf_pw"
    And I click on element having id "btn_register"
    Then element having id "alt_pw" should be present
