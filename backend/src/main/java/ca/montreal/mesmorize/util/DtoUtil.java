package ca.montreal.mesmorize.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import ca.montreal.mesmorize.dto.AccountDto;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Account;

/**
 * Utility class for converting objects to DTOs. Adapted from ECSE 428 group
 * project
 */
@Configuration
public class DtoUtil {

    /**
     * Converts a Account to a AccountDto
     *
     * @param account - the Account to be converted
     * @return the converted AccountDto
     * @author Siger Ma
     */
    public AccountDto convertAccountToDto(Account account) {
        if (account == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Account is null");
        }

        // Convert Account to AccountDto
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setUsername(account.getUsername());
        accountDto.setFirstname(account.getFirstname());
        accountDto.setLastname(account.getLastname());
        // leave password blank
        accountDto.setPassword("");
        return accountDto;
    }

}
