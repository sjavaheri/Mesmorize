package ca.montreal.mesmorize.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ca.montreal.mesmorize.dao.*;

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
     * @author Shidan Javaheri
     */
    public void clearDatabase() {
        practiceSessionRepository.deleteAll();
        itemRepository.deleteAll();
        themeRepository.deleteAll();
        sourceRepository.deleteAll();
        accountRepository.deleteAll();
    }

}
