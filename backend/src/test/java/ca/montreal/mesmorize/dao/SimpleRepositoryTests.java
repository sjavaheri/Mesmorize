package ca.montreal.mesmorize.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.ArrayList;
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
import ca.montreal.mesmorize.util.DatabaseUtil;

/**
 * Tests for the Simple repositories - Source, Theme and PracticeSession
 */

@SpringBootTest
public class SimpleRepositoryTests {

    @Autowired
    DatabaseUtil databaseUtil;

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PracticeSessionRepository practiceSessionRepository;

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
    public void testPersistAndLoadSource() {
        // create and save a source with all of its properties
        Source source = databaseUtil.createAndSaveSource("Book 1", "Arising To Serve", "Ruhi Institute");

        // load the source
        Source loadedSource = sourceRepository.findSourceByTitle("Book 1");

        // assert that the loaded source is the same as the saved source
        assertNotNull(loadedSource.getId(), "The loaded source should have an id");
        assertEquals(source.getTitle(), loadedSource.getTitle());
        assertEquals(source.getAuthor(), loadedSource.getAuthor());
        assertEquals(source.getDescription(), loadedSource.getDescription());

    }

    @Test
    public void testPersistAndLoadTheme() {

        // create and save a theme with all of its properties
        Theme theme = databaseUtil.createAndSaveTheme("Joy", null);

        // load the theme
        Theme loadedTheme = themeRepository.findThemeByName("Joy");

        // assert that the loaded theme is the same as the saved theme
        assertNotNull(loadedTheme.getId(), "The loaded theme should have an id");
        assertEquals(theme.getName(), loadedTheme.getName());

    }

    @Test
    public void testPersistAndLoadPracticeSession() {

        // create an item - requires creating everything else
        
        // create and save the account with all of its properties
        Account account = databaseUtil.createAndSaveAccount("Mo", "Salah", "mo.salah@gmail.com", "password");

        // create and save a source with all of its properties
        Source source = databaseUtil.createAndSaveSource("Book 1", "Arising To Serve", "Ruhi Institute");

        // create and save a theme with all of its properties
        Theme theme = databaseUtil.createAndSaveTheme("Joy", null);

        // create and save an Item with all of its properties
        Set<Theme> themes = new HashSet<Theme>();
        themes.add(theme);
        Set<PracticeSession> practiceSessions = new HashSet<PracticeSession>();
        databaseUtil.createAndSaveItem("O Befriended Stranger",
                "O Befriended Stranger! The candle of thine heart...", Date.from(Instant.now()), ItemType.Song, false,
                false, account, themes,practiceSessions,source);
        
        // get the item 
        Item item = itemRepository.findItemByName("O Befriended Stranger");
        
        // create and save a practice session with all of its properties
        PracticeSession practiceSession = databaseUtil.createAndSavePracticeSession(Date.from(Instant.now()), 10,item);

        // load the practice session
        ArrayList<PracticeSession> loadedPracticeSession = practiceSessionRepository.findPracticeSessionByItemId(item.getId());

        // assert that the loaded practice session is the same as the saved practice session
        assertEquals(loadedPracticeSession.size(), 1, "There should be one practice session");
        assertNotNull(loadedPracticeSession.get(0).getId(), "The loaded practice session should have an id");
        assertEquals(practiceSession.getMinutes(), loadedPracticeSession.get(0).getMinutes());
        assertEquals(practiceSession.getDatePracticed(), loadedPracticeSession.get(0).getDatePracticed());
        


    }

}
