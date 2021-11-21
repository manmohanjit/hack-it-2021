package com.cinema.booking.show;

import com.cinema.booking.hall.Hall;
import com.cinema.booking.movie.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "shows")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NonNull
    private Movie movie;

    @ManyToOne(optional = false)
    @JsonBackReference
    @NonNull
    private Hall hall;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime startsAt;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime endsAt;

    public Show(@NonNull Movie movie, @NonNull Hall hall, @NonNull LocalDateTime startsAt, @NonNull LocalDateTime endsAt) {
        this.movie = movie;
        this.hall = hall;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }
}
