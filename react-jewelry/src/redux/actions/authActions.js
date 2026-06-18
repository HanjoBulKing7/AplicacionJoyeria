import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from '../../api/api'

export const loginUser = createAsyncThunk(
    'auth/login',
    async ( requestBody, { rejectWithValue }) => {
        try{
            const res = await api.post('/auth/signin', requestBody);
            const { accessToken, refreshToken } = res.data;
            console.log( accessToken, refreshToken);
            
            // ✅ CORRECCIÓN: Agrega las variables como segundo argumento
            if (accessToken) localStorage.setItem('accessToken', accessToken);
            if (refreshToken) localStorage.setItem('refreshToken', refreshToken);
            
            return res.data;
        }catch(e){
            return rejectWithValue(e?.response?.data?.message || "Error logging user");
        }
    }
);

export const logoutUser = createAsyncThunk(
    'auth/logout',
    async ( requestBody, { rejectWithValue} ) => {

        try{
            const res = await api.post('/auth/signout', requestBody);
            console.log("Refresh token: ", requestBody)
            return res.data.message;
        }catch(e){
            return rejectWithValue(e?.response?.message || 'Error logging out!');
        }
    }

)

export const signUpUser = createAsyncThunk(
    'auth/signup',
    async( requestBody, { rejectWithValue }) => {

        try{
            const res = await api.post('/auth/signup', requestBody);

            return res.data.message;
        }catch(e){
            return rejectWithValue(e?.response?.message || 'Error signing up!');
        }
    }
)