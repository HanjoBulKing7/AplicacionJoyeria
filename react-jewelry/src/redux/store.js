import { configureStore } from '@reduxjs/toolkit'
import itemsReducer from './slices/itemsSlice'
import categoryReducer from './slices/categorySlice'
import cartReducer from './slices/cartSlice'

export const store = configureStore({
    reducer: {
        inventory: itemsReducer,
        categories: categoryReducer,
        cart: cartReducer,
    }

});

export default store;