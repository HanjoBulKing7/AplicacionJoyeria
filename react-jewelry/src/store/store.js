import { configureStore } from '@reduxjs/toolkit'


export const store = configureStore({ // Creating the custom items store for Redux
    reducer: {
        items: itemsReducer,
        errors: errorReducer,
    },
    preloadedState: {},
});

export default store;