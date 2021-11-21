package com.cinema.booking.show;

import com.cinema.booking.category.CategoryData;
import com.cinema.booking.inventory.InventoryData;
import com.cinema.booking.inventory.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/shows")
@AllArgsConstructor
public class ShowController {

    final private ShowService showService;
    final private InventoryService inventoryService;

    @GetMapping(path = "{showId}")
    public ShowData getShow(@PathVariable("showId") Long showId) {
        Show show = showService
                .findShow(showId)
                .orElseThrow(() -> new IllegalStateException("Unable to find show by id "+showId));

        return ShowMapper.INSTANCE.fromShow(show);
    }

    @GetMapping(path = "{showId}/inventory")
    public List<InventoryData> getInventoryByShow(@PathVariable("showId") Long showId) {
        return inventoryService.getInventoryByShow(showId);
    }

}
