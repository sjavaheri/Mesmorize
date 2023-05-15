package ca.montreal.mesmorize.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.montreal.mesmorize.model.Item;

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
     * Find an item by its unique name
     * @param name
     * @return the item with the given name
     */
    
    Item findItemByName(String name);

    /**
     * Find an item by its unique name and account username
     * @param name
     * @param username
     * @return the item with the given name and account username
     */
    Item findItemByNameAndAccountUsername(String name, String username);
    

}

