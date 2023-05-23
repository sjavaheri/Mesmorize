package ca.montreal.mesmorize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.dto.AccountDto;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.service.LoginService;

/**
 * API for authenticating users when they log in
 */

@RestController
@RequestMapping({ "api/login", "api/login/" })
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    AccountRepository accountRepository;

    /**
     * This methods takes in a username and and password of a user, and
     * authenticates them
     * 
     * The username and password are passed in the header of the request with the
     * format: "Authorization":"Basic encodeBase64(username:password)"
     * 
     * encodeBase64 is a function that encodes the username and password in base64
     * format
     * 
     * @return a JWT token if the user is properly authenticated
     * @author Shidan Javaheri
     */
    @PostMapping()
    public String getToken() {
        // get the authentication object from the context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // generate a token from the authentication object for the user
        String token = loginService.generateToken(authentication);
        return token;
    }

    /**
     * This method is used to verify that the user has a valid token
     * @return 
     */
    @GetMapping()
    public ResponseEntity<AccountDto> verifyToken() {

        // if the user accesses the endpoint, it means they have a valid token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findAccountByUsername(authentication.getName());
        return new ResponseEntity<AccountDto>(new AccountDto(account), HttpStatus.OK);

    }

}
