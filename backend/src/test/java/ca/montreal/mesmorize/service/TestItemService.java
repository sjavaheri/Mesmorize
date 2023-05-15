package ca.montreal.mesmorize.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.montreal.mesmorize.configuration.Authority;
import ca.montreal.mesmorize.dao.AccountRepository;
import ca.montreal.mesmorize.dao.ItemRepository;
import ca.montreal.mesmorize.dao.SourceRepository;
import ca.montreal.mesmorize.dao.ThemeRepository;
import ca.montreal.mesmorize.model.Account;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.PracticeSession;
import ca.montreal.mesmorize.model.Theme;
import ca.montreal.mesmorize.model.Item.ItemType;
import ca.montreal.mesmorize.util.DatabaseUtil;

/**
 * Tests for the Item Service
 */
@ExtendWith(MockitoExtension.class)
public class TestItemService {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private DatabaseUtil databaseUtil;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock SourceRepository sourceRepository;

    @Mock
    private ThemeRepository themeRepository;

    // create feilds
    private Item validItem;
    private Account validAccount;
    private Set<Theme> themes;
    private Set<PracticeSession> practiceSessions;

    // set feilds
    /**
     * Setup the valid item
     * 
     * @author Shidan Javaheri
     */
    @BeforeEach
    public void setup() {

        this.themes = new HashSet<Theme>();
        this.practiceSessions = new HashSet<PracticeSession>();
        this.validAccount = new Account("Test1", "Lastname", "test1@gmail.com", "a Cool password1", Authority.User);
        this.validItem = new Item("Hello", "It's Me", Date.from(Instant.now()), ItemType.Prayer, false, true,
                validAccount, themes, practiceSessions, null);
    }

    /**
     * Test creating a valid Item
     * @author Shidan Javaheri
     */

     @Test
     public void testCreateValidItem(){

        // setup mocks
        // no need to mock itemRepository because we want it to return null
        when(accountRepository.findAccountByUsername("test1@gmail.com")).thenAnswer((InvocationOnMock invocation) -> validAccount); 
        lenient().when(sourceRepository.findSourceByTitle(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
        lenient().when(databaseUtil.createAndSaveItem(any(String.class), any(String.class), any(ItemType.class), any(Boolean.class), any(Boolean.class), any(Account.class),any(), any(), any())).thenAnswer((InvocationOnMock invocation) -> validItem); 

        // call method
        Item createdItem = itemService.createItem("Hello", "It's Me", ItemType.Prayer, false, true, null, null,"test1@gmail.com");

        // assertions
        assertEquals(validItem.getName(), createdItem.getName());
        assertEquals(validItem.getAccount().getUsername(), createdItem.getAccount().getUsername());
        assertEquals(validItem.getWords(), createdItem.getWords());

     }

}


