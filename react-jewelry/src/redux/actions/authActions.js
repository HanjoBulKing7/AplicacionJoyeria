import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from '../../api/api'

export const loginUser = createAsyncThunk(
    'auth/login',
    async ( requestBody, { rejectWithValue }) => {
        try{
            const res = await api.post('/auth/signin', requestBody);
            console.log( res.data );
        
            if(!!res) 
                localStorage.setItem('userInfo', JSON.stringify(res.data));
            
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

export const createStripeSecret = createAsyncThunk(
    'auth/clientSecret',
    async( requestBody , {rejectWithValue}) => {
        try{
            const res = await api.post("/orders/validate", requestBody );

            return res.data;

        }catch(e){
            return rejectWithValue(e?.response?.message || 'Error getting the client secret');
        }
    }
)

export const confirmPayment = createAsyncThunk(
    'auth/confirmPayment',
    async (sendData, { rejectWithValue }) => {
        try {
            const res = await api.post("/orders/confirm", sendData);
            return res.data;
        } catch(e) {
            return rejectWithValue(e?.response?.data?.message || 'Error confirming the payment');
        }
    }
)