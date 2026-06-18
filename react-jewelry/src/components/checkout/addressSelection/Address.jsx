import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchAddresses } from '../../../redux/actions/checkoutActions';
import { ClipLoader } from 'react-spinners'
import AddressList from './AddressList';

const Address = () => {

    const dispatch = useDispatch();
  
    useEffect(() => {
        dispatch(fetchAddresses());
    }, [dispatch]);


    const { isLoading , addresses } = useSelector((state) => state.checkout);
    
  return (
    
    <div className={`flex items-center justify-center mt-15 ${ isLoading ? 'modo-skeleton' : ''}`} >
        <h2 className='text-black text-5xl tracking-wide'>Select or add an address</h2>
        {
          isLoading ? (
            <ClipLoader />
          ) 
          :
          (
            <AddressList />
          )
        }
    </div>
  )
}

export default Address