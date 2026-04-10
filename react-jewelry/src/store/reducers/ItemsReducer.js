const initialState = {

    items: [],
    categories: [],
    pagination: {},
};


export const itemsReducer = (state = initialState, action ) => {

    switch(action.type){
        case 'FETCH_ITEMS':
            return{
                ...state,
                items: [...action.content],
                pagination: {
                    ...state.pagination,
                    pageNumber: action.pageNumber,
                    pageSize: action.pageSize,
                    totalElements: action.totalElements,
                    totalPages: action.totalPages,
                    lastPage: action.lastPage,
                },

            };

        case 'FETCH_CATEGORIES':
            return{
                ...state,
                categories: [...action.payload],
            }
        
        default: 
        return state;
    }

}