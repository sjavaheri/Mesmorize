package ca.montreal.mesmorize.configuration;

import java.util.ArrayList;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Custom authentication converter - used to convert a JWT token to an
 * authentication object
 */
public class AuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /**
     * Spring method to convert a JWT token to an authentication object
     * 
     * A user sends the token they have to be validated for access, but the token
     * needs to be converted to an authentication object to be understood by spring
     * 
     * This method is used between the server and the API call
     * See the security filter chain method in {@link SecurityConfiguration} }
     * 
     * @author Shidan Javaheri
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        // Intialize an array list of authorities stored as strings
        ArrayList<String> authorities = new ArrayList<String>(source.getClaimAsStringList("grantedAuthorities"));

        // convert these strings into SimpleGrantedAuthorities
        ArrayList<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<SimpleGrantedAuthority>();
        for (String authority : authorities) {
            simpleAuthorities.add(new SimpleGrantedAuthority(authority.toString()));
        }

        // create a new Tken with the new authorities as well as the username of the
        // user
        return new JwtAuthenticationToken(source, simpleAuthorities, source.getClaimAsString("sub"));
    }

}
