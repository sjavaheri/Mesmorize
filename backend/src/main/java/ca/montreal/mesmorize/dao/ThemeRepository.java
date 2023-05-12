package ca.montreal.mesmorize.dao;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.Theme;

/**
 * Repository for Theme
 */
public interface ThemeRepository extends CrudRepository<Theme, String> {

    /**
     * Find theme by name
     * @param name the name of the theme
     * @return a theme
     */
    Theme findThemeByNameAndAccountUsername(String name, String username);
    
}
