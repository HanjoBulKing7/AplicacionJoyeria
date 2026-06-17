import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchAddresses } from '../../../redux/actions/checkoutActions';

const Address = () => {

    const dispatch = useDispatch();

    useEffect(()=>{
        dispatch(fetchAddresses());
    },[])

    const { isLoading ,addresses } = useSelector((state) => state.checkout);
    
  return (
    
    <div className={`flex items-center justify-center mt-15 ${ isLoading ? 'modo-skeleton' : ''}`} >
        <h2 className='text-black text-5xl tracking-wide'>Select or add an address</h2>
        <address></address>
    </div>
  )
}

export default Address