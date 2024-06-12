package com.example.demo.repositories;

import com.example.demo.domain.Person;
import org.springframework.data.repository.ListCrudRepository;

public interface PersonRepository extends ListCrudRepository<Person, String> {
}
