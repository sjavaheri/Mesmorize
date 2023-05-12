package ca.montreal.mesmorize.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.PracticeSession;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.model.Item.ItemType;
import ca.montreal.mesmorize.util.DatabaseUtilTest;

/**
 * Testing the Item repository, which saves an item and all of its attributes
 * 
 * This invariably tests all other repositories too
 */

@SpringBootTest
public class ItemRepositoryTests {

    @Autowired
    DatabaseUtilTest databaseUtil;

    @Autowired
    ItemRepository itemRepository;

    /**
     * Method to clear the database before all tests
     * 
     * @author Shidan Javaheri
     */

    @BeforeAll
    public static void clearDatabaseBefore(@Autowired DatabaseUtilTest databaseUtil) {
        databaseUtil.clearDatabase();
    }

    /**
     * Method to clear the database after each test
     * 
     * @author Shidan Javaheri
     */

    @AfterEach
    public void clearDatabaseAfter(@Autowired DatabaseUtilTest databaseUtil) {
        databaseUtil.clearDatabase();
    }

    /** 
     * Test persisting and loading an item
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testPersistAndLoadItem() {

        // create and save the account with all of its properties
        Account account = databaseUtil.createAndSaveAccount("Mo", "Salah", "mo.salah@gmail.com", "password");

        // create and save a source with all of its properties
        Source source = databaseUtil.createAndSaveSource("Book 1", "Arising To Serve", "Ruhi Institute");

        // create and save a theme with all of its properties
        Theme theme = databaseUtil.createAndSaveTheme("Joy", account);

        // create and save an Item with all of its properties
        Set<Theme> themes = new HashSet<Theme>();
        themes.add(theme);
        Set<PracticeSession> practiceSessions = new HashSet<PracticeSession>();
        Item item = databaseUtil.createAndSaveItem("O Befriended Stranger",
                "O Befriended Stranger! The candle of thine heart...", Date.from(Instant.now()), ItemType.Song, false,
                false, account, themes,practiceSessions,source);

        // load the item from the database
        Item loadedItem = itemRepository.findItemByName("O Befriended Stranger");

        // assert that the loaded item is the same as the saved item
        assertNotNull(loadedItem.getId(), "The loaded item's id should not be null");
        assertEquals(item.getName(), loadedItem.getName());
        assertEquals(item.getWords(), loadedItem.getWords());
        assertEquals(item.getDateCreated(), loadedItem.getDateCreated());
        assertEquals(item.getItemType(), loadedItem.getItemType());
        assertEquals(item.getAccount().getUsername(), loadedItem.getAccount().getUsername());

    }

}
