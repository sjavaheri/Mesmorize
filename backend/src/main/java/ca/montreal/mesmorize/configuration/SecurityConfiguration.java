package ca.montreal.mesmorize.configuration;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration

/**
 * Configuration for all Spring Security Setttings
 */
public class SecurityConfiguration {

    /**
     * Configures the filters between ther server layer and the controller
     * 
     * @author Shidan Javaheri
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // csrf protection is an extra security layer - prevent cross site request
                // forgery. Adds extra complexity. For post requests, you need extra actions
                .csrf().disable()
                // configuring your project to accept jwt tokens as a method of authentication.
                // Jwt (Json Web Token) tokens are json objects as strings, with no spaces,
                // base64 encoded: headers, payhold, signature
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(new AuthenticationConverter()).and().and()
                // by default, all endpoints are authenticated
                .authorizeHttpRequests().anyRequest().authenticated().and()
                // .oauth2Login().loginPage("/login")
                // .formLogin().and() // two ways to provide authentication to users. Users have
                // username and password - way they provide is either through a form or ( see
                // next )
                .httpBasic().and() // another way - in hhtp request that the backend sends, in headers there is
                                   // "Authorization":"Basic encodeBase64(username:password)" ( encodebase64 is a
                                   // method in javascript method that does base 64 encoding )
                .build(); // you build the object and return a securityFilterChain object
    }

    /**
     * This Bean is used for encoding user passwords
     * 
     * @return an object that can encrypt passwords
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creating the two keys to use in encoder and decoder methods
     * 
     * @return the public and private keys
     * 
     */
    @Bean
    JWTKey rsaKeys() throws Exception {

        // generate an RSA key pair with a key generator object
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        var keyPair = keyGenerator.generateKeyPair();

        // the private key ( used to sign tokens )
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        // validate a token - used to exchange authentication permissions
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();

        return new JWTKey(rsaPrivateKey, rsaPublicKey);
    }

    /**
     * Takes json objects and converts it to JWT token format - with a signature and
     * base64 encoding
     * 
     * @return a NimbusJWT Encoder object
     * @throws Exception
     */
    @Bean
    JwtEncoder jwtEncoder() throws Exception {
        JWK jwk = new RSAKey.Builder(rsaKeys().getPublicKey()).privateKey(rsaKeys().getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Takes in public key, and uses it to decode the JWT. Decoded from base64 to
     * ascii so that it can be validated
     * 
     * @return a Nimbus JWT Decoder object
     * @throws Exception
     */
    @Bean
    JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey(rsaKeys().getPublicKey()).build();
    }

    /*
     * If we use javascript, this is the code for the frontend to encode the
     * username and password upon sending to get the token
     * import * as crypto from 'crypto-js';
     * import Base64 from 'crypto-js/enc-base64';
     * const base64Url = (str) => {
     * return str.toString(Base64).replace(/=/g, '').replace(/\+/g,
     * '-').replace(/\//g, '_');
     * }
     * const encodeBase64 = (str) => {
     * let encodedWord = crypto.enc.Utf8.parse(str)
     * return base64Url(crypto.enc.Base64.stringify(encodedWord));
     * }
     */

}