package ca.montreal.mesmorize.util;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ca.montreal.mesmorize.dao.*;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.PracticeSession;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.model.Item.ItemType;

/** Class for reusable database methods needed across other classes */
@Configuration
public class DatabaseUtil {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PracticeSessionRepository practiceSessionRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private ThemeRepository themeRepository;

    /**
     * Method to clear the database
     * 
     * @author Shidan Javaheri
     */
    public void clearDatabase() {
        practiceSessionRepository.deleteAll();
        itemRepository.deleteAll();
        themeRepository.deleteAll();
        sourceRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /**
     * Method to create and save an account
     * 
     * @param firstname
     * @param lastname
     * @param username
     * @param password
     * @return the created {@link Account} object
     * @author Shidan Javaheri
     */
    public Account createAndSaveAccount(String firstname, String lastname, String username, String password) {
        Account account = new Account();
        account.setFirstname(firstname);
        account.setLastname(lastname);
        account.setUsername(username);
        account.setPassword(password);
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    /**
     * Method to create and save an item
     * 
     * @param name
     * @param words
     * @param dateCreated
     * @param itemType
     * @param favorite
     * @param learnt
     * @param account
     * @param themes
     * @param practiceSessions
     * @param source
     * @return the created {@link Item object}
     * @author Shidan Javaheri
     */
    public Item createAndSaveItem(String name, String words, Date dateCreated, ItemType itemType, boolean favorite,
            boolean learnt, Account account, Set<Theme> themes, List<PracticeSession> practiceSessions, Source source) {
        Item item = new Item();
        item.setName(name);
        item.setWords(words);
        item.setDateCreated(dateCreated);
        item.setItemType(itemType);
        item.setFavorite(favorite);
        item.setLearnt(learnt);
        item.setAccount(account);
        item.setThemes(themes);
        item.setPracticeSessions(practiceSessions);
        item.setSource(source);
        Item savedItem = itemRepository.save(item);
        return savedItem;
    }

    /**
     * Method to create and save a theme
     * 
     * @param name
     * @param account
     * @return the created {@link Theme} object
     * @author Shidan Javaheri
     */
    public Theme createAndSaveTheme(String name, Account account) {
        Theme theme = new Theme();
        theme.setName(name);
        theme.setAccount(account);
        Theme savedTheme = themeRepository.save(theme);
        return savedTheme;
    }

    /**
     * Method to create and save a practice session
     * 
     * @param date
     * @param account
     * @return the created {@link PracticeSession} object
     * @author Shidan Javaheri
     */
    public PracticeSession createAndSavePracticeSession(Date datePracticed, int minutes) {
        PracticeSession practiceSession = new PracticeSession();
        practiceSession.setDatePracticed(datePracticed);
        practiceSession.setMinutes(minutes);
        PracticeSession savedPracticeSession = practiceSessionRepository.save(practiceSession);
        return savedPracticeSession;

    }

    /**
     * Method to create and save a source
     * 
     * @param name
     * @param account
     * @return the created {@link Source} object
     * @author Shidan Javaheri
     */
    public Source createAndSaveSource(String title, String description) {
        Source source = new Source();
        source.setTitle(title);
        source.setDescription(description);
        Source savedSource = sourceRepository.save(source);
        return savedSource;

    }

}
