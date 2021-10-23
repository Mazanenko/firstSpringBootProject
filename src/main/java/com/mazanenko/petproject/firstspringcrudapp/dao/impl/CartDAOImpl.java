package com.mazanenko.petproject.firstspringcrudapp.dao.impl;

import com.mazanenko.petproject.firstspringcrudapp.dao.CartDAO;
import com.mazanenko.petproject.firstspringcrudapp.dao.mapper.CartMapper;
import com.mazanenko.petproject.firstspringcrudapp.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDAOImpl implements CartDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Cart cart) {
        jdbcTemplate.update("INSERT INTO cart (customer_id) VALUES (?)", cart.getCustomerId());
    }

    @Override
    public Cart read(int id) {
        return jdbcTemplate.query("SELECT * FROM cart WHERE id = ?", new CartMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public Cart readByCustomerId(int id) {
        return jdbcTemplate.query("SELECT * FROM cart WHERE customer_id = ?", new CartMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Cart> readAll() {
        return jdbcTemplate.query("SELECT * FROM cart", new CartMapper());
    }

    @Override
    public void update(int id, Cart cart) {
        jdbcTemplate.update("UPDATE cart SET customer_id = ? WHERE id = ?", cart.getCustomerId(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM order_table WHERE id = ?", id);
    }
}