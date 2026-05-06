import { createSlice } from "@reduxjs/toolkit";
import { handleCartAction } from "../actions/cartActions";
import toast  from 'react-hot-toast'

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
        cartAction: (state, action) => {
            const { item, qty } = action.payload;

            const inCart = state.cart.find(i => i.id === item.id);

            if (qty > 0 && inCart && inCart.quantity >= item.stock) {
                toast.error("You exceeded the stock quantity");
                return;
            }

            state.cart = handleCartAction(state.cart, item, qty);
            localStorage.setItem("Cart", JSON.stringify(state.cart));
        }
        /*removeItem: (state, action) => {
            state.cart = removeFromCart(state.cart, action.payload.id);
            localStorage.setItem("Cart", JSON.stringify(state.cart));
            console.log("Deleted! ", state.cart)
        }*/
    } 
})

export const { cartAction } = cartSlice.actions; 
export default cartSlice.reducer;