package ca.montreal.mesmorize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.dao.ThemeRepository;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.util.DatabaseUtil;

/**
 * Service Class for very simple short methods
 * Includes Theme Class Services
 */
@Service
public class SimpleService {
    
    @Autowired
    ThemeRepository themeRepository;

    @Autowired 
    DatabaseUtil databaseUtil;

    @Autowired
    AccountRepository accountRepository;

    /**
     * Method to create a theme
     * @param name of the theme
     * @param username of the account creating the theme
     * @return the {@link Theme} object
     * @author Shidan Javaheri
     */
    public Theme createTheme(String name, String username){ 

        // check if user created theme exists
        if (themeRepository.findThemeByNameAndAccountUsername(name,username)!= null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "A Theme with this name already exists");
        }

        // check if default theme exists
        if (themeRepository.findThemeByNameAndAccountUsername(name, "server@local.com")!= null){
            throw new GlobalException(HttpStatus.BAD_REQUEST, "A Theme with this name already exists");
        }

        // otherwise create theme
        Theme theme = databaseUtil.createAndSaveTheme(name, accountRepository.findAccountByUsername(username));
        return theme;

    }
    
}
