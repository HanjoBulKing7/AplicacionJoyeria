import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from '../../api/api'

// Thunk para obtener Items (Soporta búsqueda, paginación y filtros)
export const fetchItems = createAsyncThunk(
    'items/fetchItems',
    async (params, { rejectWithValue }) => {
        try {
            // Llama a @GetMapping( "/public/items" ) o los de búsqueda
            // Si mandas { keyword: 'anillo' }, Axios arma /public/items?keyword=anillo
            const res = await api.get("/public/items", { params });
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
            // Llama a @GetMapping ( "/public/categories" )
            const res = await api.get("/public/categories", { params });
            return res.data;
        } catch (e) {
            return rejectWithValue(e.response?.data?.message || 'Error fetching categories');
        }
    }
);