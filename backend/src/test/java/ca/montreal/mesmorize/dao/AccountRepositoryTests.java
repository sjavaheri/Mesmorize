package ca.montreal.mesmorize.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.util.DatabaseUtil;

/**
 * Testing the Account repository, which saves an app user and all of its
 * attributes
 * 
 * The database is cleared before and after each test
 */
@SpringBootTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Method to clear the database before all tests
     * 
     * @author Shidan Javaheri
     */

    @BeforeAll
    public static void clearDatabaseBefore(@Autowired DatabaseUtil databaseUtil) {
        databaseUtil.clearDatabase();
    }

    /**
     * Method to clear the database after each test
     * 
     * @author Shidan Javaheri
     */

    @AfterEach
    public void clearDatabaseAfter(@Autowired DatabaseUtil databaseUtil) {
        databaseUtil.clearDatabase();
    }

    /**
     * Test the account repository and all of its methods by persisting and loading
     * from the database
     * 
     * @author Shidan Javaheri
     */

     @Test
     public void testPersistAndLoadAccount() {

        // create the account and all of its properties
        Account account = new Account();
        account.setFirstname("Mo");
        account.setLastname("Salah");
        account.setUsername("mo.salah@gmail.com");
        account.setPassword("password");

        // save the account 
        accountRepository.save(account);

        // load the account 
        Account loadedAccount = accountRepository.findAccountByUsername("mo.salah@gmail.com");

        // assert that the account was loaded correctly
        assertEquals(account.getFirstname(), loadedAccount.getFirstname());
        assertEquals(account.getLastname(), loadedAccount.getLastname());
        assertEquals(account.getUsername(), loadedAccount.getUsername());

     }

}
