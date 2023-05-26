package ca.montreal.mesmorize.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.montreal.mesmorize.dto.ThemeDto;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.service.SimpleService;

/**
 * API endpoints for very simple methods
 * Includes endpoints for creating a theme
 */
@RestController
public class SimpleController {

    @Autowired
    SimpleService simpleService;

    /**
     * Endpoint to create a theme
     * @param name
     * @return a confirmation message of theme creation
     * @author Shidan Javaheri
     */
    @PostMapping({"api/theme", "api/theme/"})
    public ResponseEntity<String> createTheme(@RequestParam String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        simpleService.createTheme(name, username);
        return new ResponseEntity<String>("Theme created", HttpStatus.CREATED);

    }


    /**
     * Endpoint to get all themes that an account can link things to, optionally with a filter of names
     * @param name the name of the theme
     * @return a response entity with the array list of theme dtos
     * @author Shidan Javaheri
     */
    @GetMapping({"api/theme", "api/theme/"})
    public ResponseEntity<ArrayList<ThemeDto>> getThemes(@RequestParam String name) {
        // get username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // get themes
        ArrayList<Theme> themes = simpleService.getThemes(name, username);

        // convert to dtos and return
        ArrayList<ThemeDto> themeDtos = new ArrayList<ThemeDto>();
        for (Theme theme : themes) {
            ThemeDto themeDto = new ThemeDto(theme);
            themeDtos.add(themeDto);
        }
        
        return new ResponseEntity<ArrayList<ThemeDto>>(themeDtos, HttpStatus.OK);

    }

}
