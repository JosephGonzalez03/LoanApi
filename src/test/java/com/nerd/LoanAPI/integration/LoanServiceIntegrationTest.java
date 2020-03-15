package com.nerd.LoanAPI.integration;

import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.repository.UserDao;
import com.nerd.LoanAPI.service.LoanService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class LoanServiceIntegrationTest {

    @TestConfiguration
    static class LoanServiceTestContextConfiguration {
        @Bean
        LoanService loanService() {
            return new LoanService();
        }
    }

    @Autowired
    private LoanService loanService;

    @MockBean
    private LoanDao loanDao;

    @MockBean
    private UserDao userDao;

    User Joe = new User();
    Loan a = new Loan();
    Loan b = new Loan();
    List mockedList = new ArrayList();
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

        mockedList.add(a);
        mockedList.add(b);

        Mockito.when(loanDao.findByUserIdOrderByContributionDesc(1))
                .thenReturn(mockedList);
    }

    @Test
    public void whenFindByUserIdOrderByContribution_thenLoanListShouldBeReturned() {
        List<Loan> found = loanDao.findByUserIdOrderByContributionDesc(1);

        expected.add(a);
        expected.add(b);

        Assert.assertEquals(found.get(0), expected.get(0));
        Assert.assertEquals(found.get(1), expected.get(1));
    }
}
