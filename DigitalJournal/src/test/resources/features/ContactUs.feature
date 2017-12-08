Feature:
  As a not logged in user
  I want to contact the DigitalJournal-team
  so that they get an email with my issue

  Scenario: Successful Contact Request
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "Jonas" into input field having id "txt_contactname"
    And I enter "jonas@example.com" into input field having id "txt_contactemail"
    And I enter "Help me! I forgot my password :(" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactsuccess"

  Scenario: Contact Name Empty
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "" into input field having id "txt_contactname"
    And I enter "jonas@example.com" into input field having id "txt_contactemail"
    And I enter "Help me! I forgot my password :(" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactemptyform"

  Scenario: Email Empty
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "Jonas" into input field having id "txt_contactname"
    And I enter "" into input field having id "txt_contactemail"
    And I enter "Help me! I forgot my password :(" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactemptyform"

  Scenario: Message Empty
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "Jonas" into input field having id "txt_contactname"
    And I enter "jonas@example.com" into input field having id "txt_contactemail"
    And I enter "" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactemptyform"

  Scenario: Email Invalid
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "Jonas" into input field having id "txt_contactname"
    And I enter "test" into input field having id "txt_contactemail"
    And I enter "Help me! I forgot my password :(" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactinvalidemail"

  Scenario: Name Too Long
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "JonasJonasJonasJonasJonas" into input field having id "txt_contactname"
    And I enter "jonas@example.com" into input field having id "txt_contactemail"
    And I enter "Help me! I forgot my password :(" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactnametoolong"

  Scenario: Message Too Long
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "JonasJonasJonasJonasJonas" into input field having id "txt_contactname"
    And I enter "jonas@example.com" into input field having id "txt_contactemail"
    And I enter "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu f" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactmessagetoolong"

  Scenario: Email Too Long
    Given I navigate to "/"
    And I click on element having id "btn_contactUs"
    When I enter "JonasJonasJonasJonasJonas" into input field having id "txt_contactname"
    And I enter "jonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonasjonas@example.com" into input field having id "txt_contactemail"
    And I enter "Help me! I forgot my password :(" into input field having id "txt_contactmessage"
    And I click on element having id "btn_contactsubmit"
    Then I should see modal "mdl_contactemailtoolong"

