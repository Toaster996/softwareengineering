Feature:
  As a logged in user
  I want to create a new journal
  so that i have a added a journal to my site

  Scenario: Successful Creation
    Given I navigate to "journal.html"
    And I click on element having id "btn_newjournal"
    When I enter "Journalname" into input field having id "txt_journalname"
    And I enter "Description" into input field having id "txt_journaldesc"
    And I click on element having id "btn_submitjournal"
    Then element having class "card-title" should have text as "Journalname"
    And element having class "card-text" should have text as "Journalname"

  Scenario: Journalname Empty
    Given I navigate to "journal.html"
    And I click on element having id "btn_newjournal"
    When I enter "" into input field having id "txt_journalname"
    And I enter "Description" into input field having id "txt_journaldesc"
    And I click on element having id "btn_submitjournal"
    Then I should see modal "mdl_emptyname"

  Scenario: Description Empty
    Given I navigate to "journal.html"
    And I click on element having id "btn_newjournal"
    When I enter "Journalname" into input field having id "txt_journalname"
    And I enter "" into input field having id "txt_journaldesc"
    And I click on element having id "btn_submitjournal"
    Then I should see modal "mdl_emptydesc"

