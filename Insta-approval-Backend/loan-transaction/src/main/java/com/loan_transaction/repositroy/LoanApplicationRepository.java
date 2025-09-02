package com.loan_transaction.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan_transaction.domain.LoanStatus;
import com.loan_transaction.entity.LoanApplication;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
	List<LoanApplication> findByStatus(LoanStatus status);
	List<LoanApplication> findByUserId(Long userId);

}
