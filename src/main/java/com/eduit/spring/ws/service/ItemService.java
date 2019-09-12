package com.eduit.spring.ws.service;


import com.eduit.spring.ws.exception.NotFoundException;
import com.eduit.spring.ws.model.Item;
import com.eduit.spring.ws.repository.ItemRepository;
import com.eduit.spring.ws.resource.request.ItemRequest;
import com.eduit.spring.ws.resource.response.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public Optional<ItemResponse> getById(Long id){
        Optional<Item> item = Optional.ofNullable(itemRepository.findOne(id));

        Optional<ItemResponse> response = item.map(i -> Optional.of(toItemResponse(i))).
                                               orElse(Optional.empty());
        return response;
    }

    public boolean exists(long id){
        return itemRepository.exists(id);
    }


    public List<ItemResponse> getAll(){
        List<ItemResponse> itemResponses = new ArrayList<>();
        itemRepository.findAll().forEach(item -> {
            ItemResponse itemResponse = toItemResponse(item);
            itemResponses.add(itemResponse);
        });
        return itemResponses;
    }

    private ItemResponse toItemResponse(Item item) {
        return new ItemResponse(item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity());
    }

    @Transactional
    public ItemResponse saveOrUpdate(ItemRequest itemRequest){

        Item item = new Item(itemRequest.getId(),itemRequest.getName(),itemRequest.getPrice(),itemRequest.getQuantity());
        Item saved = itemRepository.save(item);
        ItemResponse itemResponse = toItemResponse(saved);
        return itemResponse;
    }

    @Transactional
    public void delete(long id){
        if (!itemRepository.exists(id)){
            throw new NotFoundException(" id " + id + "not found");
        }

       itemRepository.delete(id);
    }




}
