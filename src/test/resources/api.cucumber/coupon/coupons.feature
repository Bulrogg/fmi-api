Feature: Test associé au service des coupons

  Scenario: Je ne peux pas recuperer un coupon qui n'existe pas
    Given je ne fais rien
    When j'appelle l'api pour recuperer le coupons "10000"
    Then je ne recois pas de coupon
    And l'api me retourne bien un statut "404"

  Scenario: Je crée un coupon non utilisé via l'api puis je le récupère via l'api
    Given je crée le coupon non utilisé de nom "test-coupon-nom" et de reduction "test-coupon-reduction"
    When je recupere le dernier coupon que j'ai crée via l'api
    Then l'api me retourne bien un statut "200"
    And un coupon est bien retourné
    And son nom est bien "test-coupon-nom"
    And sa réduction est bien "test-coupon-reduction"
    And il est marqué comme non utilisé
    And son identifiant technique est correct

  Scenario: Je crée un coupon non utilisé via l'api puis je le récupère via l'api
    Given je crée le coupon utilisé de nom "test-coupon-nom" et de reduction "test-coupon-reduction"
    When je recupere le dernier coupon que j'ai crée via l'api
    Then l'api me retourne bien un statut "200"
    And un coupon est bien retourné
    And son nom est bien "test-coupon-nom"
    And sa réduction est bien "test-coupon-reduction"
    And il est marqué comme utilisé
    And son identifiant technique est correct

  Scenario: Je ne peux plus récupérer un coupon supprimé
    Given je crée le coupon non utilisé de nom "test-coupon-nom" et de reduction "test-coupon-reduction"
    When je supprime le dernier coupon que j'ai crée
    Then je ne peux plus récupérer le dernier coupons crée
    And l'api me retourne bien un statut "404"

  Scenario: Je ne récupère pas les coupons utilisés au moment de la récupération de la liste de coupons
    Given je crée le coupon utilisé de nom "test-coupon-nom" et de reduction "test-coupon-reduction"
    When je récupère la liste des coupons
    Then le dernier coupon ajouté ne se trouve pas dans la liste de coupon

  Scenario: Je récupère bien les coupons non utilisés au moment de la récupération de la liste de coupons
    Given je crée le coupon non utilisé de nom "test-coupon-nom" et de reduction "test-coupon-reduction"
    When je récupère la liste des coupons
    Then le dernier coupon ajouté se trouve bien dans la liste de coupon