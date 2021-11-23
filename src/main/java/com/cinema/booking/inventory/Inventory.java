package com.cinema.booking.inventory;

import com.cinema.booking.category.Category;
import com.cinema.booking.order.Order;
import com.cinema.booking.seat.Seat;
import com.cinema.booking.show.Show;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "inventory")
@Table(uniqueConstraints = {
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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @NonNull
    private Show show;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @NonNull
    private Category category;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @NonNull
    private Seat seat;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryStatus status = InventoryStatus.AVAILABLE;

    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean enabled;

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    public Inventory(@NonNull Show show, @NonNull Category category, @NonNull Seat seat, InventoryStatus status, Boolean enabled) {
        this.show = show;
        this.category = category;
        this.seat = seat;

        this.status = status == null ? InventoryStatus.AVAILABLE : status;
        this.enabled = enabled == null || enabled;
    }
}
