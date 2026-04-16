import { useState, useEffect } from 'react'
import ItemCard from '../shared/ItemCard'
import { useSelector, useDispatch } from 'react-redux'
import { fetchItems } from '../../redux/actions/itemActions'
import { clearItems } from '../../redux/slices/itemsSlice'
import Filter from './Filter'
import { useSearchParams } from 'react-router-dom'

const Products = () => {

    const dispatch = useDispatch();
    const items = useSelector((state)=> state.inventory.items);


    const [searchParams] = useSearchParams();

    useEffect(()=>{
        dispatch(fetchItems(searchParams));
        return ()=> clearItems();
    },[searchParams])

    return(
        <div className='bg-slate-950 w-screen h-screen flex flex-col justify-between'>
            <Filter />
            { items.map(( i )=>( <ItemCard key={i.id} item={i} />   ))}
        </div>
    );
}
export default Products;