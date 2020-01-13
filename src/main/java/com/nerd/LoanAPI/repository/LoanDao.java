package com.nerd.LoanAPI.repository;

import com.nerd.LoanAPI.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer> {

    List<Loan> findByUserIdOrderByInterestRateDesc(Integer userId);
    List<Loan> findByUserIdOrderByOutstandingBalanceDesc(Integer userId);
    List<Loan> findByUserIdOrderByContributionDesc(Integer userId);
    List<Loan> findByUserIdOrderByName(Integer userId);
    Optional<Loan> findByIdAndUserId(Integer id, Integer userId);
}
