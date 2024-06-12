package com.example.demo.repositories;

import com.example.demo.domain.BankTransfer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransferRepository extends JpaRepository<BankTransfer, String> {
    default BankTransfer findByIdOrThrow(String id) {
        return findById(id).orElseThrow();
    }

    // To fetch associated sender and receiver accounts eagerly,
    // consider using a custom JPQL query with JOIN FETCH or EntityGraph for performance optimization,
    // as the sender and receiver associations are marked as FetchType.LAZY.

    // @Query("FROM BankTransfer b JOIN FETCH b.sender JOIN FETCH b.receiver WHERE b.id = :senderId")
    @EntityGraph(attributePaths = {"sender", "receiver"})
    List<BankTransfer> findBySenderId(String senderId);
}
