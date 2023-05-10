package ca.montreal.mesmorize.model;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The PracticeSession Class, to store a history of each time a quote is
 * practiced
 */
@Entity
public class PracticeSession {

    // -----------
    // Attributes
    // -----------

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private Date datePracticed;

    @Column(nullable = false)
    private int minutes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // -----------
    // Getters and Setters
    // -----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDatePracticed() {
        return datePracticed;
    }

    public void setDatePracticed(Date datePracticed) {
        this.datePracticed = datePracticed;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    

}
