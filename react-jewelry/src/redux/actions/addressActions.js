import { createAsyncThunk, isRejectedWithValue } from "@reduxjs/toolkit";
import { api, publicApi } from '../../api/api'
import { __unsafe_useEmotionCache } from "@emotion/react";


export const fetchAddresses = createAsyncThunk(
    'address/fetch',
    async (_,{ rejectWithValue }) => {
        try{

            const res = await api.get('/addresses')
            return res.data;

        }catch(e){
            return rejectWithValue(e?.response?.data?.message || 'Error fetching addresses of the user')
        }
    }
);

export const saveAddress = createAsyncThunk(
    'address/create',
    async (requestBody, { isRejectedWithValue }) => {
        try{
            const savedAddress = await api.post("/addresses", requestBody);
            return savedAddress.data;
        }catch(e){
            return rejectWithValue(e?.response?.data.message || 'Error saving the address');
        }       
    }
)

export const updateAddress = createAsyncThunk(
    'address/update', async ({ addressId , requestBody} , { rejectWithValue }) => {
        try{
            const updatedAddress = await api.put(`/addresses/${addressId}`, requestBody)

            return updatedAddress.data;
 
        }catch(e){
            return rejectWithValue(e?.response?.data?.message || 'Error updating the address')
        }
    }
)


export const deleteAddress = createAsyncThunk(
    'address/delete',
    async (addressId , { rejectWithValue }) => {
        try{
            const resMessage = await api.delete(`/addresses/${addressId}`)
            return resMessage.data
        }catch(e){
            return rejectWithValue(e?.response?.data?.messge || 'Error deleting the address')
        }
    }
)