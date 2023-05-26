package ca.montreal.mesmorize.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.dao.ThemeRepository;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.util.DatabaseUtil;
import jakarta.transaction.Transactional;

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
    @Transactional
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

    /**
     * Method to return a list of Themes matching the name criteria if provided
     * @param name
     * @param username
     * @return an array list of {@link Theme} objects
     * @author Shidan Javaheri 
     */
    @Transactional
    public ArrayList<Theme> getThemes(String name, String username) { 
        
        ArrayList<Theme> themes = new ArrayList<Theme>(); 
        if (!name.equals("")){
            themes = themeRepository.findThemeByNameStartingWithIgnoreCaseAndAccountUsernameOrAccountUsername(name, username, "server@local.com"); 
        } else { 
            themes = themeRepository.findThemeByAccountUsernameOrAccountUsername(username, "server@local.com"); 
        }
        if (themes.isEmpty()){
            throw new GlobalException(HttpStatus.NOT_FOUND, "There are not themes mathcing the given criteria");
        }
        return themes;
    }
    
}
