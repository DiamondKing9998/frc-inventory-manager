package com.cwarren.frcinventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cwarren.frcinventory.model.Item;
import com.cwarren.frcinventory.service.ItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "items/list";
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/form";
    }

    @PostMapping
    public String createItem(
            @Valid @ModelAttribute("item") Item item,
            BindingResult result) {

        if (itemService.nameExists(item.getName())) {
            result.rejectValue(
                    "name",
                    "duplicate",
                    "An item with this name already exists"
            );
        }

        if (item.getPartNumber() != null
                && !item.getPartNumber().isBlank()
                && itemService.partNumberExists(item.getPartNumber())) {

            result.rejectValue(
                    "partNumber",
                    "duplicate",
                    "An item with this part number already exists"
            );
        }

        if (result.hasErrors()) {
            return "items/form";
        }

        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String editItemForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id));
        return "items/form";
    }

    @PostMapping("/{id}")
    public String updateItem(
            @PathVariable Long id,
            @Valid @ModelAttribute("item") Item item,
            BindingResult result) {

        if (itemService.nameExistsForOtherItem(item.getName(), id)) {
            result.rejectValue(
                    "name",
                    "duplicate",
                    "An item with this name already exists"
            );
        }

        if (item.getPartNumber() != null
                && !item.getPartNumber().isBlank()
                && itemService.partNumberExistsForOtherItem(item.getPartNumber(), id)) {

            result.rejectValue(
                    "partNumber",
                    "duplicate",
                    "An item with this part number already exists"
            );
        }

        if (result.hasErrors()) {
            item.setId(id);
            return "items/form";
        }

        Item existing = itemService.getItemById(id);

        existing.setName(item.getName());
        existing.setCategory(item.getCategory());
        existing.setManufacturer(item.getManufacturer());
        existing.setPartNumber(item.getPartNumber());
        existing.setTotalQuantity(item.getTotalQuantity());
        existing.setReorderThreshold(item.getReorderThreshold());
        existing.setStorageLocation(item.getStorageLocation());
        existing.setProductUrl(item.getProductUrl());
        existing.setUnitPrice(item.getUnitPrice());

        itemService.saveItem(existing);

        return "redirect:/items";
    }

    @PostMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/items";
    }

}