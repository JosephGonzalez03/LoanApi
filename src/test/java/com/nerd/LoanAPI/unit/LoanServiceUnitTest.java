package com.nerd.LoanAPI.unit;

import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.OrderBy;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.repository.UserDao;
import com.nerd.LoanAPI.service.LoanService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static  org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceUnitTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    @Mock
    private UserDao userDao;

    User Joe = new User();
    Loan a = new Loan();
    Loan b = new Loan();
    List<Loan> expected = new ArrayList<>();

    @Before
    public void setup() {
        Joe.setId(1);

        a.setId(1);
        a.setName("a");
        a.setUser(Joe);
        a.setInterestRate(BigDecimal.valueOf(5.00));
        a.setContribution(BigDecimal.TEN);
        a.setOutstandingBalance(BigDecimal.valueOf(1000.00));

        b.setId(2);
        b.setName("b");
        b.setUser(Joe);
        b.setInterestRate(BigDecimal.valueOf(1.00));
        b.setContribution(BigDecimal.ONE);
        b.setOutstandingBalance(BigDecimal.valueOf(2000.00));
    }

    @After
    public void cleanUp() {
        expected.clear();
    }

    @Test
    public void getAll_InterestRateOrderByEnumAs2ndParam_ReturnsLoanListInOrderOfDescendingInterestRate() {
        when(loanDao.findByUserIdOrderByInterestRateDesc(1))
                .thenReturn(Arrays.asList(a, b));

        List<Loan> found = loanService.getAll(1, OrderBy.INTEREST_RATE);

        expected.add(a);
        expected.add(b);

        Assert.assertEquals(found.get(0), expected.get(0));
        Assert.assertEquals(found.get(1), expected.get(1));
    }

    @Test
    public void getAll_OutstandingBalanceOrderByEnumAs2ndParam_ReturnsLoanListInOrderOfDescendingOutstandingBalance() {
        when(loanDao.findByUserIdOrderByOutstandingBalanceDesc(1))
                .thenReturn(Arrays.asList(b, a));

        List<Loan> found = loanService.getAll(1, OrderBy.OUTSTANDING_BALANCE);

        expected.add(b);
        expected.add(a);

        Assert.assertEquals(found.get(0), expected.get(0));
        Assert.assertEquals(found.get(1), expected.get(1));

        verify(loanDao, times(1)).findByUserIdOrderByOutstandingBalanceDesc(1);
    }

    @Test
    public void getAll_ContributionOrderByEnumAs2ndParam_ReturnsLoanListInOrderOfDescendingContribution() {
        when(loanDao.findByUserIdOrderByContributionDesc(1))
                .thenReturn(Arrays.asList(a, b));

        List<Loan> found = loanService.getAll(1, OrderBy.CONTRIBUTION);

        expected.add(a);
        expected.add(b);

        Assert.assertEquals(found.get(0), expected.get(0));
        Assert.assertEquals(found.get(1), expected.get(1));

        verify(loanDao, times(1)).findByUserIdOrderByContributionDesc(1);
    }

    @Test
    public void getAll_NameOrderByEnumAs2ndParam_ReturnsLoanListOrderedAlphabetically() {
        when(loanDao.findByUserIdOrderByName(1))
                .thenReturn(Arrays.asList(a, b));

        List<Loan> found = loanService.getAll(1, OrderBy.NAME);

        expected.add(a);
        expected.add(b);

        Assert.assertEquals(found.get(0), expected.get(0));
        Assert.assertEquals(found.get(1), expected.get(1));

        verify(loanDao, times(1)).findByUserIdOrderByName(1);
    }
}
