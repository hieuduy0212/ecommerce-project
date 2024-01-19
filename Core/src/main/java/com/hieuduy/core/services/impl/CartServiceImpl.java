package com.hieuduy.core.services.impl;

import com.hieuduy.core.entities.CartItem;
import com.hieuduy.core.entities.Customer;
import com.hieuduy.core.entities.Product;
import com.hieuduy.core.entities.ShoppingCart;
import com.hieuduy.core.repositories.CartItemRepository;
import com.hieuduy.core.repositories.CartRepository;
import com.hieuduy.core.repositories.CustomerRepository;
import com.hieuduy.core.repositories.ProductRepository;
import com.hieuduy.core.services.CartService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void addToCart(long productId, int quantity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerRepository.findByUsername(userDetails.getUsername());
        Product product = productRepository.findById(productId).get();
        Optional<ShoppingCart> cartOp = cartRepository.findByCustomerId(customer.getId());
        ShoppingCart cart;

        // get cart
        if (cartOp.isPresent()) {
            cart = cartOp.get();
        } else {
            cart = ShoppingCart.builder().customer(customer).cartItems(new ArrayList<>()).build();
            cart = cartRepository.save(cart);
        }

        CartItem cartItem = checkExistsCartItemInCart(cart, product);
        if (cartItem == null) {
            cartItem = CartItem.builder().quantity(quantity).product(product).shoppingCart(cart).build();
            List<CartItem> cartItems = cart.getCartItems();
            cartItems.add(cartItem);
            cart.setCartItems(cartItems);
            cartRepository.save(cart);
        } else {
            int newQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(newQuantity);
            List<CartItem> cartItems = cart.getCartItems();
            cartItems.add(cartItem);
            cart.setCartItems(cartItems);
            cartRepository.save(cart);
        }
    }

    private CartItem checkExistsCartItemInCart(ShoppingCart cart, Product product) {
        if (cart.getCartItems().isEmpty()) return null;
        for (CartItem ci : cart.getCartItems()) {
            if (Objects.equals(ci.getProduct().getId(), product.getId())) {
                return ci;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getSizeCartAndTotalPrice() {
        Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        if (principal.equals("anonymousUser")) {
            map.put("sizeCart", 0);
            map.put("totalPrice", 0f);
        } else {
            UserDetails userDetails = (UserDetails)principal;
            Customer customer = customerRepository.findByUsername(userDetails.getUsername());
            Optional<ShoppingCart> cartOp = cartRepository.findByCustomerId(customer.getId());
            if (cartOp.isPresent()) {
                ShoppingCart cart = cartOp.get();
                map.put("sizeCart", cart.getCartItems().size());
                map.put("totalPrice", cart.getTotalPrice());
            } else {
                map.put("sizeCart", 0);
                map.put("totalPrice", 0f);
            }
        }
        return map;
    }

    @Override
    public ShoppingCart getShppingCartByUsername(String username) {
        Optional<ShoppingCart> cartOp = cartRepository.findByCustomerUsername(username);
        return cartOp.orElse(null);
    }

    @Override
    public CartItem update(Long cartItemId, int quantity) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!principal.equals("anonymousUser")){
            cartItemRepository.update(cartItemId, quantity);
            CartItem ci = cartItemRepository.findCartItemById(cartItemId).get();
            return ci;
        }
        return null;
    }
}
