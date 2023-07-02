package ca.montreal.mesmorize.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.dao.ItemRepository;
import ca.montreal.mesmorize.dao.SourceRepository;
import ca.montreal.mesmorize.dao.ThemeRepository;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.model.Item.ItemType;
import ca.montreal.mesmorize.util.DatabaseUtil;
import jakarta.transaction.Transactional;

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
    private SourceRepository sourceRepository;

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
     * @param language
     * @param chords
     * @param source
     * @param username
     * @return an {@link Item} object
     * @author Shidan Javaheri
     */
    @Transactional
    public Item createItem(String name, String words, ItemType itemType, boolean favorite,
            boolean learnt, Set<String> themeIds, String language, String chords, Source source, String username) {

        // make sure account doesn't already have another item of same name
        if (itemRepository.findItemByNameAndAccountUsername(name, username) != null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "An Item with this name already exists");
        }

        // check if the source exists, if not create a new one
        if (source != null) {
            if (sourceRepository.findSourceByTitle(source.getTitle()) == null) {
                sourceRepository.save(source);
            }
        }

        // make sure item name is not more than 100 characters
        if (name.length() > 100) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Item name cannot be more than 100 characters");
        }
        
        // make sure item language is not more than 100 characters
        if (language.length() > 100) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Item language cannot be more than 100 characters");
        }
        // make sure item chords are not more than 100 characters
        if (chords.length() > 100) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Item chords cannot be more than 100 characters");
        }

        // make sure no input feild is null
        if (name == null || words == null || itemType == null || username == null || language == null || chords == null) {
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
                accountRepository.findAccountByUsername(username), themesList, null,language, chords, 0, source);

        return newItem;

    }

    /**
     * Filter items in the database. Name, Itemtype, Theme, Words and Boolean are
     * optional
     * 
     * @param name
     * @param itemType
     * @param themeName
     * @param words
     * @param favorite
     * @param username
     * @return an array list with {@link Item} objects
     * @author Shidan Javaheri 
     */
    @Transactional
    public ArrayList<Item> filterItems(String name, ItemType itemType, String themeName, String words, Boolean favorite,
            String username) {

        ArrayList<Item> items = new ArrayList<Item>();

        // depending on which feilds are present, call the right function

        // null name
        if (name == null) {
            // not null itemtype
            if (itemType != null) {
                if (words == null) {
                    items = itemRepository.findItemByAccountUsernameAndItemType(username, itemType);
                } else {
                    items = itemRepository.findItemByAccountUsernameAndItemTypeAndWordsContainingIgnoreCase(username,
                            itemType, words);
                }
                // null itemtype
            } else {
                if (words == null) {
                    items = itemRepository.findItemByAccountUsername(username);
                } else {
                    items = itemRepository.findItemByAccountUsernameAndWordsContainingIgnoreCase(username, words);
                }
            }
            // not null name
        } else {
            // not null itemtype
            if (itemType != null) {
                if (words == null) {
                    items = itemRepository.findItemByAccountUsernameAndItemTypeAndNameStartingWithIgnoreCase(username,
                            itemType, name);
                } else {
                    items = itemRepository
                            .findItemByAccountUsernameAndItemTypeAndNameStartingWithIgnoreCaseAndWordsContainingIgnoreCase(
                                    username, itemType, name, words);
                }
                // null itemtype
            } else {
                if (words == null) {
                    items = itemRepository.findItemByAccountUsernameAndNameStartingWithIgnoreCase(username, name);
                } else {
                    items = itemRepository
                            .findItemByAccountUsernameAndNameStartingWithIgnoreCaseAndWordsContainingIgnoreCase(
                                    username, name, words);
                }
            }
        }

        // if theme is present, filter items by theme
        if (themeName != null) {
            ArrayList<Item> filteredItems = new ArrayList<Item>();
            for (Item item : items) {
                for (Theme theme : item.getThemes()) {
                    if (theme.getName().equalsIgnoreCase(themeName)) {
                        filteredItems.add(item);
                    }
                }
            }
            items = filteredItems;
        }

        // if favorite is present, filter items by favorite
        if (favorite != null) {
            ArrayList<Item> filteredItems = new ArrayList<Item>();
            for (Item item : items) {
                if (item.isFavorite() == favorite) {
                    filteredItems.add(item);
                }
            }
            items = filteredItems;
        }

        // check to make sure Items matched criteria
        if (items.size() <= 0) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "No items match the given criteria");
        }

        // sort items by oldest revisited time (last item in the list is the oldest)
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                Date date1 = item1.getDateLastRevised();
                Date date2 = item2.getDateLastRevised();
                return date1.compareTo(date2);

            }
        });

        return items;
    }

    /**
     * Method to be recommended an item based on what you haven't said in the
     * longest time
     * 
     * @param username
     * @param themeName
     * @return an {@link Item} object
     * @author Shidan Javaheri 
     */
    @Transactional
    public Item recommendItem(String username, String themeName, ItemType itemType, Boolean favorite) {

        // find all items this user has
        ArrayList<Item> items = itemRepository.findItemByAccountUsername(username);
        ArrayList<Item> filteredItems = new ArrayList<Item>();

        // recommend by theme
        if (themeName != null) {
            for (Item item : items) {
                for (Theme theme : item.getThemes()) {
                    if (theme.getName().equalsIgnoreCase(themeName)) {
                        filteredItems.add(item);
                    }
                }
            }
        } else { 
            filteredItems = items;
        }

        // reccomend by itemType
        if (itemType != null) {
            ArrayList<Item> temp = new ArrayList<Item>();
            for (Item item : filteredItems) {
                if (item.getItemType().equals(itemType)) {
                    temp.add(item);
                }
            }
            filteredItems = temp;
        }

        // recommend by favorite 
        if (favorite != null) {
            ArrayList<Item> temp = new ArrayList<Item>(); 
            for (Item item : filteredItems){
                if (favorite.equals(Boolean.valueOf(item.isFavorite()))){
                    temp.add(item);
                }
            }
            filteredItems = temp; 
        }

        // sort items by oldest revisited time (last item in the list is the oldest)
        Collections.sort(filteredItems, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                Date date1 = item1.getDateLastRevised();
                Date date2 = item2.getDateLastRevised();
                return date2.compareTo(date1);

            }
        });

        ArrayList<Item> oldItems = new ArrayList<Item>();
        // based on list size, select a pool of oldest items
        int poolSize = 0;
        if (filteredItems.size() <= 2) { 
            poolSize = 1; 
        } else if (filteredItems.size() <= 5) { 
            poolSize = 2; 
        } else if (filteredItems.size() <= 10) { 
            poolSize = 3;
        } else if (filteredItems.size() <= 20) {
            poolSize = 5; 
        } else if (filteredItems.size() <= 50) {
            poolSize = 8; 
        } else if (filteredItems.size() <= 100) {
            poolSize = 10; 
        } else { 
            poolSize = 13;
        }

        // check to make sure Items matched criteria
        if (filteredItems.size() <= 0) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "No items match the given criteria");
        }
        // add the items to the pool
        for (int i = 0; i < poolSize; i++) {
            oldItems.add(filteredItems.get(filteredItems.size() - 1 - i));
        }
        // randomize the items in the pool
        Collections.shuffle(oldItems, new SecureRandom());

        Item selectedItem = oldItems.get(poolSize - 1);

        // update the revisited time on this item
        selectedItem.setDateLastRevised(Date.from(Instant.now()));
        itemRepository.save(selectedItem);
        return selectedItem;
    }

}
