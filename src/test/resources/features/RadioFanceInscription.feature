Feature: L'utilisateur s'inscrivent sur l'application Radio France

  @test
  Scenario: S'inscrit sur le site en saisissant les informations de l'utilisateur
    Given Uygulamayı başlatıyorum
    And Click le button Se Connecter
    Then Click sur Inscription
    And Ecrire "email" adress and "password" dans les champs
    And Click sur radiobox pour accepter les conditions
    When Click sur le button S`inscrire
    Then Verifie que le message " Bravo, vous êtes inscrit(e) !" apparait.

  Scenario: Tenter de s'inscrire sans accepter les conditions de remise en forme
    Given Uygulamayı başlatıyorum
    And Click le button Se Connecter
    Then Click sur Inscription
    And Ecrire "email" adress and "password" dans les champs
    When Click sur le button S`inscrire
    Then Verifie que le message "Veuillez accepter les conditions" apparait.

