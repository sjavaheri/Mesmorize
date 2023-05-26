package ca.montreal.mesmorize.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ca.montreal.mesmorize.model.Theme;

/**
 * Repository for Theme
 */
public interface ThemeRepository extends CrudRepository<Theme, String> {

    /**
     * Find theme by name
     * 
     * @param name the name of the theme
     * @return a theme
     * @author Shidan Javaheri
     */
    Theme findThemeByNameAndAccountUsername(String name, String username);

    /**
     * Find a theme by its id
     * 
     * @param id
     * @return the theme object, not optional
     * @author Shidan Javaheri
     */
    Theme findThemeById(String id);

    /**
     * Finds a theme belonging to an account and beginning with certain letters if
     * provided
     * 
     * @param username
     * @param name
     * @return an array list of theme objects
     * @author Shidan Javaheri
     */
    ArrayList<Theme> findThemeByAccountUsernameAndNameStartingWithIgnoreCase(String username, String name);

    /**
     * Method to find the themes linked to an account starting with a particular
     * name
     * 
     * @param username
     * @param username2 the default server username, 'server@local.com'
     * @param name
     * @return an array list of themes
     * @author Shidan Javaheri and ChatGPT
     */
    @Query("SELECT t FROM Theme t WHERE LOWER (t.name) LIKE LOWER(CONCAT(:name, '%')) AND (t.account.username = :username OR t.account.username = :username2)")
    ArrayList<Theme> findThemeByNameStartingWithIgnoreCaseAndAccountUsernameOrAccountUsername(
            @Param("name") String name,
            @Param("username") String username,
            @Param("username2") String username2);

    /**
     * Method to find all the themes an account can view, if they have not entered a
     * search query
     * 
     * @param username
     * @param username2
     * @return an array list of themes
     * @author Shidan Javaheri
     */
    ArrayList<Theme> findThemeByAccountUsernameOrAccountUsername(String username, String username2);

}
