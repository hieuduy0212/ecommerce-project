package com.hieuduy.customer.controller;

import com.hieuduy.core.entities.CartItem;
import com.hieuduy.core.entities.Customer;
import com.hieuduy.core.services.CartService;
import com.hieuduy.customer.security.CustomerDetails;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class CartRestController {
    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/add/{productId}/{quantity}")
    public void addToCart(@PathVariable long productId, @PathVariable(required = false) int quantity) {
        cartService.addToCart(productId, quantity);
    }

    @GetMapping("/get-size-and-price")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public Map<String, Object> getSizeCartAndTotalPrice(){
        return cartService.getSizeCartAndTotalPrice();
    }

    @PutMapping("/cart/update/{cartItemId}/{quantity}")
    public CartItem doUpdateCart(@PathVariable Long cartItemId, @PathVariable int quantity){
        CartItem ci = cartService.update(cartItemId, quantity);
        return ci;
    }
}
