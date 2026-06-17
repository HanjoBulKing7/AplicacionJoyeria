import { createAsyncThunk } from "@reduxjs/toolkit";
import { publicApi } from '../../api/api'


export const fetchAddresses = createAsyncThunk(
    'checkout/fetchAddresses',
    async ({ rejectWithValue }) => {
        try{

            const res = publicApi.get('/addresses')
            return res.data;

        }catch(e){
            return rejectWithValue(e?.response?.data?.message || 'Error fetching addresses of the user')
        }
    }
);