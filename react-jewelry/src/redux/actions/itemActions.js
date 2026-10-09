import { createAsyncThunk, createImmutableStateInvariantMiddleware } from "@reduxjs/toolkit";
import { api, publicApi } from '../../api/api'

// Thunk para obtener Items (Soporta búsqueda, paginación y filtros)
export const fetchItems = createAsyncThunk(
    'items/fetchItems',
    async (params, { rejectWithValue }) => {
        try {
            const endpoint = params?.keyword 
            ? "/public/items/search"
            : params?.categoryId ? `/public/items/category/${params?.categoryId}`
            :"/public/items";

            const res = await publicApi.get(endpoint, {params})
            return res.data;
        } catch (e) {
            return rejectWithValue(e.response?.data?.message || 'Error fetching items');
        }
    }
);

// Thunk para obtener Categorías
export const fetchCategories = createAsyncThunk(
    'items/fetchCategories',
    async (params, { rejectWithValue }) => {
        try {
            const res = await publicApi.get("/public/categories", { params });
            return res.data;
        } catch (e) {
            return rejectWithValue(e.response?.data?.message || 'Error fetching categories');
        }
    }
); 

export const updateCategory = createAsyncThunk(
    'items/udpateCategory',
    async ( params, dispatch, { rejectWithValue }) => {
        try{
            const res = await api.put(`/admin/categories/${params.id}`);
                dispatch(fetchCategories())
            return res.data;
        }catch(e){
            return rejectWithValue(e.response?.data?.message || 'Error updating the category');
        }
    }
)