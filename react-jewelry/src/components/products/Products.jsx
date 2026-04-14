import { useState, useEffect } from 'react'
import ItemCard from '../shared/ItemCard'
import { useSelector, useDispatch } from 'react-redux'
import { fetchItems } from '../../redux/actions/itemActions'
import { clearItems } from '../../redux/slices/itemsSlice'
import Filter from './Filter'

const Products = () => {

    const dispatch = useDispatch();
    const items = useSelector((state)=> state.inventory.items);

    useEffect(()=>{
        dispatch(fetchItems());
        return ()=> dispatch(clearItems());
    }, [dispatch])

    return(
        <div className='bg-slate-950 w-screen h-screen flex flex-col justify-between'>
            <Filter />
            { items.map(( i )=>( <ItemCard key={i.id} item={i} />   ))}
        </div>
    );
}
export default Products;