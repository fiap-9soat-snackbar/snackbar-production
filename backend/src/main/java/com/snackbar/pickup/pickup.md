 
## 📍Pickup Endpoints

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /api/pickup/notify/{orderId}</kbd>     | See [request details](#pickup-notify)
| <kbd>POST /api/pickup/delivery/{orderId}</kbd>     | See [request details](#pickup-delivery)


<h3 id="pickup-notify">POST /pickup/notify/:orderId</h3>

**RESPONSE**  
```json
{
    "message": "Cliente notificado - Pedido: {orderId}"
}
```

<h3 id="pickup-delivery">POST /pickup/delivery/:orderId</h3>

**RESPONSE**  
```json
{
    "message": "Pedido {orderId} está Finalizado!"
}
```



