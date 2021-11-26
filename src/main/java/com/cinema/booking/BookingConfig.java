package com.cinema.booking;

import com.cinema.booking.category.Category;
import com.cinema.booking.hall.Hall;
import com.cinema.booking.hall.HallRepository;
import com.cinema.booking.inventory.Inventory;
import com.cinema.booking.inventory.InventoryRepository;
import com.cinema.booking.inventory.InventoryStatus;
import com.cinema.booking.movie.Movie;
import com.cinema.booking.movie.MovieRepository;
import com.cinema.booking.order.OrderRepository;
import com.cinema.booking.seat.Seat;
import com.cinema.booking.show.Show;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class BookingConfig {

    @Bean
    CommandLineRunner commandLineRunnerForMovie(
            MovieRepository movieRepository,
            HallRepository hallRepository,
            InventoryRepository inventoryRepository,
            OrderRepository orderRepository
    ) {
        return args -> {
            File seatMapFile = ResourceUtils.getFile("classpath:static/seatmap.xml");
            String seatMapContent = new String(Files.readAllBytes(seatMapFile.toPath()));

            Hall hall = new Hall("Cinema 1", seatMapContent);

            String[] seatList = new String[]{"A-1", "A-2", "A-3", "A-4", "A-5", "A-6", "A-7", "A-8", "A-9", "A-10", "A-11", "A-12", "B-1", "B-2", "B-3", "B-4", "B-5", "B-6", "B-7", "B-8", "C-1", "C-2", "C-3", "C-4", "C-5", "C-6", "C-7", "C-8", "C-9", "C-10", "C-11", "C-12", "C-13", "C-14", "C-15", "C-16", "C-17", "C-18", "D-1", "D-2", "D-3", "D-4", "D-5", "D-6", "D-7", "D-8", "D-9", "D-10", "D-11", "D-12", "D-13", "D-14", "D-15", "D-16", "D-17", "D-18", "E-1", "E-2", "E-3", "E-4", "E-5", "E-6", "E-7", "E-8", "E-9", "E-10", "E-11", "E-12", "E-13", "E-14", "E-15", "E-16", "E-17", "E-18", "F-1", "F-2", "F-3", "F-4", "F-5", "F-6", "F-7", "F-8", "F-9", "F-10", "G-1", "G-2", "G-3", "G-4", "G-5", "G-6", "G-7", "G-8", "G-9", "G-10", "H-1", "H-2", "H-3", "H-4", "H-5", "H-6", "H-7", "H-8", "H-9", "H-10", "H-11", "H-12", "H-13", "H-14", "H-15", "H-16", "H-17", "H-18", "I-1", "I-2", "I-3", "I-4", "I-5", "I-6", "I-7", "I-8", "I-9", "I-10", "I-11", "I-12", "I-13", "I-14", "I-15", "I-16", "I-17", "I-18", "J-1", "J-2", "J-3", "J-4", "J-5", "J-6", "J-7", "J-8", "J-9", "J-10", "J-11", "J-12", "J-13", "J-14", "J-15", "J-16", "J-17", "J-18", "K-1", "K-2", "K-3", "K-4", "K-5", "K-6", "K-7", "K-8", "K-9", "K-10", "K-11", "K-12", "K-13", "K-14", "K-15", "K-16", "K-17", "K-18"};
            Arrays.stream(seatList).forEach(s -> hall.addSeat(new Seat(hall, s)));

            hallRepository.save(hall);

            Movie movie = new Movie("Test title 1", "Test body");

            Category category = new Category(
                    movie,
                    "Standard",
                    2500,
                    "red"
            );
            movie.addCategory(category);

            Category vipCategory = new Category(
                    movie,
                    "VIP",
                    5000,
                    "orange"
            );
            movie.addCategory(vipCategory);

            Category wheelchairCategory = new Category(
                    movie,
                    "Wheelchair",
                    1500,
                    "cyan"
            );
            movie.addCategory(wheelchairCategory);

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
                    .filter(seat -> !seat.getLabel().contains("F") && !seat.getLabel().contains("G") && !seat.getLabel().equals("A-6") && !seat.getLabel().equals("A-7"))
                    .map(seat -> new Inventory(show, category, seat, InventoryStatus.AVAILABLE, true))
                    .toList();
            inventoryRepository.saveAll(inventoryList);

            List<Inventory> vipInventoryList = hall.getSeats()
                    .stream()
                    .filter(seat -> seat.getLabel().contains("F") || seat.getLabel().contains("G"))
                    .map(seat -> new Inventory(show, vipCategory, seat, InventoryStatus.AVAILABLE, true))
                    .toList();
            inventoryRepository.saveAll(vipInventoryList);

            List<Inventory> wheelchairInventoryList = hall.getSeats()
                    .stream()
                    .filter(seat -> seat.getLabel().equals("A-6") || seat.getLabel().equals("A-7"))
                    .map(seat -> new Inventory(show, wheelchairCategory, seat, InventoryStatus.AVAILABLE, true))
                    .toList();
            inventoryRepository.saveAll(wheelchairInventoryList);
        };
    }
}
