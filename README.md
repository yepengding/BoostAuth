# BoostAuth

Authentication system for **Boost Plan**. The **Boost Plan** is a scaffold for building usable web applications in no
time.

BoostAuth provides a unified authentication and authorization service for all other systems provided in the Boost Plan.
It uses JWT (JSON Web Token) style for the main functionalities, including: register, login, logout, email service, and
token verification.

## Features

- [x] Token-based authentication and authorization
- [x] Fully-fledged access control
- [x] Management system with frontend
- [x] Delegation support
- [x] Email service
- [x] Admin control
- [x] Group control
- [x] Docker-based deployment
- [ ] Clients in multiple languages (Python, JavaScript, etc.)

## Setup

### Environment

- Java 11
- Maven 3.6+

### Configuration

> src/main/resources/application[-dev/-local/-prod].yml

### Start with Docker

```shell
docker-compose up
```

### Start with Maven

```shell
mvn clean package -D maven.test.skip=true -P [local/dev/prod]
```

## API Document

domain:9000/swagger-ui.html

Authentication logic:

1. */auth/preregister*  Generate `Identity` with **UUID** and `Token`
2. */auth/register* Make `Identity` valid
3. */auth/login* Sign in
4. */verify* Verify a token
5. */auth/logout* Sign out
6. */auth/reset/password* Reset password

## Frontend

### Admin

- Manage preregistration: `/admin/view/manage/preregister`
- Manage group: `/admin/view/manage/group`

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