# MyRetailApp

The Retail App Microservice is a RESTful API that provides detils/updates price of a specific product by id.

## Technology stack

* [Groovy](http://groovy-lang.org/) - Programming Language
* [SpringBoot](https://projects.spring.io/spring-boot/) - The web framework
* [Spock](http://spockframework.org/) - Unit/Integration testing framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Cassandra](http://cassandra.apache.org/) - Used for data storage
* [Swagger](https://swagger.io/) - API documentation
  
## Getting Started

### Prerequisites

 Running application locally needs installation of Java,Maven,Cassandra. Please follow installation,build processes documented below to make application up & running in local.
```
Java
Maven
Cassandra
```
### Installation

Install Java,Maven and Cassandra. Create keyspace/table.

If Cassandra is not installed download .tar file from [Cassandra Download](http://apache.claz.org/cassandra/3.11.1/apache-cassandra-3.11.4-bin.tar.gz) & extract into a folder(cassandra_home).

Running Cassandra:
```
 sh {cassandra_home}/bin/cassandra -f
```
Creating keyspace/table:

```
sh {cassandra_home}/bin/cqlsh
cqlsh> SOURCE '/{sourceCodeDir}/tables.cql'
```

## Build & Running

   Once in project directory ```mvn clean install spring-boot:run``` will bring application up & running. But for a more traditional way below 2 steps need to be followed
   
  1. Running Tests:
       ```
       mvn clean test
  
  2. Building artifact :
     ```
      mvn clean install
     ```
  3. Running Application:
      ```
       java -jar  target/myRetailApp-0.0.1-SNAPSHOT.jar
       
      ```
  
          health check url    ``` http://localhost:8080/info/health```
          
## Accessing the application 

Swagger : Collapse 'Get Product Information' , Get Product Information asyncronously' and 'Update Product Current Price' 
  drop downs to try it out.
   
   ```
      http://localhost:8080/swagger-ui.html
    
   ```

#####GET API
```
GET /products/{id}
{
    "id": 1234345L, // productId
    "name": "test", // product name
    "current_price": {
        "value": 10.99, // current price of the product
        "currency_code": "USD"
    }
}
```

#####PUT API
```
PUT /product/{id}
Request:
{
    "id": 1234345L, // productId
    "name": "test", // product name
    "current_price": {
        "value": 10.99, // current price of the product
        "currency_code": "USD"
    }
}
```

## Examples

GET Endpoint:
```bash
curl -X GET http://localhost:8080/products/13860428 

```
```json

{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 19.98,
        "current_code": "USD"
    }
}
```

```bash
curl -X GET http://localhost:8080/products/1234344
```
```json
{
    "errorCode": "E404",
    "errorMessage": [
        [
        "PRODUCT_NOT_FOUND"
        ]
    ]
}
```
PUT Endpoint:

```bash    
    curl -X PUT http://localhost:8080/products/13860428 \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'X-CLIENT-ID: MY-RETAIL' \
      -H 'X-CLIENT-SECRET: 543fdc53951c4de0bf57ad066aaa15\
      -d '{
            "id": 13860428,
            "name": "The Big Lebowski (Blu-ray) (Widescreen)",
            "current_price": {
                  "value": 29.99,
                  "currency_code": "USD"
            }
          }
    }
```

## Improvements

* Configuring for cloud configuration 
* Expanding/improving tests 
* Improve Logging 
* Implement event driven approach for each update of product price 
* Improve swagger documentation 
* Using embedded cassandra for Integration testing 

## Author

* **Varsha Jaiswal** - *Initial work*(https://github.com/varshaj/myRetailApp)