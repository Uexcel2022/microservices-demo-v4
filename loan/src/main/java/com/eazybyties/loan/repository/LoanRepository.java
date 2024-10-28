package com.eazybyties.loan.repository;

import com.eazybyties.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    boolean existsByMobileNumber(String mobileNumber);
    Optional<Loan> findByLoanNumberOrMobileNumber(String loanNumber, String mobileNumber);
}
