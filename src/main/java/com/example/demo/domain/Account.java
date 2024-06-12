package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Hibernate annotation used to enable dynamic updates for this entity.
// By default, Hibernate updates all columns of an entity whenever an update operation is performed,
// even if only a subset of columns has changed.
// Enabling dynamic updates ensures that only the modified columns are included in the SQL UPDATE statement,
// reducing the amount of data transferred and potentially improving performance.
// However, using dynamic updates may consume more memory for storing the original state of the entity,
// as Hibernate needs to keep track of the initial state to determine which columns have been modified.
// It's recommended to use dynamic updates when dealing with entities with a large number of columns
// or when optimizing update performance in high-throughput systems.
@DynamicUpdate
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Account {
    @Id
    private String id;

    private String iban;
    private String firstName;
    private String lastName;

    // Lazy by default
    @ElementCollection
    @CollectionTable(
            name = "phone_number",
            joinColumns = @JoinColumn(name = "account_id")
    )
    // https://stackoverflow.com/questions/3742897/hibernate-elementcollection-strange-delete-insert-behavior
    @OrderColumn
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    // Version field used for optimistic locking mechanism.
    // Useful especially when the entity ID is not generated - like in this case
    // During the first persist, there is no need to force an update because the entity doesn't exist in the session yet,
    // therefore Hibernate would fetch it because it doesn't know it's not present.
    // By indicating the version, it informs Hibernate that the entity doesn't exist yet, preventing unnecessary updates.
    // Alternatively, PersistableContext interface can be used.
    // ?Think about make setter protected?
    @Version
    private Long version;

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (!phoneNumbers.contains(phoneNumber)) {
            this.phoneNumbers.add(phoneNumber);
        }
    }

    // https://stackoverflow.com/questions/4388360/should-i-write-equals-and-hashcode-methods-in-jpa-entities
    // https://www.youtube.com/watch?v=jTdMIOfyx2Q
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Account account = (Account) o;
        return getId() != null && Objects.equals(getId(), account.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
