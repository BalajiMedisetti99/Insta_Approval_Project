package com.loan_transaction.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan_transaction.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
	List<Document> findByApplicationApplicationId(Long applicationId);
}
