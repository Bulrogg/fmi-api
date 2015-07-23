API FMI TEST
===========

Simple API pour montrer les tests E2E, BDD et la doc API blueprint.

----------


API EXPOSÉ PAR LE BOUCHON
-------------------------------------

* GET /v1/coupons
* GET [/v1/coupons/{couponId}
* POST /v1/coupons   { "nom": "...", "reduction": "  ", "estUtilise": false }
* DELETE /v1/coupons/{couponId}


DEMARRER LE BOUCHON
------------------------------
Déveleloppé avec springboot

> mvn spring-boot:run

UTILISER L'API
------------------

plugin postman ou curl

> curl -H "Content-Type: application/json" -X POST -d '{"nom":"reduction 4","reduction":"1000 euros"}' http://localhost:8080/v1/coupons/

LANCER TOUS LES TESTS
------------------------------
Lancer les tests d'intégration + test BDD (cucumber)
> mvn test -Dmvn.test.base.url="http://localhost:8080/fmi"


LES URLS
--------

**Webviews**
* /webview/hello

**Administration**
* /admin/gestion-coupons

**APIs**
* /v1/coupons/{couponId} - [GET]
* /v1/coupons - [POST]
* /v1/coupons - [GET]
* /v1/coupons/{couponId} - [DELETE]

**Actuator**
* /env/{name:.*}
* /env
* /health
* /autoconfig
* /mappings
* /beans
* /info
* /configprops
* /trace
* /dump
* /metrics/{name:.*}

**Documentation**
* /swagger-ui.html
* /generated/document.html
* /generated/swagger-ui/swagger.json
* /v2/api-docs
* /configuration/security
* /configuration/ui
* /swagger-resources

**Autre**
* /error