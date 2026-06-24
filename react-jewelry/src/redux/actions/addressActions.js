import { createAsyncThunk } from "@reduxjs/toolkit";
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