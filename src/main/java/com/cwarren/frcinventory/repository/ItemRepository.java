package com.cwarren.frcinventory.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cwarren.frcinventory.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByPartNumberIgnoreCase(String partNumber);
    
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsByPartNumberIgnoreCaseAndIdNot(String partNumber, Long id);
}