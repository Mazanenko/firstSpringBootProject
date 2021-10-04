package com.mazanenko.petproject.firstspringcrudapp.service.impl;

import com.mazanenko.petproject.firstspringcrudapp.dao.impl.CartDAO;
import com.mazanenko.petproject.firstspringcrudapp.dao.impl.CustomerDAO;
import com.mazanenko.petproject.firstspringcrudapp.dao.impl.DeliveryAddressDAO;
import com.mazanenko.petproject.firstspringcrudapp.entity.Cart;
import com.mazanenko.petproject.firstspringcrudapp.entity.Customer;
import com.mazanenko.petproject.firstspringcrudapp.entity.DeliveryAddress;
import com.mazanenko.petproject.firstspringcrudapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final DeliveryAddressDAO addressDAO;
    private final CartDAO cartDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, DeliveryAddressDAO addressDAO, CartDAO cartDAO) {
        this.customerDAO = customerDAO;
        this.addressDAO = addressDAO;
        this.cartDAO = cartDAO;
    }

    @Override
    public void createCustomer(Customer customer, DeliveryAddress address) {
        Customer tempCustomer;

        String cryptedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(cryptedPassword);
        customerDAO.create(customer);

        tempCustomer = customerDAO.readByEmail(customer.getEmail());
        address.setCustomerId(tempCustomer.getId());

        addressDAO.create(address);

        Cart cart = new Cart();
        cart.setCustomerId(tempCustomer.getId());
        customer.setCart(cart);
        cartDAO.create(cart);
    }

    @Override
    public Customer getCustomerById(int id) {
        Customer customer = customerDAO.read(id);
        if (customer != null) {
            customer.setDeliveryAddress(addressDAO.read(id));
        }
        return customer;
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerDAO.readByEmail(email);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.readAll();
    }

    @Override
    public void updateCustomerById(int id, Customer updatedCustomer, DeliveryAddress updatedAddress) {
        String cryptedPassword = BCrypt.hashpw(updatedCustomer.getPassword(), BCrypt.gensalt());
        updatedCustomer.setPassword(cryptedPassword);

        customerDAO.update(id, updatedCustomer);
        addressDAO.update(id, updatedAddress);
    }

    @Override
    public void deleteCustomerById(int id) {
        customerDAO.delete(id);
    }
}
