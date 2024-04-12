# Gateway Microservice
## 1.Service structure of the gateway in the project:
![](/resources/gateway.png)
## 2.The global filter implements jwt verification:
![](/resources/jwt.png)
## 3.Process Analysis:
* When the user enters the gateway, the gateway filter(GlobalFilter) determines the login. If the user logs in, the user is routed to the background management microservice for login
* After the user successfully logs in, the background management microservice issues the JWT TOKEN information and returns it to the user
* The user accesses the gateway again, and the gateway filter receives the TOKEN carried by the user
* The gateway filter parses the TOKEN to determine whether it has permission. If it does, it permits it. If it does not, it returns an unauthenticated error