import { createSlice } from '@reduxjs/toolkit'
import { fetchItems, fetchCategories } from '../actions/itemActions'

const itemSlice = createSlice({
    name: 'items',
    initialState: {
        items: [],
        categories: [],
        pagination: {},
        isLoading: false,
        categoryLoading: false, 
        error: null
    },
    extraReducers: (builder) => { // Builder pattern
        //  Managing the status as the state changes by itself using builder
        builder
            // Handle items
            .addCase(fetchItems.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(fetchItems.fulfilled, (state, action) => {
                state.isLoading = false;
                state.items = action.payload.content; // If completed set contend 
                state.pagination = action.payload; // If completed set pagination
            })
            
            // Handle categories
            .addCase(fetchCategories.pending, (state) => {
                state.categoryLoading = true;
            })
            .addCase(fetchCategories.fulfilled, (state, action) => {
                state.categoryLoading = false;
                state.categories = action.payload.content;
            });
    }
});

export const { clearItems } = itemSlice.actions;
export default itemSlice.reducer;