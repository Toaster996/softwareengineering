Feature:
  As a any user
  I want to contact the site administrators

  Scenario: Name is empty
    Given I navigate to ""
    And I click on element having id "btn_contact"
    And I enter "jonas@example.com" into input field having id "lbl_contact_email"
    And I enter "Test message" into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see bootstrapalert "name_emtpy"

  Scenario: Email is empty
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

  Scenario: Name Too Long
    Given I navigate to "/"
    And I click on element having id "btn_contact"
    When I enter "JonasJonasJonasJonasJonas" into input field having id "lbl_contact_name"
    And I enter "jonas@example.com" into input field having id "lbl_contact_email"
    And I enter "Help me! I forgot my password :(" into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see modal "mdl_name_too_long"

  Scenario: Message Too Long
    Given I navigate to "/"
    And I click on element having id "btn_contact"
    When I enter "Jonas" into input field having id "lbl_contact_name"
    And I enter "jonas@example.com" into input field having id "lbl_contact_email"
    And I enter "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu f." into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see modal "mdl_message_too_long"

  Scenario: Email Too Long
    Given I navigate to "/"
    And I click on element having id "btn_contact"
    When I enter "Jonas" into input field having id "lbl_contact_name"
    And I enter "jonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonas@example.com" into input field having id "lbl_contact_email"
    And I enter "Help me! I forgot my password :(" into input field having id "lbl_contact_message"
    And I click on element having id "btn_contact_submit"
    Then I should see modal "mdl_mail_too_long"
