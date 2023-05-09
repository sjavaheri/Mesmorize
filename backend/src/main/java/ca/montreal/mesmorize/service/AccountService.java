package ca.montreal.mesmorize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.SecurityUser;

/**
 * Service for the Account Class
 */
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    /**
     * Shows spring how to find the accounts in the database, so it verify the username and password
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

}
