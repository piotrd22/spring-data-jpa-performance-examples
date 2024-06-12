package com.example.demo.repositories;

import com.example.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// When retrieving data from the database and no modifications are needed,
// it's recommended to return entities only when necessary.
// For read-only operations or when DTOs are preferred over entities,
// consider mapping the data directly to DTOs within the repository method,
// which can lead to better performance by minimizing unnecessary object instantiation and database round trips.
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("FROM Account a JOIN FETCH a.phoneNumbers WHERE a.id = :id")
    Optional<Account> findByIdWithPhoneNumbers(String id);

    default Account findByIdOrThrow(String id) {
        return findByIdWithPhoneNumbers(id).orElseThrow();
    }

    //    default Account findByIdOrThrow(String id) {
    //        return findById(id).orElseThrow();
    //    }

    // First example of mapping to projection class
    // AccountDto findAccountDtoById(String id);

    // Other Option
    // @Query("SELECT new AccountDto(a.id, a.iban, a.firstName, a.lastName) from Account a WHERE a.id = :id")
    // AccountDto findAccountDtoById(String id);

    // We can also use plain SQL with nativeQuery, but then projection class needs to be an interface
    // @Query("SELECT id, iban, first_name, last_name FROM Account WHERE id = :id", nativeQuery = true)
    // AccountDto findAccountDtoById(String id);

    /**
     * Retrieves a projection of the account entity by its unique identifier in a generic manner.
     * This method allows for querying with various projection classes in a generic way,
     * which can be useful when there are many different projection classes and numerous query variations.
     * @param id the unique identifier of the account
     * @param clazz the class representing the projection
     * @param <T> the type of the projection
     * @return a projection of the account entity
     */
//    <T> T findById(String id, Class<T> clazz);

    /**
     * Retrieves a projection of the account entity by its unique identifier in a generic manner.
     * This method allows for querying with various projection classes in a generic way,
     * which can be useful when there are many different projection classes and numerous query variations.
     * @param id the unique identifier of the account
     * @param clazz the class representing the projection
     * @param <T> the type of the projection
     * @return a projection of the account entity
     */
    <T> Optional<T> findById(String id, Class<T> clazz);
}
