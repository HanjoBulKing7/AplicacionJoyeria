import { createSlice } from '@reduxjs/toolkit'
import { fetchCategories } from '../actions/itemActions'

const categorySlice = createSlice({
    name: 'categories',
    initialState: {
        categories: [],
        pagination: {},
        isLoading: false,
        error: null
    },
    reducers: {
        clearCategories: (state)=>{
            
            state.categories = [];
            state.pagination = {};
            state.isLoading = false;
            state.error = null;
        },
    },
    extraReducers: (builder) =>{
        builder
            .addCase(fetchCategories.pending, (state)=>{
                state.isLoading = true;
            })
            .addCase(fetchCategories.fulfilled, (state,action)=>{
                state.isLoading = false;
                state.categories = action.payload.content;
                state.pagination = {
                    pageNumber: action.payload.pageNumber,
                    pageSize: action.payload.pageSize,
                    totalElements: action.payload.totalElements,
                    totalPages: action.payload.totalPages,
                    isLastPage: action.payload.lastPage
                }
            })
    }
})


export const { clearCategories } = categorySlice.actions;
export default categorySlice.reducer;