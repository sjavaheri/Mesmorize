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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * API endpoints for very simple methods
 * Includes endpoints for creating a theme
 */
@RestController
@Tag(name = "Simple Classes API", description = "API endpoints for simple classes")
public class SimpleController {

    @Autowired
    SimpleService simpleService;

    /**
     * Endpoint to create a theme
     * @param name
     * @return a confirmation message of theme creation
     * @author Shidan Javaheri
     */
    @PostMapping({"api/theme"})
    @Operation(summary="Create a Theme", description="Endpoint to create a Theme that belongs to a user's account")
    @Parameter(name="name", description="The name of the theme that is being created ", required=true)
    @ApiResponse(responseCode="200", description="Returns a string confirming that the theme was created")
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
    @GetMapping({"api/theme"})
    @Operation(summary="Get Themes", description="Endpoint to search for both default and custom themes")
    @Parameter(name="name", description="The name of the theme that is being searched for ", required=false)
    @ApiResponse(responseCode="200", description="Retunrs an array list of theme DTOs that match the search criteria")
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
