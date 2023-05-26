package ca.montreal.mesmorize.dto;

import ca.montreal.mesmorize.model.Theme;

public class ThemeDto {

    private String id; 
    private String name;
    private String accountUsername; 

    /**
     * Null constructor
     */
    public ThemeDto() {
    }

    /**
     * Constructor with Theme Object
     * @param theme
     * @author Shidan Javaheri
     */
    public ThemeDto(Theme theme){ 
        this.id = theme.getId(); 
        this.name = theme.getName(); 
        this.accountUsername = theme.getAccount().getUsername(); 
    }

    /**
     * Constructor with parameters
     * @param id
     * @param name
     * @param accountUsername
     * @author Shidan Javaheri
     */
    public ThemeDto(String id, String name, String accountUsername) {
        this.id = id;
        this.name = name;
        this.accountUsername = accountUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    
    
    
}
