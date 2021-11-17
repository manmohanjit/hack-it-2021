package com.cinema.booking.show;

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

    @GetMapping
    public List<Show> index() {
        return showService.getShows();
    }

    @GetMapping(path = "{showId}")
    public Show show(@PathVariable("showId") Long showId) {
        return showService
                .findShow(showId)
                .orElseThrow(() -> new IllegalStateException("Unable to find show by id "+showId));
    }

}
