# Exercise

Implement logical expression evaluator.

## Specification
Your application should expose two HTTP endpoints:

### API Definition:

```
/expression
```

### API Input:

This API endpoint should take name of the logical expression and its value:

### API Response:

For each request executed against the API endpoint you should return an unique identifier that represents the identifier of logical expression.

### Workflow:

When this API is being called new logical expression should be created and identifier of newly created logical expression is returned.

### Example:

```
Name: Complex logical expression
```
```
Value: (customer.firstName == "JOHN" && customer.salary < 100) OR (customer.address != null && customer.address.city == "Washington")
```

### API Definition:

```
/evaluate
```

### API Input:

This API endpoint takes expression ID and JSON object as input.

### API Output:

Returns the result of evaluation by using the requested expression and provided JSON object.

### Workflow:

When this API is being called requested logical expression should be evaluated using the provied JSON object.

### Example:

```
{
  "customer":
  {
    "firstName": "JOHN",
    "lastName": "DOE", 
    "address":
    {
      "city": "Chicago",
      "zipCode": 1234, 
      "street": "56th", 
      "houseNumber": 2345
    },
    "salary": 99,
    "type": "BUSINESS"
  }
}
```

## Additional Information
You should use following frameworks for your work.

### Spring JPA
H2 database running in memory (data will not be persistent across application restarts).

### 3rd party libraries
You are free to add/change any libraries which you might need to solve this exercise, except using any 3rd party expression evaluation library (i.e. SpEL, JSONPath or any other). Also the requirement is that we do not have to setup / install any external software to run this application.

### Running the exercise with maven
```mvn spring-boot:run```

### Commiting
You will provide your solution by sending us a link to your repo which contains the solution for this exercise.

![image info](doc/images/docker-logo-blue.png)
#### Building
```docker build -f src/main/docker/Dockerfile .```
#### Building container
```docker build -f src/main/docker/Dockerfile -t expression-evaluator .```
#### Running container localy
```docker run -it -p 8080:8080 expression-evaluator```


![image info](doc/images/logo-landscape.png)
#### Launch app
```fly launch```

App name should be expression-evaluator

#### Deploy
```flyctl deploy```
#### Destroy app 
```fly apps destroy expression-evaluator```

Latest application version is running on https://expression-evaluator.fly.dev/swagger-ui/index.html

![image info](doc/images/postman-logo-orange.png)

[Local Enviroment](/doc/postman/Local.postman_environment.json)

[Fly.io Enviroment](/doc/postman/Fly.io.postman_environment.json)

[Postman collection](/doc/postman/OpenAPI definition.postman_collection.json)


![image info](doc/images/Swagger.png)

Swagger is available locally on http://localhost:8080/swagger-ui/index.html

Also on cloud instance on Fly.io https://expression-evaluator.fly.dev/swagger-ui/index.html
