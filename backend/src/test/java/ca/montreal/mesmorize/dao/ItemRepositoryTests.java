package ca.montreal.mesmorize.dao;

import javax.xml.crypto.Data;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.util.DatabaseUtil;

/**
 * Testing the Item repository, which saves an item and all of its attributes
 * 
 * This invariably tests all other repositories too
 */

@SpringBootTest
public class ItemRepositoryTests {

    @Autowired
    DatabaseUtil databaseUtil;

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

    @Test
    public void testPersistAndLoadItem() {

        // create and save the account with all of its properties
        Account account = databaseUtil.createAndSaveAccount("Mo", "Salah", "mo.salah@gmail.com", "password");

        // create and save a source with all of its properties
        Source source = databaseUtil.createAndSaveSource("Book 1", "Arising To Serve"); 

        // create and save a theme wiht all of its properties
        Theme theme = databaseUtil.createAndSaveTheme("Joy", null);
        


        

    }

}
