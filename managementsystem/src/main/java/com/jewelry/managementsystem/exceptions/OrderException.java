package com.jewelry.managementsystem.exceptions;

import com.jewelry.managementsystem.models.Order;

public class OrderException extends RuntimeException{

    public OrderException(Double amount){
        super(String.format("%.2f mismatch the total of the cart ",  amount));
    }
}
