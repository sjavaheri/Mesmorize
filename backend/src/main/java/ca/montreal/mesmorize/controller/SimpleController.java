package ca.montreal.mesmorize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
