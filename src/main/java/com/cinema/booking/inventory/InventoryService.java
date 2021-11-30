package com.cinema.booking.inventory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    /**
     * Retrieve a list of inventory for a show by show ID
     *
     * @param showId
     */
    public List<InventoryResponseData> getInventoryByShow(Long showId) {
        List<Inventory> inventory = inventoryRepository.findAllByShowId(showId);

        return InventoryMapper.INSTANCE.fromInventory(inventory);
    }

}
