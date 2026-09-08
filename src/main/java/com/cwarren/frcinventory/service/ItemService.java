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

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + id));
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public boolean nameExists(String name) {
        return itemRepository.existsByNameIgnoreCase(name);
    }

    public boolean partNumberExists(String partNumber) {
        return itemRepository.existsByPartNumberIgnoreCase(partNumber);
    }

    public boolean nameExistsForOtherItem(String name, Long id) {
        return itemRepository.existsByNameIgnoreCaseAndIdNot(name, id);
    }

    public boolean partNumberExistsForOtherItem(String partNumber, Long id) {
        return itemRepository.existsByPartNumberIgnoreCaseAndIdNot(partNumber, id);
    }
}