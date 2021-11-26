package com.cinema.booking.category;

import com.cinema.booking.movie.Movie;
import lombok.*;

import javax.persistence.*;

@Entity(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NonNull
    private Movie movie;

    @Column(nullable = false)
    @NonNull
    private String label;

    @Column(nullable = false)
    @NonNull
    private Integer price;

    @Column(nullable = false)
    @NonNull
    private String colour;

    public Category(@NonNull Movie movie, @NonNull String label, @NonNull Integer price, @NonNull String colour) {
        this.movie = movie;
        this.label = label;
        this.price = price;
        this.colour = colour;
    }
}
