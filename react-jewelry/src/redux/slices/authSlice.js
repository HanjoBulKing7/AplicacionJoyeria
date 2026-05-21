import { createSlice } from "@reduxjs/toolkit"
import { loginUser } from '../actions/authActions'
import { useNavigate } from "react-router-dom"


const currentAuth = localStorage.getItem("auth");

const persistedAuth = (currentAuth && currentAuth !== 'undefined') 
    ? JSON.parse(currentAuth) 
    : null;


const initialState = {
    username: persistedAuth?.username ?? '',
    roles: persistedAuth?.roles ?? [],
    token: persistedAuth?.token ?? '',
    authLoading: false,
    error:  null,
}

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        logout: (state) =>{
            state.username ='',
            state.roles = []
            state.token = '',
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
                state.token = action.payload.token;

                const authData = { 
                        username: action.payload.username, 
                        roles: action.payload.roles,
                        token: action.payload.token,
                };
                    
                console.log("Saving to storage:", authData);
                localStorage.setItem("auth", JSON.stringify(authData));
            })

            // .addCase( registerUser.pending, (state)=>{
            //     state.authLoading = true;
            // })
            // addCase( registerUser.pending, (state, action)=>{
            //     state.authLoading = false;
            // })
    }

})

export const { logout } = authSlice.actions;
export default authSlice.reducer;