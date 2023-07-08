package ca.montreal.mesmorize.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private String id; 
    private String name;
    private String words;
    private Date dateCreated;
    private Date dateLastRevised;
    private ItemType itemType;
    private boolean favorite;
    private boolean learnt;
    private String username; 
    private Set<String> themeIds;
    private Set<String> practiceSessionIds;
    private String language;
    private String chords;
    private Integer secretTimesPracticed;
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
     * @param dateLastRevised
     * @param itemType
     * @param favorite
     * @param learnt
     * @param account
     * @param themes
     * @param practiceSessionIds
     * @param language
     * @param chords
     * @param secretTimesPracticed
     * @param source
     * @author Shidan Javaheri 
     */
    public ItemDto(String id, String name, String words, Date dateCreated, Date dateLastRevised, ItemType itemType, boolean favorite, boolean learnt,
            String username, Set<String> themeIds, Set<String> practiceSessionIds, String language, String chords, Integer secretTimesPracticed, Source source) {
        
        this.id = id; 
        this.name = name;
        this.words = words;
        this.dateCreated = dateCreated;
        this.dateLastRevised = dateLastRevised;
        this.itemType = itemType;
        this.favorite = favorite;
        this.learnt = learnt;
        this.username = username;
        this.themeIds = themeIds;
        this.practiceSessionIds = practiceSessionIds;
        this.language = language;
        this.chords = chords;
        this.secretTimesPracticed = secretTimesPracticed;
        this.source = source;
    }

    /**
     * Constructor that takes in an Item object
     * 
     * @param item
     * @author Shidan Javaheri
     */
    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.words = item.getWords();
        this.dateCreated = item.getDateCreated();
        this.dateLastRevised = item.getDateLastRevised();
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
        if ( item.getPracticeSessions() != null) {
            for (PracticeSession practiceSession : item.getPracticeSessions()) {
                this.practiceSessionIds.add(practiceSession.getId());
            }
        }
        this.language = item.getLanguage();
        this.source = item.getSource();
        this.chords = item.getChords();
        
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

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getThemeIds() {
        return themeIds;
    }

    public void setThemes(Set<String> themeIds) {
        this.themeIds = themeIds;
    }

    public Set<String> getPracticeSessions() {
        return practiceSessionIds;
    }

    public void setPracticeSessions(Set<String> practiceSessionIds) {
        this.practiceSessionIds = practiceSessionIds;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Date getDateLastRevised() {
        return dateLastRevised;
    }

    public void setDateLastRevised(Date dateLastRevised) {
        this.dateLastRevised = dateLastRevised;
    }

    public void setThemeIds(Set<String> themeIds) {
        this.themeIds = themeIds;
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

    public Integer getSecretTimesPracticed() {
        return secretTimesPracticed;
    }

    public void setSecretTimesPracticed(Integer secretTimesPracticed) {
        this.secretTimesPracticed = secretTimesPracticed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

}