package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

// For detailed explanations regarding the annotations and methods used here, refer to the corresponding explanations in the Account class.
@DynamicUpdate
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class BankTransfer {
    @Id
    private String id;

    private String reference;

    // Using FetchType.LAZY optimization to delay the retrieval of associated entity until needed.
    // By default, FetchType is EAGER, meaning that associated entity would be fetched eagerly,
    // potentially causing performance issues, especially in scenarios with large datasets.
    // With FetchType.LAZY, associated entity will be fetched only when explicitly accessed,
    // which can result in faster loading times and reduced memory consumption.
    @ManyToOne(fetch = FetchType.LAZY)
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account receiver;

    @Embedded
    private Amount amount;

    @Enumerated(EnumType.STRING)
    private State state;

    @Version
    private Long version;

    public enum State {
        CREATED,
        SETTLED
    }

    public void settle() {
        this.state = State.SETTLED;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BankTransfer that = (BankTransfer) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
