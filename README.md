# Banking Application

How to start the Banking Application application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/bank-app-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

Testing the application
---

There is only one bank account in the `db` it's account `1234` and it always starts with a balance of `100$`.
To login, the account holder is `test` with a pin `1234`

Here are a few curl commands to :
1. Get the current balance of the account `curl -u test:1234 http://localhost:8080/account/1234`
2. Deposit money in the account `curl -X PUT -H "Content-Type: application/json" -u test:1234 -d '{"operationType":"DEPOSIT","amount":100}' http://localhost:8080/account/1234` the return value from this command is the current balance of the account after the deposit.
3. Withdraw money from the account ` curl -X PUT -H "Content-Type: application/json" -u test:1234 -d '{"operationType":"WITHDRAW","amount":100}' http://localhost:8080/account/1234` 

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
