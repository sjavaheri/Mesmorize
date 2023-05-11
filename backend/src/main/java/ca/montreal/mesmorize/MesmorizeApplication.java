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

			/** Account Creation at BootStrap */
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
			};

			/** Account Creation at BootStrap for Frontend Server */
			if (accountRepository.findAccountByUsername("server@local.com") == null) {
				Account account = new Account();
				account.setUsername("server@local.com");
				// encode the password
				String encoded = passwordEncoder.encode("ajsdhfqoew134509lasdfq3452k2345hk34jb523j46kj7456j745j6b2456jb");
				account.setPassword(encoded);
				account.setFirstname("Server");
				account.setLastname("Local");
				account.getAuthorities().add(Authority.Server);
				accountRepository.save(account);
			};




		};
	}

}
