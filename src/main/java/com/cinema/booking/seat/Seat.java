package com.cinema.booking.seat;

import com.cinema.booking.hall.Hall;
import lombok.*;

import javax.persistence.*;

@Entity(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NonNull
    private Hall hall;

    @Column(nullable = false)
    @NonNull
    private String label;

    public Seat(@NonNull Hall hall, @NonNull String label) {
        this.hall = hall;
        this.label = label;
    }
}
