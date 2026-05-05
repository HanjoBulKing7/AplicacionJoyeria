import { createSlice } from "@reduxjs/toolkit";
import { addItem, addItemtoCart, fetchCart, getCartItems } from "../actions/cartActions";

const cartSlice = createSlice({
    name: 'cart',
    initialState: {
        cart: [],
        pagination: {
            pageNumber: 0,
            pageSize: 0,
            totalElements: 0,
            totalPages: 0,
            lastPage: 0,
        },
        isLoading: true,
        error: null,
    },
    reducers: {
        cartAction: (state, action)=>{
            state.cart = handleCartAction(state.cart, action.payload.item, action.payload.quantity);
            localStorage.setItem("Cart", JSON.stringify(state.cart));
        }
    } 
})

export default cartSlice.reducer;