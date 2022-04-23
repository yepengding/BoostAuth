# BoostAuth

Authentication system for **Boost Plan**. The Boost Plan is a scaffold for building usable web applications in no time.

## Setup

### Environment

- Java 17
- Maven 3.6+

### Start with Maven Compile

```shell
mvn clean package -D maven.test.skip=true -P [local/dev/prod]
```

## API Document

domain:9000/swagger-ui.html

1. */auth/preregister*  Generate `Identity` with **UUID** and `Token`
2. */auth/register* Make `Identity` valid
3. */auth/login* Sign in
4. */verify* Verify a token
5. */auth/logout* Sign out

---

# References

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Web](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Testing](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html)
- [Springdoc-openapi](https://springdoc.org/)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Lombok](https://projectlombok.org/)
- [auth0 JWT](https://github.com/auth0/java-jwt)