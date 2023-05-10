package ca.montreal.mesmorize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.SecurityUser;
import ca.montreal.mesmorize.util.DatabaseUtil;
import jakarta.transaction.Transactional;

/**
 * Service for the Account Class
 */
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DatabaseUtil databaseUtil;

    /**
     * Shows spring how to find the accounts in the database, so it verify the
     * username and password
     * 
     * @param username the username of the account
     * @author Shidan Javaheri
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // look for user in database. Tell spring how to get the user
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // security user is an implementation of UserDetails which already knows how to
        // check the password and whether it matches the given username
        return new SecurityUser(account);

    }

    /**
     * Method to save an account. The username and password must both be valid
     * 
     * @param firstname
     * @param lastname
     * @param username  - must be unique and a valid email address
     * @param password  - must be at least 8 characters long and contain at least
     *                  one number, one lowercase character and one uppercase
     *                  character
     * @return the account
     * @author Code adapted from ECSE 428 group project, written by Siger Ma
     */
    @Transactional
    public Account createAccount(String firstname, String lastname, String username, String password) {

        // make sure no other account exists
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "An account with this username already exists");
        }

        // Error validation
        if (username == null || username.isEmpty()) {
            throw new GlobalException(
                    HttpStatus.BAD_REQUEST, "Please enter a valid username. Username cannot be left empty");
        }
        if (firstname == null || firstname.isEmpty()) {
            throw new GlobalException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a valid first name. First name cannot be left empty");
        }
        if (lastname == null || lastname.isEmpty()) {
            throw new GlobalException(
                    HttpStatus.BAD_REQUEST, "Please enter a valid last name. Last name cannot be left empty");
        }
        if (password == null || password.isEmpty()) {
            throw new GlobalException(
                    HttpStatus.BAD_REQUEST, "Please enter a valid password. Password cannot be left empty");
        }
        if (!username.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            throw new GlobalException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a valid username. The username you entered is not valid");
        }
        if (password.length() < 8
                || !password.matches(".*[0-9].*")
                || !password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*")) {
            throw new GlobalException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character");

        }

        // now that validation checks have passed, create and save the account
        Account newAccount = databaseUtil.createAndSaveAccount(firstname, lastname, username, password);
        return newAccount;

    }

}
