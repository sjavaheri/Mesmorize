package ca.montreal.mesmorize.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.dao.ItemRepository;
import ca.montreal.mesmorize.dao.ThemeRepository;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.model.Item.ItemType;
import ca.montreal.mesmorize.util.DatabaseUtil;

/**
 * Service method for the item class
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DatabaseUtil databaseUtil;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Method to create an item
     * 
     * @param name
     * @param words
     * @param itemType
     * @param favorite
     * @param learnt
     * @param themeIds
     * @param source
     * @param username
     * @return an {@link Item} object
     * @author Shidan Javaheri
     */
    public Item createItem(String name, String words, ItemType itemType, boolean favorite,
            boolean learnt, Set<String> themeIds, Source source, String username) {

        // make sure account doesn't already have another item of same name
        if (itemRepository.findItemByNameAndAccountUsername(name, username) != null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "An Item with this name already exists");
        }

        // make sure item name is not more than 100 characters
        if (name.length() > 100) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Item name cannot be more than 100 characters");
        }

        // make sure no input feild is null
        if (name == null || words == null || itemType == null || username == null ) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "One or more input fields are null");
        }

        // get themes
        Set<Theme> themesList = new HashSet<Theme>();
        if (themeIds != null) {
            for (String themeId : themeIds) {
                Theme theme = themeRepository.findThemeById(themeId);
                if (theme == null) {
                    throw new GlobalException(HttpStatus.BAD_REQUEST, "Theme with id " + themeId + " does not exist");
                }
                themesList.add(theme);
            }
        }

        // create and save item
        Item newItem = databaseUtil.createAndSaveItem(name, words, itemType, favorite, learnt,
                accountRepository.findAccountByUsername(username), themesList, null, source);

        return newItem;

    }

}
