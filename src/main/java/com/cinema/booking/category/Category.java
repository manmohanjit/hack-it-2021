package com.cinema.booking.category;

import com.cinema.booking.movie.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    @NonNull
    private Movie movie;

    @Column(nullable = false)
    @NonNull
    private String label;

    @Column(nullable = false)
    @NonNull
    private Integer price;

    public Category(@NonNull Movie movie, @NonNull String label, @NonNull Integer price) {
        this.movie = movie;
        this.label = label;
        this.price = price;
    }
}
