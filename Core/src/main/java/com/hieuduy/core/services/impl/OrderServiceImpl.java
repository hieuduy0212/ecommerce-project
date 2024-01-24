package com.hieuduy.core.services.impl;

import com.hieuduy.core.entities.CartItem;
import com.hieuduy.core.entities.Order;
import com.hieuduy.core.entities.OrderDetail;
import com.hieuduy.core.entities.ShoppingCart;
import com.hieuduy.core.repositories.CartRepository;
import com.hieuduy.core.repositories.CustomerRepository;
import com.hieuduy.core.repositories.OrderRepository;
import com.hieuduy.core.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createOrder(Order order) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ShoppingCart> cartOp = cartRepository.findByCustomerUsername(username);
        if (cartOp.isPresent()) {
            ShoppingCart cart = cartOp.get();
            ArrayList<OrderDetail> orderDetails = new ArrayList<>();
            for (CartItem ci : cart.getCartItems()) {
                orderDetails.add(
                        OrderDetail.builder()
                                .order(order)
                                .product(ci.getProduct())
                                .quantity(ci.getQuantity())
                                .unitPrice((ci.getProduct().getSale() > 0) ? ci.getProduct().getSalePrice() : ci.getProduct().getPrice())
                                .build());
                ci.setShoppingCart(null);
            }
            cart.getCartItems().clear();
            cartRepository.save(cart);
            order.setOrderDetails(orderDetails);
            order.setDate(LocalDateTime.now());
            order.setStatus("wait");
            order.setCustomer(customerRepository.findByUsername(username));
            orderRepository.save(order);
        }
    }
}
