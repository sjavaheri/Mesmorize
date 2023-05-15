package ca.montreal.mesmorize.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.montreal.mesmorize.dto.ItemDto;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Item.ItemType;
import ca.montreal.mesmorize.service.ItemService;

/**
 * API endpoints for all Item related methods
 */

@RestController
@RequestMapping({ "api/item", "api/item/" })
public class ItemController {

    @Autowired
    ItemService itemService;

    /**
     * Endpoint to create an item
     * 
     * @param itemDto
     * @return a response entity with HTTP status created and the item dto
     * @author Shidan Javaheri
     */
    @PostMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto) {
        // Unpack the DTO
        if (itemDto == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "The item dto is null");
        }

        // unpack the DTO wiht these attributes
        String name = itemDto.getName();
        String words = itemDto.getWords();
        ItemType itemType = itemDto.getItemType();
        boolean favorite = itemDto.isFavorite();
        boolean learnt = itemDto.isLearnt();
        Set<String> themeIds = itemDto.getThemeIds();
        Source source = itemDto.getSource();

        // get the username of the logged in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // create item
        ItemDto newItemDto = new ItemDto(
                itemService.createItem(name, words, itemType, favorite, learnt, themeIds, source, username));

        return new ResponseEntity<ItemDto>(newItemDto, HttpStatus.CREATED);

    }

}
