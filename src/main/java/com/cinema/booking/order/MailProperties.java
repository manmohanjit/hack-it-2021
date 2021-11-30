package com.cinema.booking.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.mail")
@Setter
@Getter
public class MailProperties {

    private String from;

}
