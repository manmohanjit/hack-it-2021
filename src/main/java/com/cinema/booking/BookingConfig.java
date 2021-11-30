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
            String seatMapContent = "<svg width=\"1330\" height=\"762\" viewBox=\"0 0 1330 762\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "    <g id=\"Cinema\">\n" +
                    "        <rect id=\"Background\" x=\"1.5\" y=\"0.5\" width=\"1328\" height=\"761\" fill=\"white\" stroke=\"#CCCCCC\"/>\n" +
                    "        <g id=\"Screen\">\n" +
                    "            <rect id=\"Background_2\" x=\"467\" width=\"396\" height=\"36\" fill=\"#C4C4C4\"/>\n" +
                    "            <path id=\"Screen_2\" d=\"M645.703 51.9297C644.417 51.5599 643.479 51.1068 642.891 50.5703C642.307 50.0286 642.016 49.362 642.016 48.5703C642.016 47.6745 642.372 46.9349 643.086 46.3516C643.805 45.763 644.737 45.4688 645.883 45.4688C646.664 45.4688 647.359 45.6198 647.969 45.9219C648.583 46.224 649.057 46.6406 649.391 47.1719C649.729 47.7031 649.898 48.2839 649.898 48.9141H648.391C648.391 48.2266 648.172 47.6875 647.734 47.2969C647.297 46.901 646.68 46.7031 645.883 46.7031C645.143 46.7031 644.565 46.8672 644.148 47.1953C643.737 47.5182 643.531 47.9688 643.531 48.5469C643.531 49.0104 643.727 49.4036 644.117 49.7266C644.513 50.0443 645.182 50.3359 646.125 50.6016C647.073 50.8672 647.812 51.1615 648.344 51.4844C648.88 51.8021 649.276 52.1745 649.531 52.6016C649.792 53.0286 649.922 53.5312 649.922 54.1094C649.922 55.0312 649.562 55.7708 648.844 56.3281C648.125 56.8802 647.164 57.1562 645.961 57.1562C645.18 57.1562 644.451 57.0078 643.773 56.7109C643.096 56.4089 642.573 55.9974 642.203 55.4766C641.839 54.9557 641.656 54.3646 641.656 53.7031H643.164C643.164 54.3906 643.417 54.9349 643.922 55.3359C644.432 55.7318 645.112 55.9297 645.961 55.9297C646.753 55.9297 647.359 55.7682 647.781 55.4453C648.203 55.1224 648.414 54.6823 648.414 54.125C648.414 53.5677 648.219 53.138 647.828 52.8359C647.438 52.5286 646.729 52.2266 645.703 51.9297ZM655.016 55.9766C655.531 55.9766 655.982 55.8203 656.367 55.5078C656.753 55.1953 656.966 54.8047 657.008 54.3359H658.375C658.349 54.8203 658.182 55.2812 657.875 55.7188C657.568 56.1562 657.156 56.5052 656.641 56.7656C656.13 57.026 655.589 57.1562 655.016 57.1562C653.865 57.1562 652.948 56.7734 652.266 56.0078C651.589 55.237 651.25 54.1849 651.25 52.8516V52.6094C651.25 51.7865 651.401 51.0547 651.703 50.4141C652.005 49.7734 652.438 49.276 653 48.9219C653.568 48.5677 654.237 48.3906 655.008 48.3906C655.956 48.3906 656.742 48.6745 657.367 49.2422C657.997 49.8099 658.333 50.5469 658.375 51.4531H657.008C656.966 50.9062 656.758 50.4583 656.383 50.1094C656.013 49.7552 655.555 49.5781 655.008 49.5781C654.273 49.5781 653.703 49.8438 653.297 50.375C652.896 50.901 652.695 51.6641 652.695 52.6641V52.9375C652.695 53.9115 652.896 54.6615 653.297 55.1875C653.698 55.7135 654.271 55.9766 655.016 55.9766ZM664.086 49.8438C663.867 49.8073 663.63 49.7891 663.375 49.7891C662.427 49.7891 661.784 50.1927 661.445 51V57H660V48.5469H661.406L661.43 49.5234C661.904 48.7682 662.576 48.3906 663.445 48.3906C663.727 48.3906 663.94 48.4271 664.086 48.5V49.8438ZM668.773 57.1562C667.628 57.1562 666.695 56.7812 665.977 56.0312C665.258 55.276 664.898 54.2682 664.898 53.0078V52.7422C664.898 51.9036 665.057 51.1562 665.375 50.5C665.698 49.8385 666.146 49.3229 666.719 48.9531C667.297 48.5781 667.922 48.3906 668.594 48.3906C669.693 48.3906 670.547 48.7526 671.156 49.4766C671.766 50.2005 672.07 51.237 672.07 52.5859V53.1875H666.344C666.365 54.0208 666.607 54.6953 667.07 55.2109C667.539 55.7214 668.133 55.9766 668.852 55.9766C669.362 55.9766 669.794 55.8724 670.148 55.6641C670.503 55.4557 670.812 55.1797 671.078 54.8359L671.961 55.5234C671.253 56.612 670.19 57.1562 668.773 57.1562ZM668.594 49.5781C668.01 49.5781 667.521 49.7917 667.125 50.2188C666.729 50.6406 666.484 51.2344 666.391 52H670.625V51.8906C670.583 51.1562 670.385 50.5885 670.031 50.1875C669.677 49.7812 669.198 49.5781 668.594 49.5781ZM677.258 57.1562C676.112 57.1562 675.18 56.7812 674.461 56.0312C673.742 55.276 673.383 54.2682 673.383 53.0078V52.7422C673.383 51.9036 673.542 51.1562 673.859 50.5C674.182 49.8385 674.63 49.3229 675.203 48.9531C675.781 48.5781 676.406 48.3906 677.078 48.3906C678.177 48.3906 679.031 48.7526 679.641 49.4766C680.25 50.2005 680.555 51.237 680.555 52.5859V53.1875H674.828C674.849 54.0208 675.091 54.6953 675.555 55.2109C676.023 55.7214 676.617 55.9766 677.336 55.9766C677.846 55.9766 678.279 55.8724 678.633 55.6641C678.987 55.4557 679.297 55.1797 679.562 54.8359L680.445 55.5234C679.737 56.612 678.674 57.1562 677.258 57.1562ZM677.078 49.5781C676.495 49.5781 676.005 49.7917 675.609 50.2188C675.214 50.6406 674.969 51.2344 674.875 52H679.109V51.8906C679.068 51.1562 678.87 50.5885 678.516 50.1875C678.161 49.7812 677.682 49.5781 677.078 49.5781ZM683.602 48.5469L683.648 49.6094C684.294 48.7969 685.138 48.3906 686.18 48.3906C687.966 48.3906 688.867 49.3984 688.883 51.4141V57H687.438V51.4062C687.432 50.7969 687.292 50.3464 687.016 50.0547C686.745 49.763 686.32 49.6172 685.742 49.6172C685.273 49.6172 684.862 49.7422 684.508 49.9922C684.154 50.2422 683.878 50.5703 683.68 50.9766V57H682.234V48.5469H683.602Z\" fill=\"black\"/>\n" +
                    "        </g>\n" +
                    "        <g id=\"Label Left\">\n" +
                    "            <path id=\"A\" d=\"M20.4785 112.289H14.5215L13.1836 116H11.25L16.6797 101.781H18.3203L23.7598 116H21.8359L20.4785 112.289ZM15.0879 110.746H19.9219L17.5 104.096L15.0879 110.746Z\" fill=\"black\"/>\n" +
                    "            <path id=\"B\" d=\"M12.9199 169V154.781H17.5684C19.1113 154.781 20.2702 155.1 21.0449 155.738C21.8262 156.376 22.2168 157.32 22.2168 158.57C22.2168 159.234 22.028 159.824 21.6504 160.338C21.2728 160.846 20.7585 161.24 20.1074 161.52C20.8757 161.734 21.4811 162.145 21.9238 162.75C22.373 163.349 22.5977 164.065 22.5977 164.898C22.5977 166.174 22.1842 167.177 21.3574 167.906C20.5306 168.635 19.362 169 17.8516 169H12.9199ZM14.7949 162.35V167.467H17.8906C18.763 167.467 19.4499 167.242 19.9512 166.793C20.459 166.337 20.7129 165.712 20.7129 164.918C20.7129 163.206 19.7819 162.35 17.9199 162.35H14.7949ZM14.7949 160.846H17.627C18.4473 160.846 19.1016 160.641 19.5898 160.23C20.0846 159.82 20.332 159.264 20.332 158.561C20.332 157.779 20.1042 157.213 19.6484 156.861C19.1927 156.503 18.4993 156.324 17.5684 156.324H14.7949V160.846Z\" fill=\"black\"/>\n" +
                    "            <path id=\"C\" d=\"M23.0957 217.488C22.9199 218.992 22.3633 220.154 21.4258 220.975C20.4948 221.788 19.2546 222.195 17.7051 222.195C16.0254 222.195 14.6777 221.593 13.6621 220.389C12.653 219.184 12.1484 217.573 12.1484 215.555V214.188C12.1484 212.866 12.3828 211.704 12.8516 210.701C13.3268 209.699 13.9974 208.93 14.8633 208.396C15.7292 207.856 16.7318 207.586 17.8711 207.586C19.3815 207.586 20.5924 208.009 21.5039 208.855C22.4154 209.695 22.946 210.861 23.0957 212.352H21.2109C21.0482 211.219 20.6934 210.398 20.1465 209.891C19.6061 209.383 18.8477 209.129 17.8711 209.129C16.6732 209.129 15.7324 209.572 15.0488 210.457C14.3717 211.342 14.0332 212.602 14.0332 214.236V215.613C14.0332 217.156 14.3555 218.383 15 219.295C15.6445 220.206 16.5462 220.662 17.7051 220.662C18.7467 220.662 19.5443 220.428 20.0977 219.959C20.6576 219.484 21.0286 218.66 21.2109 217.488H23.0957Z\" fill=\"black\"/>\n" +
                    "            <path id=\"D\" d=\"M12.5879 275V260.781H16.6016C17.8385 260.781 18.9323 261.055 19.8828 261.602C20.8333 262.148 21.5658 262.926 22.0801 263.936C22.6009 264.945 22.8646 266.104 22.8711 267.412V268.32C22.8711 269.661 22.6107 270.837 22.0898 271.846C21.5755 272.855 20.8366 273.63 19.873 274.17C18.916 274.71 17.7995 274.987 16.5234 275H12.5879ZM14.4629 262.324V273.467H16.4355C17.8809 273.467 19.0039 273.018 19.8047 272.119C20.612 271.221 21.0156 269.941 21.0156 268.281V267.451C21.0156 265.837 20.6348 264.583 19.873 263.691C19.1178 262.793 18.0436 262.337 16.6504 262.324H14.4629Z\" fill=\"black\"/>\n" +
                    "            <path id=\"E\" d=\"M21.5039 321.428H15.3418V326.467H22.5V328H13.4668V313.781H22.4023V315.324H15.3418V319.895H21.5039V321.428Z\" fill=\"black\"/>\n" +
                    "            <path id=\"F\" d=\"M21.4648 374.721H15.498V381H13.623V366.781H22.4316V368.324H15.498V373.188H21.4648V374.721Z\" fill=\"black\"/>\n" +
                    "            <path id=\"G\" d=\"M22.832 432.135C22.3503 432.825 21.6764 433.342 20.8105 433.688C19.9512 434.026 18.9486 434.195 17.8027 434.195C16.6439 434.195 15.6152 433.925 14.7168 433.385C13.8184 432.838 13.1217 432.063 12.627 431.061C12.1387 430.058 11.888 428.896 11.875 427.574V426.334C11.875 424.192 12.373 422.532 13.3691 421.354C14.3717 420.175 15.778 419.586 17.5879 419.586C19.0723 419.586 20.2669 419.967 21.1719 420.729C22.0768 421.484 22.6302 422.558 22.832 423.951H20.957C20.6055 422.07 19.4857 421.129 17.5977 421.129C16.3411 421.129 15.3874 421.572 14.7363 422.457C14.0918 423.336 13.7663 424.612 13.7598 426.285V427.447C13.7598 429.042 14.1243 430.312 14.8535 431.256C15.5827 432.193 16.569 432.662 17.8125 432.662C18.5156 432.662 19.1309 432.584 19.6582 432.428C20.1855 432.271 20.6217 432.008 20.9668 431.637V428.443H17.6758V426.92H22.832V432.135Z\" fill=\"black\"/>\n" +
                    "            <path id=\"H\" d=\"M22.9492 487H21.0645V480.428H13.8965V487H12.0215V472.781H13.8965V478.895H21.0645V472.781H22.9492V487Z\" fill=\"black\"/>\n" +
                    "            <path id=\"I\" d=\"M18.4375 540H16.5625V525.781H18.4375V540Z\" fill=\"black\"/>\n" +
                    "            <path id=\"J\" d=\"M19.5898 578.781H21.4746V588.85C21.4746 590.21 21.0645 591.275 20.2441 592.043C19.4303 592.811 18.3431 593.195 16.9824 593.195C15.5697 593.195 14.4694 592.834 13.6816 592.111C12.8939 591.389 12.5 590.376 12.5 589.074H14.375C14.375 589.888 14.5964 590.523 15.0391 590.979C15.4883 591.434 16.1361 591.662 16.9824 591.662C17.7572 591.662 18.3822 591.418 18.8574 590.93C19.3392 590.441 19.5833 589.758 19.5898 588.879V578.781Z\" fill=\"black\"/>\n" +
                    "            <path id=\"K\" d=\"M16.4941 639.389L14.7559 641.195V646H12.8809V631.781H14.7559V638.812L21.0742 631.781H23.3398L17.7441 638.061L23.7793 646H21.5332L16.4941 639.389Z\" fill=\"black\"/>\n" +
                    "        </g>\n" +
                    "        <g id=\"Label Right\">\n" +
                    "            <path id=\"A_2\" d=\"M1316.48 112.289H1310.52L1309.18 116H1307.25L1312.68 101.781H1314.32L1319.76 116H1317.84L1316.48 112.289ZM1311.09 110.746H1315.92L1313.5 104.096L1311.09 110.746Z\" fill=\"black\"/>\n" +
                    "            <path id=\"B_2\" d=\"M1308.92 169V154.781H1313.57C1315.11 154.781 1316.27 155.1 1317.04 155.738C1317.83 156.376 1318.22 157.32 1318.22 158.57C1318.22 159.234 1318.03 159.824 1317.65 160.338C1317.27 160.846 1316.76 161.24 1316.11 161.52C1316.88 161.734 1317.48 162.145 1317.92 162.75C1318.37 163.349 1318.6 164.065 1318.6 164.898C1318.6 166.174 1318.18 167.177 1317.36 167.906C1316.53 168.635 1315.36 169 1313.85 169H1308.92ZM1310.79 162.35V167.467H1313.89C1314.76 167.467 1315.45 167.242 1315.95 166.793C1316.46 166.337 1316.71 165.712 1316.71 164.918C1316.71 163.206 1315.78 162.35 1313.92 162.35H1310.79ZM1310.79 160.846H1313.63C1314.45 160.846 1315.1 160.641 1315.59 160.23C1316.08 159.82 1316.33 159.264 1316.33 158.561C1316.33 157.779 1316.1 157.213 1315.65 156.861C1315.19 156.503 1314.5 156.324 1313.57 156.324H1310.79V160.846Z\" fill=\"black\"/>\n" +
                    "            <path id=\"C_2\" d=\"M1319.1 217.488C1318.92 218.992 1318.36 220.154 1317.43 220.975C1316.49 221.788 1315.25 222.195 1313.71 222.195C1312.03 222.195 1310.68 221.593 1309.66 220.389C1308.65 219.184 1308.15 217.573 1308.15 215.555V214.188C1308.15 212.866 1308.38 211.704 1308.85 210.701C1309.33 209.699 1310 208.93 1310.86 208.396C1311.73 207.856 1312.73 207.586 1313.87 207.586C1315.38 207.586 1316.59 208.009 1317.5 208.855C1318.42 209.695 1318.95 210.861 1319.1 212.352H1317.21C1317.05 211.219 1316.69 210.398 1316.15 209.891C1315.61 209.383 1314.85 209.129 1313.87 209.129C1312.67 209.129 1311.73 209.572 1311.05 210.457C1310.37 211.342 1310.03 212.602 1310.03 214.236V215.613C1310.03 217.156 1310.36 218.383 1311 219.295C1311.64 220.206 1312.55 220.662 1313.71 220.662C1314.75 220.662 1315.54 220.428 1316.1 219.959C1316.66 219.484 1317.03 218.66 1317.21 217.488H1319.1Z\" fill=\"black\"/>\n" +
                    "            <path id=\"D_2\" d=\"M1308.59 275V260.781H1312.6C1313.84 260.781 1314.93 261.055 1315.88 261.602C1316.83 262.148 1317.57 262.926 1318.08 263.936C1318.6 264.945 1318.86 266.104 1318.87 267.412V268.32C1318.87 269.661 1318.61 270.837 1318.09 271.846C1317.58 272.855 1316.84 273.63 1315.87 274.17C1314.92 274.71 1313.8 274.987 1312.52 275H1308.59ZM1310.46 262.324V273.467H1312.44C1313.88 273.467 1315 273.018 1315.8 272.119C1316.61 271.221 1317.02 269.941 1317.02 268.281V267.451C1317.02 265.837 1316.63 264.583 1315.87 263.691C1315.12 262.793 1314.04 262.337 1312.65 262.324H1310.46Z\" fill=\"black\"/>\n" +
                    "            <path id=\"E_2\" d=\"M1317.5 321.428H1311.34V326.467H1318.5V328H1309.47V313.781H1318.4V315.324H1311.34V319.895H1317.5V321.428Z\" fill=\"black\"/>\n" +
                    "            <path id=\"F_2\" d=\"M1317.46 374.721H1311.5V381H1309.62V366.781H1318.43V368.324H1311.5V373.188H1317.46V374.721Z\" fill=\"black\"/>\n" +
                    "            <path id=\"G_2\" d=\"M1318.83 432.135C1318.35 432.825 1317.68 433.342 1316.81 433.688C1315.95 434.026 1314.95 434.195 1313.8 434.195C1312.64 434.195 1311.62 433.925 1310.72 433.385C1309.82 432.838 1309.12 432.063 1308.63 431.061C1308.14 430.058 1307.89 428.896 1307.88 427.574V426.334C1307.88 424.192 1308.37 422.532 1309.37 421.354C1310.37 420.175 1311.78 419.586 1313.59 419.586C1315.07 419.586 1316.27 419.967 1317.17 420.729C1318.08 421.484 1318.63 422.558 1318.83 423.951H1316.96C1316.61 422.07 1315.49 421.129 1313.6 421.129C1312.34 421.129 1311.39 421.572 1310.74 422.457C1310.09 423.336 1309.77 424.612 1309.76 426.285V427.447C1309.76 429.042 1310.12 430.312 1310.85 431.256C1311.58 432.193 1312.57 432.662 1313.81 432.662C1314.52 432.662 1315.13 432.584 1315.66 432.428C1316.19 432.271 1316.62 432.008 1316.97 431.637V428.443H1313.68V426.92H1318.83V432.135Z\" fill=\"black\"/>\n" +
                    "            <path id=\"H_2\" d=\"M1318.95 487H1317.06V480.428H1309.9V487H1308.02V472.781H1309.9V478.895H1317.06V472.781H1318.95V487Z\" fill=\"black\"/>\n" +
                    "            <path id=\"I_2\" d=\"M1314.44 540H1312.56V525.781H1314.44V540Z\" fill=\"black\"/>\n" +
                    "            <path id=\"J_2\" d=\"M1315.59 578.781H1317.47V588.85C1317.47 590.21 1317.06 591.275 1316.24 592.043C1315.43 592.811 1314.34 593.195 1312.98 593.195C1311.57 593.195 1310.47 592.834 1309.68 592.111C1308.89 591.389 1308.5 590.376 1308.5 589.074H1310.38C1310.38 589.888 1310.6 590.523 1311.04 590.979C1311.49 591.434 1312.14 591.662 1312.98 591.662C1313.76 591.662 1314.38 591.418 1314.86 590.93C1315.34 590.441 1315.58 589.758 1315.59 588.879V578.781Z\" fill=\"black\"/>\n" +
                    "            <path id=\"K_2\" d=\"M1312.49 639.389L1310.76 641.195V646H1308.88V631.781H1310.76V638.812L1317.07 631.781H1319.34L1313.74 638.061L1319.78 646H1317.53L1312.49 639.389Z\" fill=\"black\"/>\n" +
                    "        </g>\n" +
                    "        <g id=\"Seats\">\n" +
                    "            <g id=\"ROW-A\" class=\"row\">\n" +
                    "                <g id=\"Frame 2\">\n" +
                    "                    <path d=\"M72 94H262V114C262 116.761 259.761 119 257 119H77C74.2386 119 72 116.761 72 114V94Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-1\" d=\"M73 95H96V114C96 116.209 94.2091 118 92 118H77C74.7909 118 73 116.209 73 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-2\" d=\"M128 95H151V114C151 116.209 149.209 118 147 118H132C129.791 118 128 116.209 128 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-3\" d=\"M183 95H206V114C206 116.209 204.209 118 202 118H187C184.791 118 183 116.209 183 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-4\" d=\"M238 95H261V114C261 116.209 259.209 118 257 118H242C239.791 118 238 116.209 238 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1\">\n" +
                    "                    <path d=\"M405 94H595V114C595 116.761 592.761 119 590 119H410C407.239 119 405 116.761 405 114V94Z\" fill=\"white\"/>\n" +
                    "                    <g id=\"Frame 5\">\n" +
                    "                        <path class=\"seat\" id=\"A-5\" d=\"M516 95H539V114C539 116.209 537.209 118 535 118H520C517.791 118 516 116.209 516 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                        <path class=\"seat\" id=\"A-6\" d=\"M571 95H594V114C594 116.209 592.209 118 590 118H575C572.791 118 571 116.209 571 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    </g>\n" +
                    "                    <g id=\"Frame 4\">\n" +
                    "                        <path class=\"seat\" id=\"A-7\" d=\"M738 95H761V114C761 116.209 759.209 118 757 118H742C739.791 118 738 116.209 738 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                        <path class=\"seat\" id=\"A-8\" d=\"M793 95H816V114C816 116.209 814.209 118 812 118H797C794.791 118 793 116.209 793 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    </g>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3\">\n" +
                    "                    <path d=\"M1068 94H1258V114C1258 116.761 1255.76 119 1253 119H1073C1070.24 119 1068 116.761 1068 114V94Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-9\" d=\"M1069 95H1092V114C1092 116.209 1090.21 118 1088 118H1073C1070.79 118 1069 116.209 1069 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-10\" d=\"M1124 95H1147V114C1147 116.209 1145.21 118 1143 118H1128C1125.79 118 1124 116.209 1124 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-11\" d=\"M1179 95H1202V114C1202 116.209 1200.21 118 1198 118H1183C1180.79 118 1179 116.209 1179 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"A-12\" d=\"M1234 95H1257V114C1257 116.209 1255.21 118 1253 118H1238C1235.79 118 1234 116.209 1234 114V95Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-B\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_2\">\n" +
                    "                    <path d=\"M72 149H262V169C262 171.761 259.761 174 257 174H77C74.2386 174 72 171.761 72 169V149Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-1\" d=\"M73 150H96V169C96 171.209 94.2091 173 92 173H77C74.7909 173 73 171.209 73 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-2\" d=\"M128 150H151V169C151 171.209 149.209 173 147 173H132C129.791 173 128 171.209 128 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-3\" d=\"M183 150H206V169C206 171.209 204.209 173 202 173H187C184.791 173 183 171.209 183 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-4\" d=\"M238 150H261V169C261 171.209 259.209 173 257 173H242C239.791 173 238 171.209 238 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_2\">\n" +
                    "                    <path d=\"M1068 149H1258V169C1258 171.761 1255.76 174 1253 174H1073C1070.24 174 1068 171.761 1068 169V149Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-5\" d=\"M1069 150H1092V169C1092 171.209 1090.21 173 1088 173H1073C1070.79 173 1069 171.209 1069 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-6\" d=\"M1124 150H1147V169C1147 171.209 1145.21 173 1143 173H1128C1125.79 173 1124 171.209 1124 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-7\" d=\"M1179 150H1202V169C1202 171.209 1200.21 173 1198 173H1183C1180.79 173 1179 171.209 1179 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"B-8\" d=\"M1234 150H1257V169C1257 171.209 1255.21 173 1253 173H1238C1235.79 173 1234 171.209 1234 169V150Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-C\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_3\">\n" +
                    "                    <path d=\"M72 204H262V224C262 226.761 259.761 229 257 229H77C74.2386 229 72 226.761 72 224V204Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-1\" d=\"M73 205H96V224C96 226.209 94.2091 228 92 228H77C74.7909 228 73 226.209 73 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-2\" d=\"M128 205H151V224C151 226.209 149.209 228 147 228H132C129.791 228 128 226.209 128 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-3\" d=\"M183 205H206V224C206 226.209 204.209 228 202 228H187C184.791 228 183 226.209 183 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-4\" d=\"M238 205H261V224C261 226.209 259.209 228 257 228H242C239.791 228 238 226.209 238 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1_2\">\n" +
                    "                    <path d=\"M405 204H925V217C925 219.761 922.761 222 920 222H410C407.239 222 405 219.761 405 217V204Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-5\" d=\"M406 205H429V224C429 226.209 427.209 228 425 228H410C407.791 228 406 226.209 406 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-6\" d=\"M461 205H484V224C484 226.209 482.209 228 480 228H465C462.791 228 461 226.209 461 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-7\" d=\"M516 205H539V224C539 226.209 537.209 228 535 228H520C517.791 228 516 226.209 516 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-8\" d=\"M571 205H594V224C594 226.209 592.209 228 590 228H575C572.791 228 571 226.209 571 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-9\" d=\"M626 205H649V224C649 226.209 647.209 228 645 228H630C627.791 228 626 226.209 626 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-10\" d=\"M681 205H704V224C704 226.209 702.209 228 700 228H685C682.791 228 681 226.209 681 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-11\" d=\"M736 205H759V224C759 226.209 757.209 228 755 228H740C737.791 228 736 226.209 736 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-12\" d=\"M791 205H814V224C814 226.209 812.209 228 810 228H795C792.791 228 791 226.209 791 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-13\" d=\"M846 205H869V224C869 226.209 867.209 228 865 228H850C847.791 228 846 226.209 846 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-14\" d=\"M901 205H924V224C924 226.209 922.209 228 920 228H905C902.791 228 901 226.209 901 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_3\">\n" +
                    "                    <path d=\"M1068 204H1258V224C1258 226.761 1255.76 229 1253 229H1073C1070.24 229 1068 226.761 1068 224V204Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-15\" d=\"M1069 205H1092V224C1092 226.209 1090.21 228 1088 228H1073C1070.79 228 1069 226.209 1069 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-16\" d=\"M1124 205H1147V224C1147 226.209 1145.21 228 1143 228H1128C1125.79 228 1124 226.209 1124 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-17\" d=\"M1179 205H1202V224C1202 226.209 1200.21 228 1198 228H1183C1180.79 228 1179 226.209 1179 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"C-18\" d=\"M1234 205H1257V224C1257 226.209 1255.21 228 1253 228H1238C1235.79 228 1234 226.209 1234 224V205Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-D\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_4\">\n" +
                    "                    <path d=\"M72 259H262V279C262 281.761 259.761 284 257 284H77C74.2386 284 72 281.761 72 279V259Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-1\" d=\"M73 260H96V279C96 281.209 94.2091 283 92 283H77C74.7909 283 73 281.209 73 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-2\" d=\"M128 260H151V279C151 281.209 149.209 283 147 283H132C129.791 283 128 281.209 128 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-3\" d=\"M183 260H206V279C206 281.209 204.209 283 202 283H187C184.791 283 183 281.209 183 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-4\" d=\"M238 260H261V279C261 281.209 259.209 283 257 283H242C239.791 283 238 281.209 238 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1_3\">\n" +
                    "                    <path d=\"M405 259H925V272C925 274.761 922.761 277 920 277H410C407.239 277 405 274.761 405 272V259Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-5\" d=\"M406 260H429V279C429 281.209 427.209 283 425 283H410C407.791 283 406 281.209 406 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-6\" d=\"M461 260H484V279C484 281.209 482.209 283 480 283H465C462.791 283 461 281.209 461 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-7\" d=\"M516 260H539V279C539 281.209 537.209 283 535 283H520C517.791 283 516 281.209 516 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-8\" d=\"M571 260H594V279C594 281.209 592.209 283 590 283H575C572.791 283 571 281.209 571 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-9\" d=\"M626 260H649V279C649 281.209 647.209 283 645 283H630C627.791 283 626 281.209 626 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-10\" d=\"M681 260H704V279C704 281.209 702.209 283 700 283H685C682.791 283 681 281.209 681 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-11\" d=\"M736 260H759V279C759 281.209 757.209 283 755 283H740C737.791 283 736 281.209 736 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-12\" d=\"M791 260H814V279C814 281.209 812.209 283 810 283H795C792.791 283 791 281.209 791 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-13\" d=\"M846 260H869V279C869 281.209 867.209 283 865 283H850C847.791 283 846 281.209 846 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-14\" d=\"M901 260H924V279C924 281.209 922.209 283 920 283H905C902.791 283 901 281.209 901 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_4\">\n" +
                    "                    <path d=\"M1068 259H1258V279C1258 281.761 1255.76 284 1253 284H1073C1070.24 284 1068 281.761 1068 279V259Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-15\" d=\"M1069 260H1092V279C1092 281.209 1090.21 283 1088 283H1073C1070.79 283 1069 281.209 1069 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-16\" d=\"M1124 260H1147V279C1147 281.209 1145.21 283 1143 283H1128C1125.79 283 1124 281.209 1124 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-17\" d=\"M1179 260H1202V279C1202 281.209 1200.21 283 1198 283H1183C1180.79 283 1179 281.209 1179 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"D-18\" d=\"M1234 260H1257V279C1257 281.209 1255.21 283 1253 283H1238C1235.79 283 1234 281.209 1234 279V260Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-E\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_5\">\n" +
                    "                    <path d=\"M72 314H262V334C262 336.761 259.761 339 257 339H77C74.2386 339 72 336.761 72 334V314Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-1\" d=\"M73 315H96V334C96 336.209 94.2091 338 92 338H77C74.7909 338 73 336.209 73 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-2\" d=\"M128 315H151V334C151 336.209 149.209 338 147 338H132C129.791 338 128 336.209 128 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-3\" d=\"M183 315H206V334C206 336.209 204.209 338 202 338H187C184.791 338 183 336.209 183 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-4\" d=\"M238 315H261V334C261 336.209 259.209 338 257 338H242C239.791 338 238 336.209 238 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1_4\">\n" +
                    "                    <path d=\"M405 314H925V327C925 329.761 922.761 332 920 332H410C407.239 332 405 329.761 405 327V314Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-5\" d=\"M406 315H429V334C429 336.209 427.209 338 425 338H410C407.791 338 406 336.209 406 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-6\" d=\"M461 315H484V334C484 336.209 482.209 338 480 338H465C462.791 338 461 336.209 461 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-7\" d=\"M516 315H539V334C539 336.209 537.209 338 535 338H520C517.791 338 516 336.209 516 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-8\" d=\"M571 315H594V334C594 336.209 592.209 338 590 338H575C572.791 338 571 336.209 571 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-9\" d=\"M626 315H649V334C649 336.209 647.209 338 645 338H630C627.791 338 626 336.209 626 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-10\" d=\"M681 315H704V334C704 336.209 702.209 338 700 338H685C682.791 338 681 336.209 681 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-11\" d=\"M736 315H759V334C759 336.209 757.209 338 755 338H740C737.791 338 736 336.209 736 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-12\" d=\"M791 315H814V334C814 336.209 812.209 338 810 338H795C792.791 338 791 336.209 791 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-13\" d=\"M846 315H869V334C869 336.209 867.209 338 865 338H850C847.791 338 846 336.209 846 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-14\" d=\"M901 315H924V334C924 336.209 922.209 338 920 338H905C902.791 338 901 336.209 901 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_5\">\n" +
                    "                    <path d=\"M1068 314H1258V334C1258 336.761 1255.76 339 1253 339H1073C1070.24 339 1068 336.761 1068 334V314Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-15\" d=\"M1069 315H1092V334C1092 336.209 1090.21 338 1088 338H1073C1070.79 338 1069 336.209 1069 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-16\" d=\"M1124 315H1147V334C1147 336.209 1145.21 338 1143 338H1128C1125.79 338 1124 336.209 1124 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-17\" d=\"M1179 315H1202V334C1202 336.209 1200.21 338 1198 338H1183C1180.79 338 1179 336.209 1179 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"E-18\" d=\"M1234 315H1257V334C1257 336.209 1255.21 338 1253 338H1238C1235.79 338 1234 336.209 1234 334V315Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-F\" class=\"row\">\n" +
                    "                <g id=\"Frame 1_5\">\n" +
                    "                    <path d=\"M405 369H925V382C925 384.761 922.761 387 920 387H410C407.239 387 405 384.761 405 382V369Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-1\" d=\"M406 370H429V389C429 391.209 427.209 393 425 393H410C407.791 393 406 391.209 406 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-2\" d=\"M461 370H484V389C484 391.209 482.209 393 480 393H465C462.791 393 461 391.209 461 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-3\" d=\"M516 370H539V389C539 391.209 537.209 393 535 393H520C517.791 393 516 391.209 516 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-4\" d=\"M571 370H594V389C594 391.209 592.209 393 590 393H575C572.791 393 571 391.209 571 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-5\" d=\"M626 370H649V389C649 391.209 647.209 393 645 393H630C627.791 393 626 391.209 626 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-6\" d=\"M681 370H704V389C704 391.209 702.209 393 700 393H685C682.791 393 681 391.209 681 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-7\" d=\"M736 370H759V389C759 391.209 757.209 393 755 393H740C737.791 393 736 391.209 736 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-8\" d=\"M791 370H814V389C814 391.209 812.209 393 810 393H795C792.791 393 791 391.209 791 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-9\" d=\"M846 370H869V389C869 391.209 867.209 393 865 393H850C847.791 393 846 391.209 846 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"F-10\" d=\"M901 370H924V389C924 391.209 922.209 393 920 393H905C902.791 393 901 391.209 901 389V370Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-G\" class=\"row\">\n" +
                    "                <g id=\"Frame 1_6\">\n" +
                    "                    <path d=\"M405 417H925V430C925 432.761 922.761 435 920 435H410C407.239 435 405 432.761 405 430V417Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-1\" d=\"M406 418H429V437C429 439.209 427.209 441 425 441H410C407.791 441 406 439.209 406 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-2\" d=\"M461 418H484V437C484 439.209 482.209 441 480 441H465C462.791 441 461 439.209 461 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-3\" d=\"M516 418H539V437C539 439.209 537.209 441 535 441H520C517.791 441 516 439.209 516 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-4\" d=\"M571 418H594V437C594 439.209 592.209 441 590 441H575C572.791 441 571 439.209 571 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-5\" d=\"M626 418H649V437C649 439.209 647.209 441 645 441H630C627.791 441 626 439.209 626 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-6\" d=\"M681 418H704V437C704 439.209 702.209 441 700 441H685C682.791 441 681 439.209 681 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-7\" d=\"M736 418H759V437C759 439.209 757.209 441 755 441H740C737.791 441 736 439.209 736 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-8\" d=\"M791 418H814V437C814 439.209 812.209 441 810 441H795C792.791 441 791 439.209 791 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-9\" d=\"M846 418H869V437C869 439.209 867.209 441 865 441H850C847.791 441 846 439.209 846 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"G-10\" d=\"M901 418H924V437C924 439.209 922.209 441 920 441H905C902.791 441 901 439.209 901 437V418Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-H\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_6\">\n" +
                    "                    <path d=\"M72 465H262V485C262 487.761 259.761 490 257 490H77C74.2386 490 72 487.761 72 485V465Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-1\" d=\"M73 466H96V485C96 487.209 94.2091 489 92 489H77C74.7909 489 73 487.209 73 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-2\" d=\"M128 466H151V485C151 487.209 149.209 489 147 489H132C129.791 489 128 487.209 128 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-3\" d=\"M183 466H206V485C206 487.209 204.209 489 202 489H187C184.791 489 183 487.209 183 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-4\" d=\"M238 466H261V485C261 487.209 259.209 489 257 489H242C239.791 489 238 487.209 238 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1_7\">\n" +
                    "                    <path d=\"M405 465H925V478C925 480.761 922.761 483 920 483H410C407.239 483 405 480.761 405 478V465Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-5\" d=\"M406 466H429V485C429 487.209 427.209 489 425 489H410C407.791 489 406 487.209 406 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-6\" d=\"M461 466H484V485C484 487.209 482.209 489 480 489H465C462.791 489 461 487.209 461 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-7\" d=\"M516 466H539V485C539 487.209 537.209 489 535 489H520C517.791 489 516 487.209 516 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-8\" d=\"M571 466H594V485C594 487.209 592.209 489 590 489H575C572.791 489 571 487.209 571 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-9\" d=\"M626 466H649V485C649 487.209 647.209 489 645 489H630C627.791 489 626 487.209 626 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-10\" d=\"M681 466H704V485C704 487.209 702.209 489 700 489H685C682.791 489 681 487.209 681 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-11\" d=\"M736 466H759V485C759 487.209 757.209 489 755 489H740C737.791 489 736 487.209 736 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-12\" d=\"M791 466H814V485C814 487.209 812.209 489 810 489H795C792.791 489 791 487.209 791 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-13\" d=\"M846 466H869V485C869 487.209 867.209 489 865 489H850C847.791 489 846 487.209 846 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-14\" d=\"M901 466H924V485C924 487.209 922.209 489 920 489H905C902.791 489 901 487.209 901 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_6\">\n" +
                    "                    <path d=\"M1068 465H1258V485C1258 487.761 1255.76 490 1253 490H1073C1070.24 490 1068 487.761 1068 485V465Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-15\" d=\"M1069 466H1092V485C1092 487.209 1090.21 489 1088 489H1073C1070.79 489 1069 487.209 1069 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-16\" d=\"M1124 466H1147V485C1147 487.209 1145.21 489 1143 489H1128C1125.79 489 1124 487.209 1124 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-17\" d=\"M1179 466H1202V485C1202 487.209 1200.21 489 1198 489H1183C1180.79 489 1179 487.209 1179 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"H-18\" d=\"M1234 466H1257V485C1257 487.209 1255.21 489 1253 489H1238C1235.79 489 1234 487.209 1234 485V466Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-I\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_7\">\n" +
                    "                    <path d=\"M72 520H262V540C262 542.761 259.761 545 257 545H77C74.2386 545 72 542.761 72 540V520Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-1\" d=\"M73 521H96V540C96 542.209 94.2091 544 92 544H77C74.7909 544 73 542.209 73 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-2\" d=\"M128 521H151V540C151 542.209 149.209 544 147 544H132C129.791 544 128 542.209 128 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-3\" d=\"M183 521H206V540C206 542.209 204.209 544 202 544H187C184.791 544 183 542.209 183 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-4\" d=\"M238 521H261V540C261 542.209 259.209 544 257 544H242C239.791 544 238 542.209 238 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1_8\">\n" +
                    "                    <path d=\"M405 520H925V533C925 535.761 922.761 538 920 538H410C407.239 538 405 535.761 405 533V520Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-5\" d=\"M406 521H429V540C429 542.209 427.209 544 425 544H410C407.791 544 406 542.209 406 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-6\" d=\"M461 521H484V540C484 542.209 482.209 544 480 544H465C462.791 544 461 542.209 461 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-7\" d=\"M516 521H539V540C539 542.209 537.209 544 535 544H520C517.791 544 516 542.209 516 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-8\" d=\"M571 521H594V540C594 542.209 592.209 544 590 544H575C572.791 544 571 542.209 571 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-9\" d=\"M626 521H649V540C649 542.209 647.209 544 645 544H630C627.791 544 626 542.209 626 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-10\" d=\"M681 521H704V540C704 542.209 702.209 544 700 544H685C682.791 544 681 542.209 681 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-11\" d=\"M736 521H759V540C759 542.209 757.209 544 755 544H740C737.791 544 736 542.209 736 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-12\" d=\"M791 521H814V540C814 542.209 812.209 544 810 544H795C792.791 544 791 542.209 791 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-13\" d=\"M846 521H869V540C869 542.209 867.209 544 865 544H850C847.791 544 846 542.209 846 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-14\" d=\"M901 521H924V540C924 542.209 922.209 544 920 544H905C902.791 544 901 542.209 901 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_7\">\n" +
                    "                    <path d=\"M1068 520H1258V540C1258 542.761 1255.76 545 1253 545H1073C1070.24 545 1068 542.761 1068 540V520Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-15\" d=\"M1069 521H1092V540C1092 542.209 1090.21 544 1088 544H1073C1070.79 544 1069 542.209 1069 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-16\" d=\"M1124 521H1147V540C1147 542.209 1145.21 544 1143 544H1128C1125.79 544 1124 542.209 1124 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-17\" d=\"M1179 521H1202V540C1202 542.209 1200.21 544 1198 544H1183C1180.79 544 1179 542.209 1179 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"I-18\" d=\"M1234 521H1257V540C1257 542.209 1255.21 544 1253 544H1238C1235.79 544 1234 542.209 1234 540V521Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-J\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_8\">\n" +
                    "                    <path d=\"M72 575H262V595C262 597.761 259.761 600 257 600H77C74.2386 600 72 597.761 72 595V575Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-1\" d=\"M73 576H96V595C96 597.209 94.2091 599 92 599H77C74.7909 599 73 597.209 73 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-2\" d=\"M128 576H151V595C151 597.209 149.209 599 147 599H132C129.791 599 128 597.209 128 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-3\" d=\"M183 576H206V595C206 597.209 204.209 599 202 599H187C184.791 599 183 597.209 183 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-4\" d=\"M238 576H261V595C261 597.209 259.209 599 257 599H242C239.791 599 238 597.209 238 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1_9\">\n" +
                    "                    <path d=\"M405 575H925V588C925 590.761 922.761 593 920 593H410C407.239 593 405 590.761 405 588V575Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-5\" d=\"M406 576H429V595C429 597.209 427.209 599 425 599H410C407.791 599 406 597.209 406 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-6\" d=\"M461 576H484V595C484 597.209 482.209 599 480 599H465C462.791 599 461 597.209 461 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-7\" d=\"M516 576H539V595C539 597.209 537.209 599 535 599H520C517.791 599 516 597.209 516 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-8\" d=\"M571 576H594V595C594 597.209 592.209 599 590 599H575C572.791 599 571 597.209 571 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-9\" d=\"M626 576H649V595C649 597.209 647.209 599 645 599H630C627.791 599 626 597.209 626 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-10\" d=\"M681 576H704V595C704 597.209 702.209 599 700 599H685C682.791 599 681 597.209 681 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-11\" d=\"M736 576H759V595C759 597.209 757.209 599 755 599H740C737.791 599 736 597.209 736 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-12\" d=\"M791 576H814V595C814 597.209 812.209 599 810 599H795C792.791 599 791 597.209 791 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-13\" d=\"M846 576H869V595C869 597.209 867.209 599 865 599H850C847.791 599 846 597.209 846 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-14\" d=\"M901 576H924V595C924 597.209 922.209 599 920 599H905C902.791 599 901 597.209 901 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_8\">\n" +
                    "                    <path d=\"M1068 575H1258V595C1258 597.761 1255.76 600 1253 600H1073C1070.24 600 1068 597.761 1068 595V575Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-15\" d=\"M1069 576H1092V595C1092 597.209 1090.21 599 1088 599H1073C1070.79 599 1069 597.209 1069 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-16\" d=\"M1124 576H1147V595C1147 597.209 1145.21 599 1143 599H1128C1125.79 599 1124 597.209 1124 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-17\" d=\"M1179 576H1202V595C1202 597.209 1200.21 599 1198 599H1183C1180.79 599 1179 597.209 1179 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"J-18\" d=\"M1234 576H1257V595C1257 597.209 1255.21 599 1253 599H1238C1235.79 599 1234 597.209 1234 595V576Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "            <g id=\"ROW-K\" class=\"row\">\n" +
                    "                <g id=\"Frame 2_9\">\n" +
                    "                    <path d=\"M72 630H262V650C262 652.761 259.761 655 257 655H77C74.2386 655 72 652.761 72 650V630Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-1\" d=\"M73 631H96V650C96 652.209 94.2091 654 92 654H77C74.7909 654 73 652.209 73 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-2\" d=\"M128 631H151V650C151 652.209 149.209 654 147 654H132C129.791 654 128 652.209 128 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-3\" d=\"M183 631H206V650C206 652.209 204.209 654 202 654H187C184.791 654 183 652.209 183 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-4\" d=\"M238 631H261V650C261 652.209 259.209 654 257 654H242C239.791 654 238 652.209 238 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 1_10\">\n" +
                    "                    <path d=\"M405 630H925V643C925 645.761 922.761 648 920 648H410C407.239 648 405 645.761 405 643V630Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-5\" d=\"M406 631H429V650C429 652.209 427.209 654 425 654H410C407.791 654 406 652.209 406 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-6\" d=\"M461 631H484V650C484 652.209 482.209 654 480 654H465C462.791 654 461 652.209 461 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-7\" d=\"M516 631H539V650C539 652.209 537.209 654 535 654H520C517.791 654 516 652.209 516 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-8\" d=\"M571 631H594V650C594 652.209 592.209 654 590 654H575C572.791 654 571 652.209 571 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-9\" d=\"M626 631H649V650C649 652.209 647.209 654 645 654H630C627.791 654 626 652.209 626 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-10\" d=\"M681 631H704V650C704 652.209 702.209 654 700 654H685C682.791 654 681 652.209 681 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-11\" d=\"M736 631H759V650C759 652.209 757.209 654 755 654H740C737.791 654 736 652.209 736 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-12\" d=\"M791 631H814V650C814 652.209 812.209 654 810 654H795C792.791 654 791 652.209 791 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-13\" d=\"M846 631H869V650C869 652.209 867.209 654 865 654H850C847.791 654 846 652.209 846 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-14\" d=\"M901 631H924V650C924 652.209 922.209 654 920 654H905C902.791 654 901 652.209 901 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "                <g id=\"Frame 3_9\">\n" +
                    "                    <path d=\"M1068 630H1258V650C1258 652.761 1255.76 655 1253 655H1073C1070.24 655 1068 652.761 1068 650V630Z\" fill=\"white\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-15\" d=\"M1069 631H1092V650C1092 652.209 1090.21 654 1088 654H1073C1070.79 654 1069 652.209 1069 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-16\" d=\"M1124 631H1147V650C1147 652.209 1145.21 654 1143 654H1128C1125.79 654 1124 652.209 1124 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-17\" d=\"M1179 631H1202V650C1202 652.209 1200.21 654 1198 654H1183C1180.79 654 1179 652.209 1179 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                    <path class=\"seat\" id=\"K-18\" d=\"M1234 631H1257V650C1257 652.209 1255.21 654 1253 654H1238C1235.79 654 1234 652.209 1234 650V631Z\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                    "                </g>\n" +
                    "            </g>\n" +
                    "        </g>\n" +
                    "        <g id=\"Door2\">\n" +
                    "            <rect id=\"Background_3\" x=\"1230\" width=\"100\" height=\"12\" fill=\"#C4C4C4\"/>\n" +
                    "            <path id=\"Exit\" d=\"M1275.17 27.7422H1270.24V31.7734H1275.97V33H1268.74V21.625H1275.89V22.8594H1270.24V26.5156H1275.17V27.7422ZM1280.45 27.6328L1282.32 24.5469H1284.01L1281.24 28.7266L1284.09 33H1282.42L1280.47 29.8359L1278.52 33H1276.84L1279.69 28.7266L1276.92 24.5469H1278.59L1280.45 27.6328ZM1287.12 33H1285.67V24.5469H1287.12V33ZM1285.55 22.3047C1285.55 22.0703 1285.62 21.8724 1285.77 21.7109C1285.91 21.5495 1286.12 21.4688 1286.41 21.4688C1286.69 21.4688 1286.9 21.5495 1287.05 21.7109C1287.19 21.8724 1287.27 22.0703 1287.27 22.3047C1287.27 22.5391 1287.19 22.7344 1287.05 22.8906C1286.9 23.0469 1286.69 23.125 1286.41 23.125C1286.12 23.125 1285.91 23.0469 1285.77 22.8906C1285.62 22.7344 1285.55 22.5391 1285.55 22.3047ZM1291.4 22.5V24.5469H1292.98V25.6641H1291.4V30.9062C1291.4 31.2448 1291.47 31.5 1291.61 31.6719C1291.75 31.8385 1291.99 31.9219 1292.33 31.9219C1292.49 31.9219 1292.72 31.8906 1293.02 31.8281V33C1292.64 33.1042 1292.27 33.1562 1291.91 33.1562C1291.26 33.1562 1290.77 32.9609 1290.45 32.5703C1290.12 32.1797 1289.95 31.625 1289.95 30.9062V25.6641H1288.41V24.5469H1289.95V22.5H1291.4Z\" fill=\"black\"/>\n" +
                    "        </g>\n" +
                    "        <g id=\"Door1\">\n" +
                    "            <rect id=\"Background_4\" width=\"100\" height=\"12\" fill=\"#C4C4C4\"/>\n" +
                    "            <path id=\"Exit_2\" d=\"M45.1719 27.7422H40.2422V31.7734H45.9688V33H38.7422V21.625H45.8906V22.8594H40.2422V26.5156H45.1719V27.7422ZM50.4453 27.6328L52.3203 24.5469H54.0078L51.2422 28.7266L54.0938 33H52.4219L50.4688 29.8359L48.5156 33H46.8359L49.6875 28.7266L46.9219 24.5469H48.5938L50.4453 27.6328ZM57.1172 33H55.6719V24.5469H57.1172V33ZM55.5547 22.3047C55.5547 22.0703 55.625 21.8724 55.7656 21.7109C55.9115 21.5495 56.125 21.4688 56.4062 21.4688C56.6875 21.4688 56.901 21.5495 57.0469 21.7109C57.1927 21.8724 57.2656 22.0703 57.2656 22.3047C57.2656 22.5391 57.1927 22.7344 57.0469 22.8906C56.901 23.0469 56.6875 23.125 56.4062 23.125C56.125 23.125 55.9115 23.0469 55.7656 22.8906C55.625 22.7344 55.5547 22.5391 55.5547 22.3047ZM61.3984 22.5V24.5469H62.9766V25.6641H61.3984V30.9062C61.3984 31.2448 61.4688 31.5 61.6094 31.6719C61.75 31.8385 61.9896 31.9219 62.3281 31.9219C62.4948 31.9219 62.724 31.8906 63.0156 31.8281V33C62.6354 33.1042 62.2656 33.1562 61.9062 33.1562C61.2604 33.1562 60.7734 32.9609 60.4453 32.5703C60.1172 32.1797 59.9531 31.625 59.9531 30.9062V25.6641H58.4141V24.5469H59.9531V22.5H61.3984Z\" fill=\"black\"/>\n" +
                    "        </g>\n" +
                    "        <g id=\"Door3\">\n" +
                    "            <rect id=\"Background_5\" y=\"743\" width=\"169\" height=\"19\" fill=\"#C4C4C4\"/>\n" +
                    "            <path id=\"Entrance\" d=\"M60.9219 733.742H55.9922V737.773H61.7188V739H54.4922V727.625H61.6406V728.859H55.9922V732.516H60.9219V733.742ZM64.7266 730.547L64.7734 731.609C65.4193 730.797 66.263 730.391 67.3047 730.391C69.0911 730.391 69.9922 731.398 70.0078 733.414V739H68.5625V733.406C68.5573 732.797 68.4167 732.346 68.1406 732.055C67.8698 731.763 67.4453 731.617 66.8672 731.617C66.3984 731.617 65.987 731.742 65.6328 731.992C65.2786 732.242 65.0026 732.57 64.8047 732.977V739H63.3594V730.547H64.7266ZM74.1484 728.5V730.547H75.7266V731.664H74.1484V736.906C74.1484 737.245 74.2188 737.5 74.3594 737.672C74.5 737.839 74.7396 737.922 75.0781 737.922C75.2448 737.922 75.474 737.891 75.7656 737.828V739C75.3854 739.104 75.0156 739.156 74.6562 739.156C74.0104 739.156 73.5234 738.961 73.1953 738.57C72.8672 738.18 72.7031 737.625 72.7031 736.906V731.664H71.1641V730.547H72.7031V728.5H74.1484ZM81.5078 731.844C81.2891 731.807 81.0521 731.789 80.7969 731.789C79.849 731.789 79.2057 732.193 78.8672 733V739H77.4219V730.547H78.8281L78.8516 731.523C79.3255 730.768 79.9974 730.391 80.8672 730.391C81.1484 730.391 81.362 730.427 81.5078 730.5V731.844ZM87.75 739C87.6667 738.833 87.599 738.536 87.5469 738.109C86.875 738.807 86.0729 739.156 85.1406 739.156C84.3073 739.156 83.6224 738.922 83.0859 738.453C82.5547 737.979 82.2891 737.38 82.2891 736.656C82.2891 735.776 82.6224 735.094 83.2891 734.609C83.9609 734.12 84.9036 733.875 86.1172 733.875H87.5234V733.211C87.5234 732.706 87.3724 732.305 87.0703 732.008C86.7682 731.706 86.3229 731.555 85.7344 731.555C85.2188 731.555 84.7865 731.685 84.4375 731.945C84.0885 732.206 83.9141 732.521 83.9141 732.891H82.4609C82.4609 732.469 82.6094 732.062 82.9062 731.672C83.2083 731.276 83.6146 730.964 84.125 730.734C84.6406 730.505 85.2057 730.391 85.8203 730.391C86.7943 730.391 87.5573 730.635 88.1094 731.125C88.6615 731.609 88.9479 732.279 88.9688 733.133V737.023C88.9688 737.799 89.0677 738.417 89.2656 738.875V739H87.75ZM85.3516 737.898C85.8047 737.898 86.2344 737.781 86.6406 737.547C87.0469 737.312 87.3411 737.008 87.5234 736.633V734.898H86.3906C84.6198 734.898 83.7344 735.417 83.7344 736.453C83.7344 736.906 83.8854 737.26 84.1875 737.516C84.4896 737.771 84.8776 737.898 85.3516 737.898ZM92.6016 730.547L92.6484 731.609C93.2943 730.797 94.138 730.391 95.1797 730.391C96.9661 730.391 97.8672 731.398 97.8828 733.414V739H96.4375V733.406C96.4323 732.797 96.2917 732.346 96.0156 732.055C95.7448 731.763 95.3203 731.617 94.7422 731.617C94.2734 731.617 93.862 731.742 93.5078 731.992C93.1536 732.242 92.8776 732.57 92.6797 732.977V739H91.2344V730.547H92.6016ZM103.453 737.977C103.969 737.977 104.419 737.82 104.805 737.508C105.19 737.195 105.404 736.805 105.445 736.336H106.812C106.786 736.82 106.62 737.281 106.312 737.719C106.005 738.156 105.594 738.505 105.078 738.766C104.568 739.026 104.026 739.156 103.453 739.156C102.302 739.156 101.385 738.773 100.703 738.008C100.026 737.237 99.6875 736.185 99.6875 734.852V734.609C99.6875 733.786 99.8385 733.055 100.141 732.414C100.443 731.773 100.875 731.276 101.438 730.922C102.005 730.568 102.674 730.391 103.445 730.391C104.393 730.391 105.18 730.674 105.805 731.242C106.435 731.81 106.771 732.547 106.812 733.453H105.445C105.404 732.906 105.195 732.458 104.82 732.109C104.451 731.755 103.992 731.578 103.445 731.578C102.711 731.578 102.141 731.844 101.734 732.375C101.333 732.901 101.133 733.664 101.133 734.664V734.938C101.133 735.911 101.333 736.661 101.734 737.188C102.135 737.714 102.708 737.977 103.453 737.977ZM111.945 739.156C110.799 739.156 109.867 738.781 109.148 738.031C108.43 737.276 108.07 736.268 108.07 735.008V734.742C108.07 733.904 108.229 733.156 108.547 732.5C108.87 731.839 109.318 731.323 109.891 730.953C110.469 730.578 111.094 730.391 111.766 730.391C112.865 730.391 113.719 730.753 114.328 731.477C114.938 732.201 115.242 733.237 115.242 734.586V735.188H109.516C109.536 736.021 109.779 736.695 110.242 737.211C110.711 737.721 111.305 737.977 112.023 737.977C112.534 737.977 112.966 737.872 113.32 737.664C113.674 737.456 113.984 737.18 114.25 736.836L115.133 737.523C114.424 738.612 113.362 739.156 111.945 739.156ZM111.766 731.578C111.182 731.578 110.693 731.792 110.297 732.219C109.901 732.641 109.656 733.234 109.562 734H113.797V733.891C113.755 733.156 113.557 732.589 113.203 732.188C112.849 731.781 112.37 731.578 111.766 731.578Z\" fill=\"black\"/>\n" +
                    "        </g>\n" +
                    "        <g id=\"Door4\">\n" +
                    "            <rect id=\"Background_6\" x=\"1161\" y=\"743\" width=\"169\" height=\"19\" fill=\"#C4C4C4\"/>\n" +
                    "            <path id=\"Entrance_2\" d=\"M1221.92 733.742H1216.99V737.773H1222.72V739H1215.49V727.625H1222.64V728.859H1216.99V732.516H1221.92V733.742ZM1225.73 730.547L1225.77 731.609C1226.42 730.797 1227.26 730.391 1228.3 730.391C1230.09 730.391 1230.99 731.398 1231.01 733.414V739H1229.56V733.406C1229.56 732.797 1229.42 732.346 1229.14 732.055C1228.87 731.763 1228.45 731.617 1227.87 731.617C1227.4 731.617 1226.99 731.742 1226.63 731.992C1226.28 732.242 1226 732.57 1225.8 732.977V739H1224.36V730.547H1225.73ZM1235.15 728.5V730.547H1236.73V731.664H1235.15V736.906C1235.15 737.245 1235.22 737.5 1235.36 737.672C1235.5 737.839 1235.74 737.922 1236.08 737.922C1236.24 737.922 1236.47 737.891 1236.77 737.828V739C1236.39 739.104 1236.02 739.156 1235.66 739.156C1235.01 739.156 1234.52 738.961 1234.2 738.57C1233.87 738.18 1233.7 737.625 1233.7 736.906V731.664H1232.16V730.547H1233.7V728.5H1235.15ZM1242.51 731.844C1242.29 731.807 1242.05 731.789 1241.8 731.789C1240.85 731.789 1240.21 732.193 1239.87 733V739H1238.42V730.547H1239.83L1239.85 731.523C1240.33 730.768 1241 730.391 1241.87 730.391C1242.15 730.391 1242.36 730.427 1242.51 730.5V731.844ZM1248.75 739C1248.67 738.833 1248.6 738.536 1248.55 738.109C1247.88 738.807 1247.07 739.156 1246.14 739.156C1245.31 739.156 1244.62 738.922 1244.09 738.453C1243.55 737.979 1243.29 737.38 1243.29 736.656C1243.29 735.776 1243.62 735.094 1244.29 734.609C1244.96 734.12 1245.9 733.875 1247.12 733.875H1248.52V733.211C1248.52 732.706 1248.37 732.305 1248.07 732.008C1247.77 731.706 1247.32 731.555 1246.73 731.555C1246.22 731.555 1245.79 731.685 1245.44 731.945C1245.09 732.206 1244.91 732.521 1244.91 732.891H1243.46C1243.46 732.469 1243.61 732.062 1243.91 731.672C1244.21 731.276 1244.61 730.964 1245.12 730.734C1245.64 730.505 1246.21 730.391 1246.82 730.391C1247.79 730.391 1248.56 730.635 1249.11 731.125C1249.66 731.609 1249.95 732.279 1249.97 733.133V737.023C1249.97 737.799 1250.07 738.417 1250.27 738.875V739H1248.75ZM1246.35 737.898C1246.8 737.898 1247.23 737.781 1247.64 737.547C1248.05 737.312 1248.34 737.008 1248.52 736.633V734.898H1247.39C1245.62 734.898 1244.73 735.417 1244.73 736.453C1244.73 736.906 1244.89 737.26 1245.19 737.516C1245.49 737.771 1245.88 737.898 1246.35 737.898ZM1253.6 730.547L1253.65 731.609C1254.29 730.797 1255.14 730.391 1256.18 730.391C1257.97 730.391 1258.87 731.398 1258.88 733.414V739H1257.44V733.406C1257.43 732.797 1257.29 732.346 1257.02 732.055C1256.74 731.763 1256.32 731.617 1255.74 731.617C1255.27 731.617 1254.86 731.742 1254.51 731.992C1254.15 732.242 1253.88 732.57 1253.68 732.977V739H1252.23V730.547H1253.6ZM1264.45 737.977C1264.97 737.977 1265.42 737.82 1265.8 737.508C1266.19 737.195 1266.4 736.805 1266.45 736.336H1267.81C1267.79 736.82 1267.62 737.281 1267.31 737.719C1267.01 738.156 1266.59 738.505 1266.08 738.766C1265.57 739.026 1265.03 739.156 1264.45 739.156C1263.3 739.156 1262.39 738.773 1261.7 738.008C1261.03 737.237 1260.69 736.185 1260.69 734.852V734.609C1260.69 733.786 1260.84 733.055 1261.14 732.414C1261.44 731.773 1261.88 731.276 1262.44 730.922C1263.01 730.568 1263.67 730.391 1264.45 730.391C1265.39 730.391 1266.18 730.674 1266.8 731.242C1267.43 731.81 1267.77 732.547 1267.81 733.453H1266.45C1266.4 732.906 1266.2 732.458 1265.82 732.109C1265.45 731.755 1264.99 731.578 1264.45 731.578C1263.71 731.578 1263.14 731.844 1262.73 732.375C1262.33 732.901 1262.13 733.664 1262.13 734.664V734.938C1262.13 735.911 1262.33 736.661 1262.73 737.188C1263.14 737.714 1263.71 737.977 1264.45 737.977ZM1272.95 739.156C1271.8 739.156 1270.87 738.781 1270.15 738.031C1269.43 737.276 1269.07 736.268 1269.07 735.008V734.742C1269.07 733.904 1269.23 733.156 1269.55 732.5C1269.87 731.839 1270.32 731.323 1270.89 730.953C1271.47 730.578 1272.09 730.391 1272.77 730.391C1273.86 730.391 1274.72 730.753 1275.33 731.477C1275.94 732.201 1276.24 733.237 1276.24 734.586V735.188H1270.52C1270.54 736.021 1270.78 736.695 1271.24 737.211C1271.71 737.721 1272.3 737.977 1273.02 737.977C1273.53 737.977 1273.97 737.872 1274.32 737.664C1274.67 737.456 1274.98 737.18 1275.25 736.836L1276.13 737.523C1275.42 738.612 1274.36 739.156 1272.95 739.156ZM1272.77 731.578C1272.18 731.578 1271.69 731.792 1271.3 732.219C1270.9 732.641 1270.66 733.234 1270.56 734H1274.8V733.891C1274.76 733.156 1274.56 732.589 1274.2 732.188C1273.85 731.781 1273.37 731.578 1272.77 731.578Z\" fill=\"black\"/>\n" +
                    "        </g>\n" +
                    "    </g>\n" +
                    "</svg>";

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
