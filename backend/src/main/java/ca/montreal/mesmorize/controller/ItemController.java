package ca.montreal.mesmorize.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.montreal.mesmorize.dto.FilterDto;
import ca.montreal.mesmorize.dto.ItemDto;
import ca.montreal.mesmorize.exception.GlobalException;
import ca.montreal.mesmorize.model.Item;
import ca.montreal.mesmorize.model.Source;
import ca.montreal.mesmorize.model.Item.ItemType;
import ca.montreal.mesmorize.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * API endpoints for all Item related methods
 */

@RestController
@RequestMapping({ "api/item" })
@Tag(name = "Item API", description = "API endpoints for all Item related methods")
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
    @Operation(summary="Create an Item to Memorize", description="Endpoint to create an Item to Memorize")
    @ApiResponse(responseCode="200", description="Returns the Item Dto")
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
        String language = itemDto.getLanguage();
        String chords = itemDto.getChords();

        // get the username of the logged in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // create item
        ItemDto newItemDto = new ItemDto(
                itemService.createItem(name, words, itemType, favorite, learnt, themeIds,language, chords, source, username));

        return new ResponseEntity<ItemDto>(newItemDto, HttpStatus.CREATED);

    }

    @GetMapping
    @PreAuthorize("hasAuthority('User')")
    @Operation(summary="The Search Endpoint for Items", description="Endpoint to search and filter all of the items linked to an account")
    @ApiResponse(responseCode="200", description="A list of Item Dtos matching the criteria of the filter")
    public ResponseEntity<ArrayList<ItemDto>> filterItems(@RequestBody FilterDto filterDto){
        // unpack Dto if it is not null
        if (filterDto == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "The filter dto is null");
        }
        // get attributes 
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String name = filterDto.getName();
        String words = filterDto.getWords();
        String themeName = filterDto.getThemeName();
        ItemType itemType = filterDto.getItemType();
        Boolean favorite = filterDto.getFavorite();
        String language = filterDto.getLanguage(); 

        // call service 
        ArrayList<Item> items = itemService.filterItems(name, itemType, themeName, words, favorite, language, username);

        // create DTOs
        ArrayList<ItemDto> itemDtos = new ArrayList<ItemDto>();
        for (Item item : items) {
            itemDtos.add(new ItemDto(item));
        }
        // return a response entity with the DTOs
        return new ResponseEntity<ArrayList<ItemDto>>(itemDtos, HttpStatus.OK);

    }

    @GetMapping({"/recommend"})
    @PreAuthorize("hasAuthority('User')")
    @Operation(summary="Recommend an Item to Practice", description="Endpoint to be recommended an item to practice")
    @ApiResponse(responseCode="200", description="Returns the single recommended Item")
    public ResponseEntity<Item> recommendItem(@RequestBody FilterDto filterDto) {
        // make sure DTO is not null
        if (filterDto == null){ 
            throw new GlobalException(HttpStatus.BAD_REQUEST, "The filter dto is null"); 
        }

        // get the username of the logged in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // unpack Dto
        String theme = filterDto.getThemeName();
        ItemType itemType = filterDto.getItemType();
        Boolean favorite = filterDto.getFavorite(); 
        String language = filterDto.getLanguage();

        // call service
        Item item = itemService.recommendItem(username, theme, itemType, favorite, language);

        // return a response entity with the item
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

}
