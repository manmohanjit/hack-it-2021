package com.cinema.booking.customer;

import javax.persistence.*;

@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String phoneCountry;

    public Customer() {
    }

    public Customer(Long id, String name, String email, String phone, String phoneCountry) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.phoneCountry = phoneCountry;
    }

    public Customer(String name, String email, String phone, String phoneCountry) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.phoneCountry = phoneCountry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCountry() {
        return phoneCountry;
    }

    public void setPhoneCountry(String phoneCountry) {
        this.phoneCountry = phoneCountry;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneCountry='" + phoneCountry + '\'' +
                '}';
    }
}
