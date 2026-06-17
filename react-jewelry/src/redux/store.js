import { configureStore } from '@reduxjs/toolkit'
import itemsReducer from './slices/itemsSlice'
import categoryReducer from './slices/categorySlice'
import cartReducer from './slices/cartSlice'
import authReducer from './slices/authSlice'
import checkoutReducer from './slices/checkoutSlice'

export const store = configureStore({
    reducer: {
        inventory: itemsReducer,
        categories: categoryReducer,
        cart: cartReducer,
        auth: authReducer,
        checkout: checkoutReducer,
    }
});

export default store;