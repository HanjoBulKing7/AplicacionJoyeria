import { createSlice } from "@reduxjs/toolkit";
import { deleteAddress, fetchAddresses, saveAddress } from "../actions/addressActions";
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
            .addCase(saveAddress.pending, (state , action)=>{
                state.isLoading = true;
            })
            .addCase(saveAddress.fulfilled, (state, action)=>{

                const savedAddress = action.payload;

                state.addresses.push(savedAddress);
                state.isLoading = false;
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
            .addCase(deleteAddress.pending, (state)=>{
                state.isLoading = true;
            })
            .addCase(deleteAddress.fulfilled, (state, action)=>{
                state.isLoading = false;
                const deletedId = action.meta.arg;

                if (deletedId > 0) {
                const index = state.addresses.findIndex(address => address.addressId === deletedId);
                if (index !== -1) 
                    state.addresses.splice(index, 1);
                }
            })
    }
})

export default addressSlice.reducer;