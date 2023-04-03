Feature: User - Login and register

  Scenario Outline: Register and Login
    Given I have opened <browser>
    Given I have entered MailChimp website
    Given I have accepted cookies
    Given I input email "<email>"
    Given I input username "<username>"
    Given I input password "<password>"
    When I press sign up
    Then I have made an account "<state>"

    Examples:
      | browser | email             | username                                                                                               | password      | state   |
      | chrome  | hejsan1@gmail.com | random                                                                                                 | 1number1%CHAR | success |
      | chrome  | hejsan1@gmail.com | asdfghjklqwertyuiopzxcvbnm1234567891234567891234567891234567891234567891234567891234567891234567891234 | 1number1%CHAR | fail    |
      | chrome  | hejsan1@gmail.com | Hej                                                                                                    | 1number1%CHAR | fail    |
      | chrome  |                   | random                                                                                                 | 1number1%CHAR | fail    |
      | edge    | hejsan1@gmail.com | random                                                                                                 | 1number1%CHAR | success |
      | edge    | hejsan1@gmail.com | asdfghjklqwertyuiopzxcvbnm1234567891234567891234567891234567891234567891234567891234567891234567891234 | 1number1%CHAR | fail    |
      | edge    | hejsan1@gmail.com | Hej                                                                                                    | 1number1%CHAR | fail    |
      | edge    |                   | random                                                                                                 | 1number1%CHAR | fail    |