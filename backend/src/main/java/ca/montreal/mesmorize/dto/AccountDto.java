package ca.montreal.mesmorize.dto;

import org.springframework.http.HttpStatus;

import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Account;

/**
 * Data transfer object for the AppUser class.
 * Adapted from code for ECSE 428 Group Project
 */

public class AccountDto {

    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    /** Default constructor for AppUserDto. */
    public AccountDto() {
    }

    /**
     * Constructor for AppUserDto.
     *
     * @param username  - the username of the user
     * @param password  - the password of the user
     * @param firstname - the firstname of the user
     * @param lastname  - the lastname of the user
     * @author Shidan Javaheri
     */

    public AccountDto(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

        /**
     * Converts a Account to a AccountDto
     *
     * @param account - the Account to be converted
     * @return the converted AccountDto with nothing in the password feild
     * @author Shidan Javaheri
     */
    public AccountDto(Account account) {
        if (account == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Account is null");
        }
        // Convert Account to AccountDto
        this.id = account.getId();
        this.username = account.getUsername();
        this.firstname = account.getFirstname();
        this.lastname = account.getLastname();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


