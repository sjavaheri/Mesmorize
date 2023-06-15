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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * API endpoints for all Account related methods
 */

@RestController
@RequestMapping({ "api/account" })
@Tag(name="Account API", description="API endpoints for all Account related methods")
public class AccountController {

    @Autowired
    AccountService accountService;

    /**
     * Endpoint to create an account with a User Authority. For now, Admin accounts
     * cannot be created from the frontend
     * 
     * @param accountDto
     * @return a response entity with HTTP status created and the account dto
     * @author Apated from ECSE 428 Group Project, credit Siger Ma
     */
    @PostMapping
    @PreAuthorize("hasAuthority('Server')")
    @Operation(summary="Create an Account", description="Endpoint to create an account with User Authority. For now, Admin accounts cannot be created from the frontend")
    @Parameter(name="AccountDto", description="An account DTO with containing the account information", required=true)
    @ApiResponse(responseCode="200", description="Returns the account DTO with the password feild left empty")
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
        AccountDto newAccountDto = new AccountDto(accountService.createAccount(firstname, lastname, username, password));

        return new ResponseEntity<AccountDto>(newAccountDto, HttpStatus.CREATED);

    }

}