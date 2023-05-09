package ca.montreal.mesmorize.dao;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.Account;

/**
 * Repository for Account
 */

public interface AccountRepository extends CrudRepository<Account, String> {

    /**
     * Find account by username
     * @param username the username of the account
     * @return an account
     */
    Account findAccountByUsername(String username);

}