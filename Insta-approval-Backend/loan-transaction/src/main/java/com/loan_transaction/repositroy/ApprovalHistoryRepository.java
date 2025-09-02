package com.loan_transaction.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan_transaction.entity.ApprovalHistory;

@Repository
public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory, Long> {
	List<ApprovalHistory> findByApplicationApplicationId(Long applicationId);
}
