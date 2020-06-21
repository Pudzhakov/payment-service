### Description
This is the simple payment service completed as test task.

### Guides
The following guides illustrate how start project and use some features:

* step 1: To start a project you need build project to executable jar file
then use command console with the next command:
java -jar -Dspring.profiles.active=local payment_service-0.0.1.jar
* step 2: there are some features in this service such as:
    * Transfer money (/api/payment/v1/accounts/money/transfer)
    * Put money (/api/payment/v1/accounts/money/put)
    * Withdraw money (/api/payment/v1/accounts/money/withdraw)
    * Get account info (/api/payment/v1/accounts/{id}) 