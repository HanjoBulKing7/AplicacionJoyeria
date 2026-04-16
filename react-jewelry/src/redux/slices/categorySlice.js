import { createSlice } from '@reduxjs/toolkit'
import { fetchCategories } from '../actions/itemActions'

const categorySlice = createSlice({
    name: 'categories',
    initialState: {
        categories: [],
        isLoading: false,
        error: null
    },
    rerducers: {
        clearCategories: (state)=>{
            state.categories = [];
            state.isLoading = false;
            state.error = null;
        }
    },
    extraReducers: (builder) =>{
        builder
            .addCase(fetchCategories.pending, (state)=>{
                state.isLoading = true;
            })
            .addCase(fetchCategories.fulfilled, (state,action)=>{
                state.isLoading = false;
                state.categories = action.payload.content;
            })
    }
})


export const { clearCategories } = categorySlice.actions;
export default categorySlice.reducer;