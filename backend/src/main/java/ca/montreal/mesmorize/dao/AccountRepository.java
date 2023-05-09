package ca.montreal.mesmorize.dao;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.Account;

/**
 * Repository for Account
 */

public interface AccountRepository extends CrudRepository<Account, String> {

    Account findAccountByUsername(String username);

}