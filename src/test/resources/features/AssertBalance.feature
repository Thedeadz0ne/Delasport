Feature: Assert balance in top bar and request getMemberBalance

  @Balance
  Scenario: Login in babibet and assert user balance is correct
    Given Navigate to "https:/babibet.com.test-delasport.com"
    And Click in site with username "betplayer" and password "Pass112#"
    When Close the modals (pop-ups) if any upon login
    Then  Verify if the balance in the header is the same as the one from the response to request getMemberBalance
