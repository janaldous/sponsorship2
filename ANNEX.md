# Annex

Project structure inspired by <https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3>

Company House API Client generated using Company House API OpenAPI documentation.
- Generate RestTemplate library using openapi-generator-cli.jar. See <https://www.baeldung.com/spring-boot-rest-client-swagger-codegen>
- See also: <medium.com/zbytes/spring-boot-app-using-java-code-generated-from-openapi-d8d7710e1a04>

Generating sponsorship api client:
- OAS3 was used. See <https://www.baeldung.com/spring-rest-openapi-documentation>

Note about getting TFL underground and DLR stations and zones: see TflLineApiTest.testCallApi_thenWriteCSVToFile to run API calls to fetch stations information. Make sure to manually correct the post code districts.

- Dockerized application + added springdevtools as seen in: <https://www.youtube.com/watch?v=1w1Jv9qssqg>