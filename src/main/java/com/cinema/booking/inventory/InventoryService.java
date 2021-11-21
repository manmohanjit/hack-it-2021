package com.cinema.booking.inventory;

import com.cinema.booking.hall.Hall;
import com.cinema.booking.seat.SeatData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryData> getInventoryByShow(Long showId) {
        List<Inventory> inventory = inventoryRepository.findAllByShowId(showId);

        return InventoryMapper.INSTANCE.fromInventory(inventory);
    }

}
