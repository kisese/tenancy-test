# Getting Started

### Reference Documentation

The following is a spring boot microservice that allows for multitenancy (based on a user<>organization relationship) as
well as tenant customization.

### API Documentation

API Documentation has been handled through swagger docs. Access the docs at `http://localhost:8080/swagger-ui.html`

### Multitenancy Support (Top Level)
For Hosting the application, im using an open source PAAS tool called CapRover (https://caprover.com/). This allows for easy deployment of the
application to a cloud provider. The application is hosted on a Digital Ocean Droplet.

#### Multitenancy Support (Application Level)

For application level we have a single instance of a spring boot application and SQLITE database that is able to handle multiple tenants. The tenants are segregated by Organization. Each organization has a unique identifier that is used to differentiate between tenants.

A more advanced setup with subdomain routing can be achieved by modifying NGINX configurations on Caprover to route requests to the correct tenant based on the subdomain.

### Validation

For input validation a mix of JSR-303 annotations and custom validation annotations have been used. The custom validation annotations are used to validate the uniqueness of the organization name and the organization code.

For multiple subsequent validations we have applied a delegation of control pattern to validators that can be extended to handle more complex validation scenarios.

### Pagination

Pagination has been implemented using the Pageable interface provided by Spring Data JPA. The Pageable interface allows for easy pagination of data in the database.

### Running the Application

A live version of the application is available at:

- https://tenancy.apps.kisese.com/swagger-ui/index.html
- https://tenancy.apps.kisese.com/
- https://tenancy_ui.apps.kisese.com/

To run the application, you can use the following command:

    ```shell
    ./gradlew bootRun
    ```

The application will start on port 8080 by default. You can access the application at `http://localhost:8080`

### Running the Tests

To run the tests, you can use the following command:

    ```shell
    ./gradlew test
    ```

### TODO

Due to time constraints, the following features are yet to be implemented:
- Testcontainers for integration tests
- ID obfuscation for security using TSIDs or UUIDs