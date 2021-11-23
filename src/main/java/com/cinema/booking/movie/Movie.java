package com.cinema.booking.movie;

import com.cinema.booking.category.Category;
import com.cinema.booking.show.Show;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String body;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Show> shows = new HashSet<>();

    public Movie(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }
}
