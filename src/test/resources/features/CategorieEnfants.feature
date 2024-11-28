Feature: Vérification de la section "Enfant"
@tek
  Scenario: Accéder et tester la section "Enfant"
    Given Uygulamayı başlatıyorum
    When Je clique sur le lien "Enfants" dans le menu principal
    Then Vérifier que l'URL contient "enfants"
    And Voir le titre "Podcasts et histoires pour les enfants"
    When Je clique sur "Catégories" et sélectionne "Enfants"
    Then Vérifier que l'URL est identique à celle obtenue via le menu enfants
   # When Les épisodes sont affichés sous le titre "10 épisodes pour passer l'automne"
   # Then Choisir un épisode au hasard et vérifier qu'il commence à jouer
   # And Je clique button X pour fermer