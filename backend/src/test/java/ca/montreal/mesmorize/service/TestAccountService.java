package ca.montreal.mesmorize.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.montreal.mesmorize.configuration.Authority;
import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.util.DatabaseUtil;

/**
 * Test the Account Service
 */

@ExtendWith(MockitoExtension.class)
public class TestAccountService {

    // mock the repositories

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DatabaseUtil databaseUtil;

    @InjectMocks
    private AccountService accountService;

    // set the feilds you will use in the tests

    private Account validAccount;

    /**
     * Setup the valid account
     * 
     * @author Shidan Javaheri
     */
    @BeforeEach
    public void setup() {
        this.validAccount = new Account("Test1", "Lastname", "test1@gmail.com", "a Cool password1", Authority.User);
    }

    /**
     * Test creating a valid account 
     * @author Shidan Javaheri
     */

    @Test
    public void createValidAccount(){
        // setup mocks
        when(accountRepository.findAccountByUsername("test1@gmail.com")).thenAnswer((InvocationOnMock invocation) -> null); 
        when(passwordEncoder.encode(any(String.class))).thenAnswer((InvocationOnMock invocation) -> "encodedPassword1");
        when(databaseUtil.createAndSaveAccount(any(String.class), any(String.class), any(String.class), any(String.class))).thenAnswer((InvocationOnMock invocation) -> this.validAccount);

        // call the service method 
        Account account = accountService.createAccount("Test1", "Lastname", "test1@gmail.com", "a Cool password1");

        // assert on result
        assertEquals(account.getUsername(), this.validAccount.getUsername());
        assertEquals(account.getAuthorities().contains(Authority.User), this.validAccount.getAuthorities().contains(Authority.User));

        // verify the repository calls
        verify(databaseUtil,times(1)).createAndSaveAccount(any(String.class), any(String.class), any(String.class), any(String.class));
        verify(accountRepository,times(1)).findAccountByUsername("test1@gmail.com");

    }

    /**
     * Test creating an account with an invalid password
     * @author Shidan Javaheri
     */

    @Test
    public void createAccountInvalidPassword(){

        // setup mocks
        when(accountRepository.findAccountByUsername("test1@gmail.com")).thenAnswer((InvocationOnMock invocation) -> null); 
       
        GlobalException exception = assertThrows(GlobalException.class, () ->
        accountService.createAccount("Test1", "Lastname", "test1@gmail.com", "a lame password"));

        // assertions
        assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getMessage(), "Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character");

        // verify the repository calls
        verify(accountRepository,times(1)).findAccountByUsername("test1@gmail.com");
    }

    /**
     * Test creating an account with an invalid email
     * @author Shidan Javaheri
     */

    @Test
    public void createAccountInvalidEmail(){

        // setup mocks
        when(accountRepository.findAccountByUsername("test1@gmail.com")).thenAnswer((InvocationOnMock invocation) -> this.validAccount);

        GlobalException exception = assertThrows(GlobalException.class, () ->
        accountService.createAccount("Test1", "Lastname", "test1@gmail.com", "a Cool password1"));

        // assertions
        assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getMessage(), "An account with this username already exists");
  
    }
}
