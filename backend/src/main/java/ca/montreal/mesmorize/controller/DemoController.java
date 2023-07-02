package ca.montreal.mesmorize.controller;

import ca.montreal.mesmorize.configuration.Authority;
import ca.montreal.mesmorize.dao.SourceRepository;
import ca.montreal.mesmorize.dto.FilterDto;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Item.ItemType;
import ca.montreal.mesmorize.util.DatabaseUtil;
import io.swagger.v3.oas.annotations.Hidden;

import java.sql.Date;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API for demonstrating how permissions work for access to endpoints */
@RestController
@RequestMapping({ "/demo", "/demo/" })
public class DemoController {

  @Autowired
  private DatabaseUtil databaseUtil;

  @Autowired
  private SourceRepository sourceRepository;

  /**
   * Method to demonstrate how to restrict access to an endpoint
   *
   * @return a string
   * @throws GlobalException with http status 418 and message "You are a teapot",
   *                         for demo purposes
   */
  @Hidden
  @GetMapping
  @PreAuthorize("hasAuthority('User')")
  public String test() throws GlobalException {
    // Concept #1 - controllers don't need to take usernames as parameters - you get
    // them with the .getName() method of the token
    // all endpoints are secured by default - someone only gets to an endpoint if
    // they are logged in. Get the user like this
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();

    // Concept #2 - every user can have authorities. This is just a string in the
    // context associated with user - it is inside the authenication object
    // authentication.getAuthorities() - gets the list of authorities. A role is an
    // authority with the prefix _.
    // to restrict an endpoint for a particular authority, at the top of the method
    // you add @PreAuthorize to decide if a user has a right to connect to a method
    // ( returns 403 forbidden )
    throw new GlobalException(HttpStatus.I_AM_A_TEAPOT, "You are a teapot");
  }

  /**
   * Method to check the JSON format of Dtos. Change as required
   * 
   * @return the dto of choice
   * @author Shidan Javaheri
   */
  @Hidden
  @PostMapping
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<FilterDto> showDto() {

    Source source = new Source();
    
    // create and save a source with all of its properties
    if (sourceRepository.findSourceByTitle("Book 1") == null) {
      source = databaseUtil.createAndSaveSource("Book 1", "Arising To Serve", "Ruhi Institute");
    } else { 
      source = sourceRepository.findSourceByTitle("Book 1");
    }

    Account validAccount = new Account("Test1", "Lastname", "test1@gmail.com", "a Cool password1", Authority.User);
    Item item = new Item("Hello", "It's Me", Date.from(Instant.now()), Date.from(Instant.now()), ItemType.Prayer, false,
        true,
        validAccount, null, null, "English", "C F Am G", 0, source);

    return new ResponseEntity<FilterDto>(new FilterDto(), HttpStatus.OK);
  }
}