import { createSlice } from "@reduxjs/toolkit"
import { loginUser } from '../actions/authActions'
import { useNavigate } from "react-router-dom"


const initialState = {
    username: '',
    roles: [],
    accessToken: '',
    refreshToken: '',
    isLoading: false,
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
            })
    }

})

export const { logout } = authSlice.actions;
export default authSlice.reducer;