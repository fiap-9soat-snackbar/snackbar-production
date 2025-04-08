 
## 📍Orders Endpoints

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /api/cooking</kbd>     | See [request details](#cooking)
| <kbd>GET /api/cooking/{id}</kbd>     |  See [request details](#get-order-by-id)
| <kbd>POST /api/cooking/receive-order/{id}</kbd>     | See [request details](#receive-order)
| <kbd>POST /api/cooking/start-preparation/{id}</kbd>     | See [request details](#start-preparation)
| <kbd>POST /api/cooking/finish-preparation/{id}</kbd>     | See [request details](#finish-preparation)


<h3 id="cooking">GET /api/cooking</h3>

**RESPONSE**  

- **Response Body**:
```json
[
    {
        "id": "string",
        "orderId": "string",
        "status": "string"
    }
]
```

<h3 id="get-order-by-id">GET /api/cooking/{id}</h3>

**RESPONSE**
```json
{
    "id": "string",
    "orderId": "string",
    "status": "string"
}
```

<h3 id="receive-order">POST /api/cooking/receive-order/{id}</h3>

**RESPONSE**
```json
{
    "id": "string",
    "orderId": "string",
    "status": "string"
}
```

<h3 id="start-preparation">POST /api/cooking/start-preparation/{id}</h3>


**RESPONSE**
```json
{
    "id": "string",
    "orderId": "string",
    "status": "string"
}
```

<h3 id="finish-preparation">POST /api/cooking/finish-preparation/{id}</h3>


**RESPONSE**
```json
{
    "id": "string",
    "orderId": "string",
    "status": "string"
}
```
