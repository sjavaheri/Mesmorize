package ca.montreal.mesmorize.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Item class stores items that the user wants help keeping track of so they can
 * practice them
 * 
 * This may become more sophisticated in the future, but for now there is one
 * class for all items to be memorized
 */
@Entity
public class Item {

    // -----------
    // Enumeration
    // -----------
    public enum ItemType {
        Song, Quote, Prayer
    }

    // -----------
    // Attributes
    // -----------

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String words;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(nullable = false)
    private Date dateLastRevised;

    // Can find the date it was last practiced by getting the most recent practice
    // item associated with it
    // private Date lastPracticed;

    @Column(nullable = false)
    private ItemType itemType;

    @Column(nullable = false)
    private boolean favorite;

    @Column(nullable = false)
    private boolean learnt;

    @Column(nullable = false)
    private String language; 

    @Column(nullable = false)
    private String chords; 

    @Column(nullable = false)
    private Integer secretTimesPracticed; 

    // -----------
    // Associations
    // -----------

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_theme", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "theme_id"))
    private Set<Theme> themes;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "item_id", nullable = true)
    private Set<PracticeSession> practiceSessions;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "source_id", nullable = true)
    private Source source;

    // -----------
    // Constructors
    // -----------

    /**
     * Null constructor
     */
    public Item() {
    }

    /**
     * Constructor to make an Item without an Id
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
     * @param practiceSessions
     * @param source
     * @param language
     * @param chords
     * @param secretTimesPracticed
     * @author Shidan Javaheri
     */
    public Item(String name, String words, Date dateCreated, Date dateLastRevised, ItemType itemType, boolean favorite,
            boolean learnt,
            Account account, Set<Theme> themes, Set<PracticeSession> practiceSessions, String language, String chords, Integer secretTimesPracticed, Source source) {
        this.name = name;
        this.words = words;
        this.dateCreated = dateCreated;
        this.dateLastRevised = dateLastRevised;
        this.itemType = itemType;
        this.favorite = favorite;
        this.learnt = learnt;
        this.account = account;
        this.themes = themes;
        this.practiceSessions = practiceSessions;
        this.source = source;
        this.language = language;
        this.chords = chords;
        this.secretTimesPracticed = secretTimesPracticed;
    }

    // -----------
    // Getters and Setters
    // -----------

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
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

    public Date getDateLastRevised() {
        return dateLastRevised;
    }

    public void setDateLastRevised(Date dateLastRevised) {
        this.dateLastRevised = dateLastRevised;
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

    

}
