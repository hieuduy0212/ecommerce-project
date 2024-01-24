package com.hieuduy.customer.controller;

import com.hieuduy.core.entities.Order;
import com.hieuduy.core.entities.ShoppingCart;
import com.hieuduy.core.services.CartService;
import com.hieuduy.core.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/order")
    public String goOrderPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("title", "Checkout");
        ShoppingCart cart = cartService.getShppingCartByUsername(userDetails.getUsername());
        model.addAttribute("cart", cart);
        model.addAttribute("order", new Order());
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@ModelAttribute("order") Order order){
        orderService.createOrder(order);
        return "redirect:/cart/show?order";

    }
}
