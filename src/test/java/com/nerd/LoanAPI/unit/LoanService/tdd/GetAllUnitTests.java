package com.nerd.LoanAPI.unit.LoanService.tdd;

import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.OrderBy;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.service.LoanService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class GetAllUnitTests {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    User userWithExistentUserId = new User();
    Loan validLoanA = new Loan();
    Loan validLoanB = new Loan();
    List<Loan> expected = new ArrayList<>();

    @Before
    public void setup() {
        userWithExistentUserId.setId(1);

        validLoanA.setId(1);
        validLoanA.setName("validLoanA");
        validLoanA.setUser(userWithExistentUserId);
        validLoanA.setInterestRate(BigDecimal.valueOf(5.00));
        validLoanA.setContribution(BigDecimal.TEN);
        validLoanA.setOutstandingBalance(BigDecimal.valueOf(1000.00));

        validLoanB.setId(2);
        validLoanB.setName("validLoanB");
        validLoanB.setUser(userWithExistentUserId);
        validLoanB.setInterestRate(BigDecimal.valueOf(1.00));
        validLoanB.setContribution(BigDecimal.ONE);
        validLoanB.setOutstandingBalance(BigDecimal.valueOf(2000.00));
    }

    @After
    public void cleanUp() {
        expected.clear();
    }

    @Test
    public void getAll_ExistentUserIdAs1stParam_InterestRateOrderByEnumAs2ndParam_ReturnsLoanListInOrderOfDescendingInterestRate() {
        when(loanDao.findByUserIdOrderByInterestRateDesc(1))
                .thenReturn(Arrays.asList(validLoanA, validLoanB));

        List<Loan> found = loanService.getAll(1, OrderBy.INTEREST_RATE);

        expected.add(validLoanA);
        expected.add(validLoanB);

        Assert.assertEquals(expected.get(0), found.get(0));
        Assert.assertEquals(expected.get(1), found.get(1));

        verify(loanDao, times(1)).findByUserIdOrderByInterestRateDesc(1);
    }

    @Test
    public void getAll_ExistentUserIdAs1stParam_OutstandingBalanceOrderByEnumAs2ndParam_ReturnsLoanListInOrderOfDescendingOutstandingBalance() {
        when(loanDao.findByUserIdOrderByOutstandingBalanceDesc(1))
                .thenReturn(Arrays.asList(validLoanB, validLoanA));

        List<Loan> found = loanService.getAll(1, OrderBy.OUTSTANDING_BALANCE);

        expected.add(validLoanB);
        expected.add(validLoanA);

        Assert.assertEquals(expected.get(0), found.get(0));
        Assert.assertEquals(expected.get(1), found.get(1));

        verify(loanDao, times(1)).findByUserIdOrderByOutstandingBalanceDesc(1);
    }

    @Test
    public void getAll_ExistentUserIdAs1stParam_ContributionOrderByEnumAs2ndParam_ReturnsLoanListInOrderOfDescendingContribution() {
        when(loanDao.findByUserIdOrderByContributionDesc(1))
                .thenReturn(Arrays.asList(validLoanA, validLoanB));

        List<Loan> found = loanService.getAll(1, OrderBy.CONTRIBUTION);

        expected.add(validLoanA);
        expected.add(validLoanB);

        Assert.assertEquals(expected.get(0), found.get(0));
        Assert.assertEquals(expected.get(1), found.get(1));

        verify(loanDao, times(1)).findByUserIdOrderByContributionDesc(1);
    }

    @Test
    public void getAll_ExistentUserIdAs1stParam_NameOrderByEnumAs2ndParam_ReturnsLoanListOrderedAlphabetically() {
        when(loanDao.findByUserIdOrderByName(1))
                .thenReturn(Arrays.asList(validLoanA, validLoanB));

        List<Loan> found = loanService.getAll(1, OrderBy.NAME);

        expected.add(validLoanA);
        expected.add(validLoanB);

        Assert.assertEquals(expected.get(0), found.get(0));
        Assert.assertEquals(expected.get(1), found.get(1));

        verify(loanDao, times(1)).findByUserIdOrderByName(1);
    }
}
