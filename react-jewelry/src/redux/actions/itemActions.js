import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from '../../api/api'

// Thunk para obtener Items (Soporta búsqueda, paginación y filtros)
export const fetchItems = createAsyncThunk(
    'items/fetchItems',
    async (params, { rejectWithValue }) => {
        try {
            const endpoint = params?.keyword 
            ? "/public/items/search"
            : params?.categoryId ? `/public/items/category/${params?.categoryId}`
            :"/public/items";

            const res = await api.get(endpoint, {params})
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
            const res = await api.get("/public/categories", { params });
            return res.data;
        } catch (e) {
            return rejectWithValue(e.response?.data?.message || 'Error fetching categories');
        }
    }
);