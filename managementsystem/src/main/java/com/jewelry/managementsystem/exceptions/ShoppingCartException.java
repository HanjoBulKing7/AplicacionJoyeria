package com.jewelry.managementsystem.exceptions;

import lombok.Data;

@Data
public class ShoppingCartException extends RuntimeException{

    private String itemName;
    private String reason;
    private int quantity;
    private Long itemId;

    public ShoppingCartException(Long itemId){
        super("Item with id: "+itemId+" does not exists on the cart");
        this.itemName = itemId.toString();
    }

    public ShoppingCartException(String itemName, String reason){
        super(itemName + reason );
        this.itemName = itemName;
        this.reason = reason;
    }

    public ShoppingCartException(String itemName, int quantity){
        super("Make an order of "+itemName+" less or equal than"+quantity);
        this.itemName = itemName;
        this.quantity = quantity;
    }

}
