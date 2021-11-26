package com.cinema.booking.show;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowData {
    private Long id;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private Long hallId;
    private Long movieId;
}
