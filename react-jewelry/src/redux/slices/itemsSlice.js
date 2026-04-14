import { createSlice } from '@reduxjs/toolkit'
import { fetchItems } from '../actions/itemActions'

const itemSlice = createSlice({
    name: 'items',
    initialState: {
        items: [],
        pagination: {},
        isLoading: true,
        error: null
    },
    reducers: {
        clearItems: (state)=>{
            state.items = [];
            state.pagination = {};
            state.isLoading = false;
            state.error = null;
        },
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
    }
});

export const { clearItems } = itemSlice.actions;

export default itemSlice.reducer;