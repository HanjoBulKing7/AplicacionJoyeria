import { createSlice } from '@reduxjs/toolkit'
import { fetchItems } from '../actions/itemActions'

const itemSlice = createSlice({
    name: 'items',
    initialState: {
        items: [],
        pagination: {
            pageNumber: 0,
            pageSize: 0,
            totalElements: 0,
            totalPages: 0,
            lastPage: 0,
        },
        isLoading: true,
        error: null
    },
    reducers: {
        clearItems: (state)=>{
            state.items = [];
            state.pagination = {
                pageNumber: 0,
                pageSize: 0,
                totalElements: 0,
                totalPages: 0,
                lastPage: 0,
            };
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
                state.items = action.payload.content; 
                state.pagination = {
                    pageNumber: action.payload.pageNumber,
                    pageSize: action.payload.pageSize,
                    totalElements: action.payload.totalElements,
                    totalPages: action.payload.totalPages,
                    totalElements: action.payload.totalElements,
                }; 
            })
    }
});

export const { clearItems } = itemSlice.actions;
export default itemSlice.reducer;