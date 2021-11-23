package com.cinema.booking.show;

import com.cinema.booking.inventory.InventoryResponseData;
import com.cinema.booking.inventory.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/shows")
@AllArgsConstructor
public class ShowController {

    final private ShowService showService;
    final private InventoryService inventoryService;

    @GetMapping(path = "{showId}")
    public ShowData getShow(@PathVariable("showId") Long showId) {
        return showService
                .findShow(showId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find show with id "+showId));
    }

    @GetMapping(path = "{showId}/inventory")
    public List<InventoryResponseData> getInventoryByShow(@PathVariable("showId") Long showId) {
        return inventoryService.getInventoryByShow(showId);
    }

}
