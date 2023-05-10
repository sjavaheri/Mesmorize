package ca.montreal.mesmorize.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ca.montreal.mesmorize.configuration.Authority;

/**
 * Secure User class that Spring can read, which wraps the Account class
 * Makes Account compatible with Spring Security
 */
public class SecurityUser implements UserDetails {

    // contains the Account object
    private Account account;

    public SecurityUser(Account account) {
        this.account = account;
    }

    /**
     * Method that converts the authorities of the Account into an arraylist of
     * SimpleGrantedAuthoritys
     * 
     * SimpleGrantedAuthorities is an class that Implements the GrantedAuthority
     * interface
     * 
     * @return the authorities the user has as an array list of strings
     * @author Shidan Javaheri
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // array list of simple granted authorities
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        // add the authorities
        for (Authority authority : account.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.toString()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {

        return account.getPassword();
    }

    @Override
    public String getUsername() {

        return account.getUsername();
    }

    // Spring security methods that are required to be true for a user to be
    // authenticated
    
    // For now we can leave them blank

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
