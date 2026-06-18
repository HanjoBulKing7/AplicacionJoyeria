import { createAsyncThunk } from "@reduxjs/toolkit";
import { api, publicApi } from '../../api/api'
import { __unsafe_useEmotionCache } from "@emotion/react";


export const fetchAddresses = createAsyncThunk(
    'checkout',
    async (_,{ rejectWithValue }) => {
        try{

            const res = await api.get('/addresses')
            return res.data;

        }catch(e){
            return rejectWithValue(e?.response?.data?.message || 'Error fetching addresses of the user')
        }
    }
);