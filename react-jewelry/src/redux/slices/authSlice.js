import { createSlice } from "@reduxjs/toolkit"
import { loginUser } from '../actions/authActions'

const authSlice = createSlice({
    name: 'auth',
        initialState: {
        username: '',
        roles: [],
        authLoading: false,
        error: null,
    },
    reducers: {
        logout: (state) =>{
            state.username ='',
            state.roles = []
            localStorage.removeItem("auth");
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase( loginUser.pending, (state)=>{
                state.authLoading = true;
            })
            .addCase( loginUser.fulfilled, (state, action)=>{
                state.authLoading = false;
                state.username = action.payload.username;
                state.roles = action.payload.roles;
                localStorage.setItem("auth", JSON.stringify(state.auth));
            })

            .addCase( registerUser.pending, (state)=>{
                state.authLoading = true;
            })
            addCase( registerUser.pending, (state, action)=>{
                state.authLoading = false;
            })
    }

})

export const { logout } = authSlice.actions;
export default authSlice.reducer;