The Asset Management Solution
======================================

This is a Spring Boot RESTful application which can serve the following Account operations:

 * Making a transfer between two accounts

How to run
----------

Build the project using gradle. From project root:

```
./gradlew clean build
```

Once the project is built, you have to deploy the war in tomcat. War path is:

```
build\libs\management-0.0.1-SNAPSHOT.war
```

Example request:

```
curl -i -X PUT \
   -H "Content-Type:application/json" \
   -d \
'{
  "accountFromId":"Id-1",
  "accountToId":"Id-2",
  "amount": "1.55"
}' \
 'http://localhost:8080/accounts/transfer'
```

Assumptions
-----------

* You cannot make a transfer to the same account ID. In other words, you cannot send money to yourself.
* Transfer amount is expected upto 2 decimal places For example, 100.45.
* DB connectivity is not created and instead use concurrentHashMap in repository class to store account object.
* We have tested synchronzation with some limited requests not with any tool.

Further improvements
--------------------

* Also request can be handle in a 2-step transfer. In the first step we create a transaction ID and then we request a transfer given this transaction ID. 
  Once a transfer is made the transaction ID cannot be used anymore.
* For thread safe and avoiding deadlocks we can use Lock/Executor framework.
* We can use JMeter or any other tool for further synchronization related testing.
