package com.cwarren.frcinventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cwarren.frcinventory.model.Item;
import com.cwarren.frcinventory.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
}