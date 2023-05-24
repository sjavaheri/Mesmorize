package ca.montreal.mesmorize.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.Item.ItemType;

/**
 * Repository for Item
 */
public interface ItemRepository extends CrudRepository<Item, String> {

    /**
     * Find all items by account username
     * @param username
     * @return an array list of all items belonging to the account
     */
    ArrayList<Item> findItemByAccountUsername(String username);


    /**
     * Find an item by its unique name and account username
     * @param name
     * @param username
     * @return the item with the given name and account username
     */
    Item findItemByNameAndAccountUsername(String name, String username);

    /**
     * Find an item by its account username and matching words (case insensitive)
     * @param name
     * @param username
     * @return the item with the given name and account username
     */
    ArrayList<Item> findItemByAccountUsernameAndWordsContainingIgnoreCase(String username, String words);
   

    /**
     * Find an item by its account username, item type, and name (case insensitive), and matching words
     * @param username
     * @param itemType
     * @param name
     * @return
     */
    ArrayList<Item> findItemByAccountUsernameAndItemTypeAndNameStartingWithIgnoreCaseAndWordsContainingIgnoreCase(String username, ItemType itemType, String name, String words);


    /**
     * Find an item by its account username, item type, and name (case insensitive)
     * @param username
     * @param itemType
     * @param words
     * @return
     */
    ArrayList<Item> findItemByAccountUsernameAndItemTypeAndNameStartingWithIgnoreCase(String username, ItemType itemType, String name);

    /**
     * Find an item by its account username and item type
     * @param username
     * @param itemType
     * @return
     */
    ArrayList<Item> findItemByAccountUsernameAndItemType(String username, ItemType itemType);
    
    /**
     * Find an item by its account username and item type and matching words
     * @param username
     * @param itemType
     * @return
     */
    ArrayList<Item> findItemByAccountUsernameAndItemTypeAndWordsContainingIgnoreCase(String username, ItemType itemType, String words);

    /**
     * Find an item by its account username and name (case insensitive)
     * @param username
     * @param name
     * @return
     */
    ArrayList<Item> findItemByAccountUsernameAndNameStartingWithIgnoreCase(String username, String name);
    
    /**
     * Find an item by its account username and name (case insensitive) and matching words
     * @param username
     * @param name
     * @return
     */
    ArrayList<Item> findItemByAccountUsernameAndNameStartingWithIgnoreCaseAndWordsContainingIgnoreCase(String username, String name, String words);

}

