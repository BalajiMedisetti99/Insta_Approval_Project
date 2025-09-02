package com.loan_transaction.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan_transaction.entity.LoanType;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
	boolean existsByTypeName(String typeName);
}
