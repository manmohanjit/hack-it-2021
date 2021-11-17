package com.cinema.booking.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());

        if (customerOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }

        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        if(!customerRepository.existsById(id)) {
            throw new IllegalStateException("User with ID "+id+" doesnt exist");
        }

        customerRepository.deleteById(id);
    }

    @Transactional
    public void updateCustomer(Long id, String name, String email) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with ID "+id+" doesnt exist"));

        if(name != null && name.length() > 0 && !Objects.equals(customer.getName(), name)) {
            customer.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)) {
            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);

            if(customerOptional.isPresent()) {
                throw new IllegalStateException("Email taken");
            }

            customer.setEmail(email);
        }
    }
}
