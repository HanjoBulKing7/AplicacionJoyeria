import { useState, useEffect } from 'react'
import ItemCard from '../shared/ItemCard'
import { useSelector, useDispatch } from 'react-redux'
import { fetchItems } from '../../redux/actions/itemActions'
import { clearItems } from '../../redux/slices/itemsSlice'
import Filter from './Filter'
import { useSearchParams } from 'react-router-dom'
import CustomSpinner from '../shared/CustomSpinner'

const Products = () => {
    const dispatch = useDispatch();
    // Verifica si es state.inventory o state.items (según tu store.js)
    const items = useSelector((state) => state.inventory.items);
    const isLoading = useSelector((state)=> state.inventory.isLoading);
    const [searchParams] = useSearchParams();

    useEffect(() => {
        // Convertimos searchParams a objeto para el thunk
        const params = Object.fromEntries([...searchParams]);
        dispatch(fetchItems(params));
        
        return () => dispatch(clearItems()); // IMPORTANTE: Usa dispatch aquí
    }, [searchParams, dispatch]);

    return (
    
        <div className='bg-slate-950 min-h-screen w-full flex flex-col gap-8 p-4'>
            <Filter />
            { isLoading ?
                <CustomSpinner text={"Loading items"} />      
                :
                <div className='grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6'>
                    {items && items.map((i) => (
                        <ItemCard key={i.id} item={i} />
                    ))}
                </div>
            }
        </div>
    );
}
export default Products;