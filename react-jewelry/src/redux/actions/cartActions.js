import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from "../../api/api";

// While using localstorage
export const handleCartAction = (cart, item, qtyAction) => {

    let found = false;

    const updatedCart = cart.map(i => {
        if (i.productId === item.productId) {
            found = true;
            return { ...i, quantity: i.quantity + qtyAction };
        }
        return i;
    }).filter(i => i.quantity > 0);

    return found ? updatedCart : [...cart, {...item, quantity: 1}]
}

export const checkCartItems = createAsyncThunk(
    'cart/checkCart',
    async (cart, { rejectWithValue}) => {
        try{
            const res = await api.post("/cart/check-availability", cart);
            return res.data;
            
        }catch(e){
            return rejectWithValue(e.response?.message || 'Error checking the cart items');
        }
    }
)

/*
export const removeFromCart = (cart, id) => {
    const updatedCart = cart.filter(i => i.id !== id);
    return updatedCart;
}*/