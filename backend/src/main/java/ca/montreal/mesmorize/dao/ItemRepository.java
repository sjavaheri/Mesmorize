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
    


}

