# Purpose 

Implement logical expression evaluator.

## Specification
Application consists of two API endpoints:

### API Definition:

```
/expression
```

### API Input:

This API endpoint is responsible to consume name of the logical expression and its value:

Minimum length for name is 2 characters and max length is 50 characters.

Minimum length for expression is 4 characters and max leghth is 500 characters.

### API Response:

For each request executed against the API endpoint UUID is returned that represents the identifier of logical expression.

UUID is defined by RFC 4122 -->  xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (32 hexadecimal digits grouped into five sections separated by hyphens)

### Workflow:

When this API is being called new logical expression is created and identifier of newly created logical expression is returned.

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

ID is a UUID(RFC 4122) returned by /expression endpoint

### API Output:

Returns the result of evaluation by using the requested expression and provided JSON object.

Supported logical operators

 Operator |        Desc         |
|:--------:|:-------------------:|
|   AND    |     Logical and     |
|    &&    |     Logical and     |
|    OR    |     Logical or      |
| \|\|     |     Logical or      |

Relation operators

 Operator |       Desc       |
|:--------:|:----------------:|
|    ==    |      Equal       |
|    !=    |    Not equal     |
|    <     |    Less then     |
|    >     |   Greater then   |
|    >=    | Greater than or Equal to |
|    <=    |    Less Than or Equal    |

Ternary conditional operator is also supported 
    
### Workflow:

When this API is being called requested logical expression is evaluated using the provied JSON object.

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
Following frameworks are used ofr this project.

### Spring Boot
Open-source Java framework

### Spring JPA
H2 database running in memory (data will not be persistent across application restarts).

### Spring JPA
BeanShell - Lightweight Scripting for Java



![image info](doc/images/maven.png)
### Running the exercise with maven
```mvn spring-boot:run```



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

This feature is experimental and will depend on current credit status on platform
    
Latest application version should be running on https://expression-evaluator.fly.dev/swagger-ui/index.html

![image info](doc/images/postman-logo-orange.png)

[Local Enviroment](/doc/postman/Local.postman_environment.json)

[Fly.io Enviroment](/doc/postman/Fly.io.postman_environment.json)

[Postman collection](/doc/postman/OpenAPI_definition.postman_collection.json)


![image info](doc/images/Swagger.png)

Swagger is available locally on http://localhost:8080/swagger-ui/index.html

Also on cloud instance on Fly.io https://expression-evaluator.fly.dev/swagger-ui/index.html
