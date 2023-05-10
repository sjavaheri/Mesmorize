package ca.montreal.mesmorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.montreal.mesmorize.configuration.Authority;
import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.model.Account;

@SpringBootApplication
public class MesmorizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesmorizeApplication.class, args);
	}

	/**
	 * Command Line Runner to initialize database if needed
	 * 
	 * @param accountRepository to create the user
	 * @param passwordEncoder   to encode the user's password
	 * @author Shidan Javaheri
	 */
	@Bean
	public CommandLineRunner commandLineRunner(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			/** Account CREATION AT BOOTSTRAP */
			if (accountRepository.findAccountByUsername("shidan.javaheri@mail.mcgill.ca") == null) {
				Account account = new Account();
				account.setUsername("shidan.javaheri@mail.mcgill.ca");
				// encode the password
				String encoded = passwordEncoder.encode("Password01");
				account.setPassword(encoded);
				account.setFirstname("Shidan");
				account.setLastname("Javaheri");
				account.getAuthorities().add(Authority.Admin);
				accountRepository.save(account);
			}
			;
		};
	}

}
