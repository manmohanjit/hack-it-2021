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
import com.cinema.booking.show.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAsync
@AllArgsConstructor
public class BookingConfig {

    MovieRepository movieRepository;
    HallRepository hallRepository;
    InventoryRepository inventoryRepository;
    ShowRepository showRepository;

    private void createFirstMovie(Hall hall, Hall hall2)
    {
        Movie movie = new Movie("The tales of Paltisaur", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

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
                hall2,
                LocalDateTime.now().plusDays(1).withHour(16).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0)
        );
        movie.addShow(show);

        Show show2 = new Show(
                movie,
                hall,
                LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(1).withHour(20).withMinute(0).withSecond(0)
        );
        movie.addShow(show2);

        Show show3 = new Show(
                movie,
                hall2,
                LocalDateTime.now().plusDays(1).withHour(20).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(1).withHour(22).withMinute(0).withSecond(0)
        );
        movie.addShow(show3);

        movieRepository.save(movie);

        createShowInventory(show, category, vipCategory, wheelchairCategory);
        createShowInventory(show2, category, vipCategory, wheelchairCategory);
        createShowInventory(show3, category, vipCategory, wheelchairCategory);
    }

    private void createShowInventory(Show show, Category category, Category vipCategory, Category wheelchairCategory) {

        List<Inventory> inventoryList = show.getHall().getSeats()
                .stream()
                .filter(seat -> !seat.getLabel().contains("F") && !seat.getLabel().contains("G") && !seat.getLabel().equals("A-6") && !seat.getLabel().equals("A-7"))
                .map(seat -> new Inventory(show, category, seat, InventoryStatus.AVAILABLE, true))
                .toList();
        inventoryRepository.saveAll(inventoryList);

        List<Inventory> vipInventoryList = show.getHall().getSeats()
                .stream()
                .filter(seat -> seat.getLabel().contains("F") || seat.getLabel().contains("G"))
                .map(seat -> new Inventory(show, vipCategory, seat, InventoryStatus.AVAILABLE, true))
                .toList();
        inventoryRepository.saveAll(vipInventoryList);

        List<Inventory> wheelchairInventoryList = show.getHall().getSeats()
                .stream()
                .filter(seat -> seat.getLabel().equals("A-6") || seat.getLabel().equals("A-7"))
                .map(seat -> new Inventory(show, wheelchairCategory, seat, InventoryStatus.AVAILABLE, true))
                .toList();
        inventoryRepository.saveAll(wheelchairInventoryList);
    }


    private void createSecondMovie(Hall hall, Hall hall2)
    {
        Movie movie = new Movie("Java Man: The Return of OOP", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");

        Category category = new Category(
                movie,
                "PHP Developer",
                2500,
                "purple"
        );
        movie.addCategory(category);

        Category vipCategory = new Category(
                movie,
                "Java Developer",
                1000,
                "green"
        );
        movie.addCategory(vipCategory);

        Category wheelchairCategory = new Category(
                movie,
                "Javascript Developer",
                900,
                "orange"
        );
        movie.addCategory(wheelchairCategory);

        Show show = new Show(
                movie,
                hall,
                LocalDateTime.now().plusDays(1).withHour(16).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0)
        );
        movie.addShow(show);

        Show show2 = new Show(
                movie,
                hall2,
                LocalDateTime.now().plusDays(1).withHour(16).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0)
        );
        movie.addShow(show2);

        Show show3 = new Show(
                movie,
                hall,
                LocalDateTime.now().plusDays(1).withHour(16).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0)
        );
        movie.addShow(show3);

        Show show4 = new Show(
                movie,
                hall,
                LocalDateTime.now().plusDays(7).withHour(16).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(7).withHour(18).withMinute(0).withSecond(0)
        );
        movie.addShow(show4);

        Show show5 = new Show(
                movie,
                hall,
                LocalDateTime.now().plusDays(8).withHour(16).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(8).withHour(18).withMinute(0).withSecond(0)
        );
        movie.addShow(show5);

        Show show6 = new Show(
                movie,
                hall,
                LocalDateTime.now().plusDays(9).withHour(16).withMinute(0).withSecond(0),
                LocalDateTime.now().plusDays(9).withHour(18).withMinute(0).withSecond(0)
        );
        movie.addShow(show6);

        movieRepository.save(movie);

        createShowInventory(show, category, vipCategory, wheelchairCategory);
        createShowInventory(show2, category, vipCategory, wheelchairCategory);
        createShowInventory(show3, category, vipCategory, wheelchairCategory);
        createShowInventory(show4, category, vipCategory, wheelchairCategory);
        createShowInventory(show5, category, vipCategory, wheelchairCategory);
        createShowInventory(show6, category, vipCategory, wheelchairCategory);
    }

    @Bean
    CommandLineRunner commandLineRunnerForMovie() {
        return args -> {
            File seatMapFile = ResourceUtils.getFile("classpath:static/seatmap.xml");
            String seatMapContent = new String(Files.readAllBytes(seatMapFile.toPath()));

            String[] seatList = new String[]{"A-1", "A-2", "A-3", "A-4", "A-5", "A-6", "A-7", "A-8", "A-9", "A-10", "A-11", "A-12", "B-1", "B-2", "B-3", "B-4", "B-5", "B-6", "B-7", "B-8", "C-1", "C-2", "C-3", "C-4", "C-5", "C-6", "C-7", "C-8", "C-9", "C-10", "C-11", "C-12", "C-13", "C-14", "C-15", "C-16", "C-17", "C-18", "D-1", "D-2", "D-3", "D-4", "D-5", "D-6", "D-7", "D-8", "D-9", "D-10", "D-11", "D-12", "D-13", "D-14", "D-15", "D-16", "D-17", "D-18", "E-1", "E-2", "E-3", "E-4", "E-5", "E-6", "E-7", "E-8", "E-9", "E-10", "E-11", "E-12", "E-13", "E-14", "E-15", "E-16", "E-17", "E-18", "F-1", "F-2", "F-3", "F-4", "F-5", "F-6", "F-7", "F-8", "F-9", "F-10", "G-1", "G-2", "G-3", "G-4", "G-5", "G-6", "G-7", "G-8", "G-9", "G-10", "H-1", "H-2", "H-3", "H-4", "H-5", "H-6", "H-7", "H-8", "H-9", "H-10", "H-11", "H-12", "H-13", "H-14", "H-15", "H-16", "H-17", "H-18", "I-1", "I-2", "I-3", "I-4", "I-5", "I-6", "I-7", "I-8", "I-9", "I-10", "I-11", "I-12", "I-13", "I-14", "I-15", "I-16", "I-17", "I-18", "J-1", "J-2", "J-3", "J-4", "J-5", "J-6", "J-7", "J-8", "J-9", "J-10", "J-11", "J-12", "J-13", "J-14", "J-15", "J-16", "J-17", "J-18", "K-1", "K-2", "K-3", "K-4", "K-5", "K-6", "K-7", "K-8", "K-9", "K-10", "K-11", "K-12", "K-13", "K-14", "K-15", "K-16", "K-17", "K-18"};

            Hall hall = new Hall("Cinema 1", seatMapContent);
            Arrays.stream(seatList).forEach(s -> hall.addSeat(new Seat(hall, s)));
            hallRepository.save(hall);

            Hall hall2 = new Hall("Cinema 2", seatMapContent);
            Arrays.stream(seatList).forEach(s -> hall2.addSeat(new Seat(hall2, s)));
            hallRepository.save(hall2);


            createFirstMovie(hall, hall2);
            createSecondMovie(hall, hall2);

        };
    }
}
