import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from '../../api/api'

export const loginUser = createAsyncThunk(
    'auth/login',
    async ( requestBody, { rejectWithValue }) => {
        try{
            const res = await api.post('/auth/signin', requestBody);
            return res.data;

        }catch(e){
            return rejectWithValue(e?.response?.data?.message || "Error logging user")
        }
    }
);