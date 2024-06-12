# Performance Oriented Spring Data JPA Examples

This repository contains examples and exercises focusing on performance optimizations in Spring Data JPA, inspired by the enlightening lecture by [Maciej Walkowiak](https://www.youtube.com/watch?v=exqfB1WaqIw) and the related repository [GitHub Repo](https://github.com/maciejwalkowiak/performance-oriented-spring-data-jpa-talk)

### Package Structure

- **connections**: This package contains examples demonstrating efficient management of database connections (connection pooling) to quickly release them, handle transactions, and more
- **cases**: Contains examples with Hibernate and JPA focusing on methods, mapping, N+1 query problems, and other performance-related scenarios
- **domain**: Contains domain models representing entities in the database
- **repositories**: Includes repositories
- **dto**: Contains DTO classes