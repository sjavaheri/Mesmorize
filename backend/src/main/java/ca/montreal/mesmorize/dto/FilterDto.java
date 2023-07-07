package ca.montreal.mesmorize.dto;

import ca.montreal.mesmorize.model.Item.ItemType;

/**
 * Data Transfer Object for Filtering Items
 */
public class FilterDto {

    private String name;
    private ItemType itemType;
    private String themeName;
    private String words;
    private Boolean favorite;
    private String language; 
    

    /**
     * Null constructor
     */
    public FilterDto() {
    }
    
    /**
     * Constructor with all attributes
     * @param name
     * @param itemType
     * @param themeName
     * @param words
     * @param favorite
     * @param language
     */
    public FilterDto(String name, ItemType itemType, String themeName, String words, Boolean favorite,
            String language) {
        this.name = name;
        this.itemType = itemType;
        this.themeName = themeName;
        this.words = words;
        this.favorite = favorite;
        this.language = language;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    

}
