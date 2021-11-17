package com.cinema.booking;

import com.cinema.booking.hall.Hall;
import com.cinema.booking.hall.HallRepository;
import com.cinema.booking.category.Category;
import com.cinema.booking.inventory.Inventory;
import com.cinema.booking.inventory.InventoryRepository;
import com.cinema.booking.inventory.Status;
import com.cinema.booking.movie.Movie;
import com.cinema.booking.movie.MovieRepository;
import com.cinema.booking.seat.Seat;
import com.cinema.booking.show.Show;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class BookingConfig {

    @Bean
    CommandLineRunner commandLineRunnerForMovie(
            MovieRepository movieRepository,
            HallRepository hallRepository,
            InventoryRepository inventoryRepository
    ) {
        return args -> {
            Hall hall = new Hall("Cinema 1");
            hall.addSeat(new Seat(hall, "A-1"));
            hall.addSeat(new Seat(hall, "A-2"));

            hallRepository.save(hall);

            Movie movie = new Movie("Test title 1", "Test body");

            Category category = new Category(
                    movie,
                    "Gold",
                    5000
            );
            movie.addCategory(category);

            Show show = new Show(
                    movie,
                    hall,
                    LocalDateTime.now().minusDays(1),
                    LocalDateTime.now().plusDays(1)
            );
            movie.addShow(show);

            movieRepository.save(movie);

            List<Inventory> inventoryList = hall.getSeats()
                    .stream()
                    .map(seat -> new Inventory(show, category, seat, Status.AVAILABLE, true))
                    .toList();
            inventoryRepository.saveAll(inventoryList);
        };
    }
}