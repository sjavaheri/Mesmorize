package ca.montreal.mesmorize.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.PracticeSession;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.model.Item.ItemType;

/**
 * Data Transfer Object for an Item
 */
public class ItemDto {

    // Attributes

    private String name;
    private String words;
    private Date dateCreated;
    private ItemType itemType;
    private boolean favorite;
    private boolean learnt;
    private String username; 
    private Set<String> themeIds;
    private Set<PracticeSession> practiceSessions;
    private Source source;

    // Constructors

    /**
     * Null constructor
     */
    public ItemDto() {
    }

    /**
     * Constructor with all attributes
     * 
     * @param name
     * @param words
     * @param dateCreated
     * @param itemType
     * @param favorite
     * @param learnt
     * @param account
     * @param themes
     * @param practiceSessions
     * @param source
     * @author Shidan Javaheri
     */
    public ItemDto(String name, String words, Date dateCreated, ItemType itemType, boolean favorite, boolean learnt,
            String username, Set<String> themeIds, Set<PracticeSession> practiceSessions, Source source) {
        this.name = name;
        this.words = words;
        this.dateCreated = dateCreated;
        this.itemType = itemType;
        this.favorite = favorite;
        this.learnt = learnt;
        this.username = username;
        this.themeIds = themeIds;
        this.practiceSessions = practiceSessions;
        this.source = source;
    }

    /**
     * Constructor that takes in an Item object
     * 
     * @param item
     * @author Shidan Javaheri
     */
    public ItemDto(Item item) {
        this.name = item.getName();
        this.words = item.getWords();
        this.dateCreated = item.getDateCreated();
        this.itemType = item.getItemType();
        this.favorite = item.isFavorite();
        this.learnt = item.isLearnt();
        this.username = item.getAccount().getUsername();

        // create theme Ids
        this.themeIds = new HashSet<String>();

        if (item.getThemes() != null) {
            for (Theme theme : item.getThemes()) {
                this.themeIds.add(theme.getId());
            }
        }
        this.practiceSessions = item.getPracticeSessions();
        this.source = item.getSource();
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getUsername() {
        return username;
    }

    public void setAccount(String username) {
        this.username = username;
    }

    public Set<String> getThemeIds() {
        return themeIds;
    }

    public void setThemes(Set<String> themeIds) {
        this.themeIds = themeIds;
    }

    public Set<PracticeSession> getPracticeSessions() {
        return practiceSessions;
    }

    public void setPracticeSessions(Set<PracticeSession> practiceSessions) {
        this.practiceSessions = practiceSessions;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

}