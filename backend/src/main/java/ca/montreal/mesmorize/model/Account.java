package ca.montreal.mesmorize.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.montreal.mesmorize.configuration.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * The Account class, the model for all accounts in the application
 */
@Entity
public class Account {

    // -----------
    // Attributes
    // -----------

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Every Account has a set of Authorities that they can be granted
    private Set<Authority> authorities = new HashSet<Authority>();

    // -----------
    // Constructors
    // -----------
    public Account() {

    }

    /**
     * Create an account wihtout setting its Id
     * 
     * @param firstname
     * @param lastname
     * @param username
     * @param password  that must ALREADY be encoded
     * @param authority
     * @author Shidan Javaheri
     */
    public Account(String firstname, String lastname, String username, String password, Authority authority) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.authorities.add(authority);
    }

    // -----------
    // Getters and Setters
    // -----------

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}