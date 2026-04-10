import { api } from '../../api/api'

// Items 
export const fetchItems = (queryString) => async(dispatch) =>{

    try{
        dispatch({type: 'IS_LOADING'});

        const {data} = await api.get(`/public/items?${queryString}`)

        dispatch({
            type: 'FETCH_ITEMS',
            payload: data.content,
            pageSize: data.pageSize,
            totaleElements: data.totalElements,
            totalPages: data.totalPages,
            lastPage: data.lastPage,
        });

        dispatch({ type: 'IS_SUCCESS'});
    }catch(e){
        console.log(e);
        dispatch({
            type: 'IS_ERROR',
            payload: e?.response?.message || 'Falied to fetch data',
        })
    }

};

// Categories
export const fetchItemsCategories = async(dispatch) =>{

    try{
        dispatch({ type: 'CATEGORY_LOADER'});
        const { data } = await api.get(`/public/categories`);
        dispatch({
            type: 'FETCH_CATEGORIES',
            payload: data.content,
            pageSize: data.pageSize,
            totaleElements: data.totalElements,
            totalPages: data.totalPages,
            lastPage: data.lastPage,
        });
        dispatch({ type: 'IS_SUCCESS'})
    }catch(e){
        console.log(e);
        dispatch({
            type: 'IS_ERROR',
            payload: e?.response?.data?.message || 'Failed to fetch categories',
        })
    }
}