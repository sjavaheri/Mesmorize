package ca.montreal.mesmorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.montreal.mesmorize.configuration.Authority;
import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.dao.ThemeRepository;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.util.DatabaseUtil;

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
	public CommandLineRunner commandLineRunner(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
			ThemeRepository themeRepository, DatabaseUtil databaseUtil) {
		return args -> {

			/** Account Creation at BootStrap */
			if (accountRepository.findAccountByUsername("shidan.javaheri@mail.mcgill.ca") == null) {
				databaseUtil.createAndSaveAccount("Shidan", "Javaheri","shidan.javaheri@mail.mcgill.ca", "Password01");
			}
			;

			/** Account Creation at BootStrap for Frontend Server */
			if (accountRepository.findAccountByUsername("server@local.com") == null) {
				databaseUtil.createAndSaveAccount("Server", "Local","server@local.com", "ka;sp3ru3134i5230;sldjfkpq093481u345klj093845askdjfp");
			}
			;

			/** Theme Creation at BootStrap */
			Account serverAccount = accountRepository.findAccountByUsername("server@local.com"); 
			// Themes that may be used for Prayers and Quotations

			// Aid and Assitance
			if (themeRepository.findThemeByNameAndAccountUsername("Aid and Assistance", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Aid and Assistance", serverAccount);
			}

			// Children
			if (themeRepository.findThemeByNameAndAccountUsername("Children", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Children", serverAccount);
			}

			// Detachment
			if (themeRepository.findThemeByNameAndAccountUsername("Detachment", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Detachment", serverAccount);
			}

			// Youth 
			if (themeRepository.findThemeByNameAndAccountUsername("Youth", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Youth", serverAccount);
			}

			// Joy
			if (themeRepository.findThemeByNameAndAccountUsername("Joy", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Joy", serverAccount);
			}

			// Love	
			if (themeRepository.findThemeByNameAndAccountUsername("Love", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Love", serverAccount);
			}

			// Prayer
			if (themeRepository.findThemeByNameAndAccountUsername("Prayer", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Prayer", serverAccount);
			}

			// Tests and Difficulties
			if (themeRepository.findThemeByNameAndAccountUsername("Tests and Difficulties", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Tests and Difficulties", serverAccount);
			}

			// Consultation
			if (themeRepository.findThemeByNameAndAccountUsername("Consultation", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Consultation", serverAccount);
			}

			// Praise and Gratitude
			if (themeRepository.findThemeByNameAndAccountUsername("Praise and Gratitude", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Praise and Gratitude", serverAccount);
			}

			// Unity
			if (themeRepository.findThemeByNameAndAccountUsername("Unity", "server@local.com") == null) {
				databaseUtil.createAndSaveTheme("Unity", serverAccount);
			}

			// Should we include the Units of Each Book? 

			// The units of Reflections on the Life of the Spirit: Book 1

			// 



			

		};
	}

}
