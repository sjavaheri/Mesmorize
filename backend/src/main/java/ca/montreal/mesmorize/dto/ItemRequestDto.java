package ca.montreal.mesmorize.dto;

import java.util.Set;

import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Item.ItemType;

/**
 * Data Transfer Object for creating or updating an item
 */
public class ItemRequestDto {

    private String name;
    private String words;
    private ItemType itemType;
    private boolean favorite;
    private boolean learnt;
    private Set<String> themeIds;
    private Source source;
    private String language;
    private String chords;

    /**
     * Null constructor
     */

    public ItemRequestDto() {
    }

    /**
     * All attributes constructor
     * 
     * @param name
     * @param words
     * @param itemType
     * @param favorite
     * @param learnt
     * @param themeIds
     * @param source
     * @param language
     * @param chords
     */
    public ItemRequestDto(String name, String words, ItemType itemType, boolean favorite, boolean learnt,
            Set<String> themeIds, Source source, String language, String chords) {
        this.name = name;
        this.words = words;
        this.itemType = itemType;
        this.favorite = favorite;
        this.learnt = learnt;
        this.themeIds = themeIds;
        this.source = source;
        this.language = language;
        this.chords = chords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isLearnt() {
        return learnt;
    }

    public void setLearnt(boolean learnt) {
        this.learnt = learnt;
    }

    public Set<String> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(Set<String> themeIds) {
        this.themeIds = themeIds;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getChords() {
        return chords;
    }

    public void setChords(String chords) {
        this.chords = chords;
    }

}
