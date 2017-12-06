Feature:
  As a andy user
  I want to contact the site administrators

  Scenario: Name is empty
    Given I navigate to ""
    And I click on element having id "btn_contact"
    And I enter "jonas@example.com" into input field having id "lbl_contact_email"
    And I enter "Test message" into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see bootstrapalert "name_emtpy"

  Scenario: Email is invalid
    Given I navigate to ""
    And I click on element having id "btn_contact"
    When I enter "Jonas" into input field having id "lbl_contact_name"
    And I enter "jonas@example" into input field having id "lbl_contact_email"
    And I enter "Test message" into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see bootstrapalert "email_invalid"

  Scenario: Message is empty
    Given I navigate to ""
    And I click on element having id "btn_contact"
    When I enter "Jonas" into input field having id "lbl_contact_name"
    And I enter "jonas@example.com" into input field having id "lbl_contact_email"
    And I enter "Test message" into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see bootstrapalert "message_empty"

  Scenario: Successful contact
    Given I navigate to ""
    And I click on element having id "btn_contact"
    When I enter "Jonas" into input field having id "lbl_contact_name"
    And I enter "jonas@example.com" into input field having id "lbl_contact_email"
    And I enter "Test message" into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see modal "mdl_email_send"