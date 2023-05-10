package ca.montreal.mesmorize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.montreal.mesmorize.dto.AccountDto;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.service.AccountService;
import ca.montreal.mesmorize.util.DtoUtil;

/**
 * API endpoints for all Account related methods
 */

@RestController
@RequestMapping({ "api/account", "api/account/" })
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    DtoUtil dtoUtil;

    /**
     * Endpoint to create an account
     * 
     * @param accountDto
     * @return a response entity with HTTP status created and the account dto
     * @author Apated from ECSE 428 Group Project, credit Siger Ma
     */
    @PostMapping
    @PreAuthorize("hasAuthority('Server')")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        // Unpack the DTO
        if (accountDto == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "The account dto is null");
        }

        // unpack DTO
        String username = accountDto.getUsername();
        String password = accountDto.getPassword();
        String firstname = accountDto.getFirstname();
        String lastname = accountDto.getLastname();

        // create account
        AccountDto newAccountDto = dtoUtil
                .convertAccountToDto(accountService.createAccount(firstname, lastname, username, password));

        return new ResponseEntity<AccountDto>(newAccountDto, HttpStatus.CREATED);

    }

}
