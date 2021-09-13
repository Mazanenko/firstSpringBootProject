package com.mazanenko.petproject.firstspringcrudapp.dao.impl;

import com.mazanenko.petproject.firstspringcrudapp.dao.BookDAO;
import com.mazanenko.petproject.firstspringcrudapp.dao.mapper.BookMapper;
import com.mazanenko.petproject.firstspringcrudapp.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAOImpl implements BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, author, genre, description, available_quantity) VALUES (?, ?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getGenre(), book.getDescription(), book.getAvailableQuantity());
    }

    @Override
    public Book read(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new BookMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Book> readAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    @Override
    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, genre = ?, description = ?, available_quantity = ? WHERE id = ?",
                book.getName(), book.getAuthor(), book.getGenre(), book.getDescription(), book.getAvailableQuantity(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
}
