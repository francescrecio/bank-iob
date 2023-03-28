# iobuilder bank app

API Rest para simular un pequeño banco:
- Registro usuario
- Creación de cuenta (wallet)
- Realización de depósito de dinero
- Visualización de cuenta (wallet) --> Balance y movimientos
- Transferencia de una cuenta A a una cuenta B

### Stack
- Java 11
- Quarkus
- OpenApi
- Maven
- H2
- Hibernate

### Consideraciones técnicas

- No se puede realizar una transferencia desde la cuenta A sin ser el propietario de la misma. Del token JWT extraigo el username
y si no coincide con el de la wallet envio un BAD Request
- Cualquier usuario puede depositar en una cuenta
- Cuando se está realizando una transferencia, bloqueo las dos cuentas implicadas hasta que acaba la transacción. Asi evitamos acciones paralelas en las cuentas que puedan ocasionar fraude o balances negativos.


### Swagger
http://localhost:5001/q/swagger-ui/

### Arranque
mvn quarkus:dev

### Flow

- Registro: POST http://localhost:5001/user/create?username=user&password=pass
- Login para obtener token: POST http://localhost:5001/user/login?username=user&password=pass
- Creacion wallet: POST http://localhost:5001/wallet/create con --header 'Authorization: Bearer {bearer}
- Realización de depósito de dinero: PUT http://localhost:5001/wallet/deposit/{walletId}?amount={cantidad} --header 'Authorization: Bearer {bearer}
- Visualización de cuenta (wallet): GET http://localhost:5001/wallet/balance/{walletId} --header 'Authorization: Bearer {bearer}
- Transferencia de una cuenta A a una cuenta B: POST http://localhost:5001/transfer/create --header 'Authorization: Bearer {bearer}
body json raw:
```shell script
{
"userOrigin":"nuestro id usuario",
"userDestination":"id usuario destino (puede ser el mismo que origen)",
"walletOrigin":"wallet id (tiene que ser de la propiedad del usuario que hace la petición)",
"walletDestination":"wallet id destino",
"transferType":"TRANSFER",
"amount":10
}