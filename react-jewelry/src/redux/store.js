import { configureStore } from '@reduxjs/toolkit'
import itemsReducer from './slices/itemsSlice'
import categoryReducer from './slices/categorySlice'

export const store = configureStore({
    reducer: {
        inventory: itemsReducer,
        categories: categoryReducer,
    }

});

export default store;