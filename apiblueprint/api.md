# /v1/coupons/

## GET

+ Response 200 (application/json)

    + Body

        [
            {
                "id": 1,
                "nom": "coupon name 1",
                "reduction": "coupon reduction 1",
                "estUtilise": false
            },
            {
                "id": 2,
                "nom": "coupon name 2",
                "reduction": "coupon reduction 2",
                "estUtilise": false
            }
        ]


## POST

+ Request (application/json)

    + Body

        {          
            "nom": "coupon name",
            "reduction": "coupon reduction",
            "estUtilise": false
        }

+ Response 201 (application/json)

    + Body

        {
            "id": 1,
            "nom": "coupon name",
            "reduction": "coupon reduction",
            "estUtilise": false
        }


## DELETE

+ Response 204

+ Response 404

    Returned if the coupon was not found



# /v1/coupons/{id}

## GET

Retrieve a coupon by its *id*

+ Response 200 (application/json)

    + Body

        {
            "id": 1,
            "nom": "coupon name",
            "reduction": "coupon reduction",
            "estUtilise": false
        }

+ Response 404

    Returned if the coupon was not found