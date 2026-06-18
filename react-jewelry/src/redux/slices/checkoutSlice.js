import { createSlice } from "@reduxjs/toolkit";
import { fetchAddresses } from "../actions/checkoutActions";

const checkoutSlice = createSlice({
    name: 'checkout',
    initialState: {
        addresses: [],
        selectedAddress: null,
        selectedPaymentMethod: null,
        isLoading: false,
        error: null,
    },
    reducers: {
        cancelCheckout: (state) => {
            state.selectedAddress = null,
            state.selectedPaymentMethod = null
        },   
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
    }
})

export const { cancelCheckout } = checkoutSlice.actions;
export default checkoutSlice.reducer;