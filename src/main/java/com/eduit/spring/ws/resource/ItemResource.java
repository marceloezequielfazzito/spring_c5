package com.eduit.spring.ws.resource;


import com.eduit.spring.ws.resource.request.ItemRequest;
import com.eduit.spring.ws.resource.response.ItemResponse;
import com.eduit.spring.ws.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemResource {


    private ItemService itemService;
    private static Logger LOOGER = LoggerFactory.getLogger(ItemResource.class);

    @Autowired
    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/items/{id}",produces = "application/json")
    public @ResponseBody ResponseEntity<?> getById(@PathVariable("id") Long id){


        Optional<ItemResponse> itemResponse = itemService.getById(id);

        if(itemResponse.isPresent()){
            return ResponseEntity.ok(itemResponse.get());
        }
        return ResponseEntity.badRequest().
                             contentType(MediaType.TEXT_PLAIN).
                             body(" item id " + id + " no encontrado");
    }

    @PostMapping(value = "/items" , produces = "application/json"  , consumes = "application/json")
    public ResponseEntity<?> saveItem(@RequestBody @Valid ItemRequest itemRequest){
        LOOGER.info(" /items called request {} " ,itemRequest);
        ItemResponse itemResponse = itemService.saveOrUpdate(itemRequest);
        LOOGER.info(" /items called response  {} " ,itemResponse);
        return ResponseEntity.ok(itemResponse);
    }

    @PutMapping(value = "/items" , produces = "application/json"  , consumes = "application/json")
    public ResponseEntity<?> updateItem(@RequestBody @Valid ItemRequest itemRequest){
        ItemResponse itemResponse = itemService.saveOrUpdate(itemRequest);
        return ResponseEntity.ok(itemResponse);
    }

    @DeleteMapping(value = "/items/{id}",produces = "application/json")
    public ResponseEntity<?> delete (@PathVariable("id") Long id){
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/items",produces = "application/json")
    public @ResponseBody ResponseEntity<?> getAll() {
        List<ItemResponse> itemResponses = itemService.getAll();
        return ResponseEntity.ok(itemResponses);

    }

}
