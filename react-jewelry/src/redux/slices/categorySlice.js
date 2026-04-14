import { createSlice } from '@reduxjs/toolkit'
import { fetchCategories } from '../actions/itemActions'

const categoriesSlice = createSlice({
    name: 'categories',
    initialState: {
        categories: [],
        isLoading: false,
        error: null
    },
    extraReducers: (builder) =>{
        builder
            .addCase(fetchCategories.pending, (state)=>{
                state.isLoading = true;
            })
            .addCase(fetchCategories, (state,action)=>{
                state.isLoading = false;
                state.categories = action.payload.content;
            })
    }
})