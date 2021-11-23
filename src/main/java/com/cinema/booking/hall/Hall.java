package com.cinema.booking.hall;

import com.cinema.booking.seat.Seat;
import com.cinema.booking.show.Show;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "halls")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String title;

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Seat> seats = new HashSet<>();

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Show> shows = new HashSet<>();

    public Hall(@NonNull String title) {
        this.title = title;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }
}
