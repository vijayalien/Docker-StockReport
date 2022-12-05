Feature:  Run MoneyControl page

  Scenario: Test Moneycontorl website

    Given user redirects to moneycontrol website "MoneyControl"
    And user get the title
    And user wait for "5" seconds
    And validate the first page
    And user wait for "10" seconds
    And user clicks on broker research page
    And user validate broker research page
    And user clicks on buy option
    And user fetch broker results details
#    And user clicks on all stats
#    And user validate all stats page
#    And user clicks on price and volume increase
