package com.cinema.booking.inventory;

import com.cinema.booking.category.Category;
import com.cinema.booking.hall.Hall;
import com.cinema.booking.seat.Seat;
import com.cinema.booking.show.Show;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity(name = "inventory")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"show_id", "category_id", "seat_id"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JsonBackReference
    @NonNull
    private Show show;

    @ManyToOne(optional = false)
    @JsonBackReference
    @NonNull
    private Category category;

    @ManyToOne(optional = false)
    @JsonBackReference
    @NonNull
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(columnDefinition="tinyint(1) default 1")
    private Boolean enabled;

    public Inventory(@NonNull Show show, @NonNull Category category, @NonNull Seat seat, Status status, Boolean enabled) {
        this.show = show;
        this.category = category;
        this.seat = seat;

        this.status = status == null ? Status.AVAILABLE : status;
        this.enabled = enabled == null || enabled;
    }
}
