package ca.montreal.mesmorize.util;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.montreal.mesmorize.configuration.Authority;
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

    @Autowired
    PasswordEncoder passwordEncoder;

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
     * @return the {@link Account} object right before being saved
     * @author Shidan Javaheri
     */
    public Account createAndSaveAccount(String firstname, String lastname, String username, String password) {
        Account account = new Account();
        account.setFirstname(firstname);
        account.setLastname(lastname);
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.getAuthorities().add(Authority.User);
        accountRepository.save(account);
        return account;
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
     * @return the {@link Item object} right before being saved
     * @author Shidan Javaheri
     */
    public Item createAndSaveItem(String name, String words, ItemType itemType, boolean favorite,
            boolean learnt, Account account, Set<Theme> themes, Set<PracticeSession> practiceSessions, Source source) {
        Item item = new Item();
        item.setName(name);
        item.setWords(words);
        item.setDateCreated(Date.from(Instant.now()));
        item.setItemType(itemType);
        item.setFavorite(favorite);
        item.setLearnt(learnt);
        item.setAccount(account);
        item.setThemes(themes);
        item.setPracticeSessions(practiceSessions);
        if (source != null) {
            item.setSource(source);
        }
        itemRepository.save(item);
        return item;
    }

    /**
     * Method to create and save a theme
     * 
     * @param name
     * @param account
     * @return the {@link Theme} object right before being saved
     * @author Shidan Javaheri
     */
    public Theme createAndSaveTheme(String name, Account account) {
        Theme theme = new Theme();
        theme.setName(name);

        if (account != null) {
            theme.setAccount(account);
        }

        themeRepository.save(theme);
        return theme;
    }

    /**
     * Method to create and save a practice session
     * 
     * @param date
     * @param minutes
     * @param item
     * @return the {@link PracticeSession} object right before being saved
     * @author Shidan Javaheri
     */
    public PracticeSession createAndSavePracticeSession(Date datePracticed, int minutes, Item item) {
        PracticeSession practiceSession = new PracticeSession();
        practiceSession.setDatePracticed(datePracticed);
        practiceSession.setMinutes(minutes);
        practiceSession.setItem(item);
        practiceSessionRepository.save(practiceSession);
        return practiceSession;

    }

    /**
     * Method to create and save a source
     * 
     * @param name
     * @param description
     * @param author
     * @return the created {@link Source} object
     * @author Shidan Javaheri
     */
    public Source createAndSaveSource(String title, String description, String author) {
        Source source = new Source();
        source.setTitle(title);
        source.setDescription(description);
        source.setAuthor(author);
        sourceRepository.save(source);
        return source;

    }

}
