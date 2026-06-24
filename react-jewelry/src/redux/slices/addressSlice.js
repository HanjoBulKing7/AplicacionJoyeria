import { createSlice } from "@reduxjs/toolkit";
import { fetchAddresses } from "../actions/addressActions";
import { updateAddress } from "../actions/addressActions";

const addressSlice = createSlice({
    name: 'checkout',
    initialState: {
        addresses: [],
        isLoading: false,
        error: null,
    },
    reducers: {
    },
    extraReducers: (builder) => {
        builder 
            .addCase(fetchAddresses.pending, (state)=>{
                state.isLoading = true;
            })
            .addCase(fetchAddresses.fulfilled, (state, action)=>{
                state.isLoading = false;
                state.addresses = action.payload;
            })
            .addCase(updateAddress.pending, ( state )=>{
                state.isLoading = true;
            })
            .addCase(updateAddress.fulfilled, (state , action)=>{
                const updatedAddress = action.payload;

                const updatedIndex = state.addresses.findIndex((address)=> address.addressId === updatedAddress.addressId );

                if( updatedIndex !== -1 )
                    state.addresses[updatedIndex] = updatedAddress;

                state.isLoading = false;
            })
    }
})

export default addressSlice.reducer;