import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchAddresses } from '../../../redux/actions/addressActions';
import { BounceLoader } from 'react-spinners'
import AddressList from './AddressList';
import { AddressModalProvider, useAddressModal } from '../../hooks/useAddressContext'

const AddressContent = () => {
  const dispatch = useDispatch();

  const { isLoading, addresses } = useSelector((state) => state.address);
  const { toggleAddressFormModal } = useAddressModal();

  useEffect(() => {
    dispatch(fetchAddresses());
  }, [dispatch]);

  return (
    <div className={`flex flex-col items-center justify-center mt-10 sm:mt-15 px-4 ${isLoading ? 'modo-skeleton' : ''}`}>
      <h2 className='text-white text-3xl sm:text-4xl lg:text-5xl tracking-wide font-montserrat font-light text-center'>
        Select or add an address
      </h2>
      {
        isLoading ? (
          <BounceLoader />
        )
        : (
          (addresses.length === 0 || !addresses)
            ? (
              <button onClick={toggleAddressFormModal}>
                Add a new address
              </button>
            )
            : <AddressList addresses={addresses} />
        )
      }
    </div>
  )
}

const Address = () => (
  <AddressModalProvider>
    <AddressContent />
  </AddressModalProvider>
)

export default Address