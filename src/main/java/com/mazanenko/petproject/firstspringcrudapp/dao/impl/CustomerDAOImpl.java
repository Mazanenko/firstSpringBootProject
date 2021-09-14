package com.mazanenko.petproject.firstspringcrudapp.dao.impl;

import com.mazanenko.petproject.firstspringcrudapp.dao.DAO;
import com.mazanenko.petproject.firstspringcrudapp.dao.mapper.CustomerMapper;
import com.mazanenko.petproject.firstspringcrudapp.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDAOImpl implements DAO<Customer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(Customer customer) {
        jdbcTemplate.update("INSERT INTO customer (name, surname, phone, email) VALUES (?, ?, ?, ?)",
        customer.getName(), customer.getSurname(), customer.getPhone(), customer.getEmail());
    }

    @Override
    public Customer read(int id) {
        return jdbcTemplate.query ("SELECT * FROM customer WHERE id = ?", new CustomerMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Customer> readAll() {
        return jdbcTemplate.query("SELECT * FROM customer", new CustomerMapper());
    }

    @Override
    public void update(int id, Customer customer) {
        jdbcTemplate.update("UPDATE customer SET name = ?, surname = ?, phone = ?, email = ? WHERE id = ?",
                customer.getName(), customer.getSurname(), customer.getPhone(), customer.getEmail());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM customer WHERE id = ?", id);
    }
}