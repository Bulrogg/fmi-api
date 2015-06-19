// API

[/v1/coupons/],methods=[GET]
[/v1/coupons/{couponId}],methods=[GET]
[/v1/coupons/],methods=[POST]
[/v1/coupons/{couponId}],methods=[DELETE]

// Test api via postman ou curl

ex : curl -H "Content-Type: application/json" -X POST -d '{"nom":"reduction 4","reduction":"1000 euros"}' http://localhost:8080/v1/coupons/

// Run l'api
>> mvn spring-boot:run

// Test E2E
executer les tests de la classe CouponsIntegrationTest.java