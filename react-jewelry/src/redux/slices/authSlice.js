import { createSlice } from "@reduxjs/toolkit"
import { loginUser, signUpUser } from '../actions/authActions'
import { useNavigate } from "react-router-dom"


const initialState = {
    username: '',
    roles: [],
    accessToken: '',
    refreshToken: '',
    clientSecret: null,
    isLoading: false,
    message: '',
    error: '',
}

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        logout: (state) => {
            state.username = '';
            state.roles = [];
            state.accessToken = '';
            state.refreshToken = '';
            state.error = null;
            state.isLoading = false;
            state.message = 'Logged out succesfully';
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(loginUser.pending,  (state)=>{
                state.isLoading = true;
            })
            .addCase(loginUser.fulfilled, (state, action)=>{
                state.isLoading = false;
                state.username = action.payload.username;
                state.roles = action.payload.roles;
                state.accessToken = action.payload.accessToken;
                state.refreshToken = action.payload.refreshToken;
                state.message = 'Succesfully logged in'
            })
            .addCase(loginUser.rejected, (state, action) => {
                state.isLoading = false;
                state.error = action.payload; // Aquí guardamos el mensaje de error para mostrarlo en UI
            })
            .addCase(signUpUser.pending, (state)=>{
                state.isLoading = true;
            })
            .addCase(signUpUser.fulfilled, (state, action)=>{
                state.isLoading = false;
                state.message = action.payload.message;
            })
            .addCase()
    }
})

export const { logout } = authSlice.actions;
export default authSlice.reducer;