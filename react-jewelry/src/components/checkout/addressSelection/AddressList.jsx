import React, { useContext, useEffect } from 'react'
import { BsBuildingAdd } from "react-icons/bs";
import { GiStreetLight } from "react-icons/gi";
import { FaMountainCity } from "react-icons/fa6";
import { GrMap } from "react-icons/gr";
import { FaHouse } from "react-icons/fa6";
import { TbMapPinCode } from "react-icons/tb";
import { CiFlag1 } from "react-icons/ci";
import AddressFormModal from './AddressFormModal';
import { AddressModalProvider, useAddressModal } from '../../hooks/useAddressContext'
import { MdDelete, MdEdit } from "react-icons/md";
import ConditionalForm from './ConditionalForm';
import { useDispatch, useSelector } from 'react-redux';
import truncateText from '../../../utils/truncateText';
import { deleteAddress } from '../../../redux/actions/addressActions';
import { toast } from 'react-hot-toast'
import { useCheckoutContext } from '../../hooks/useCheckoutContext';
import { MdCheckBox } from "react-icons/md";

const AddressList = () => {

  const { setEditingAddress , openAddressFormModal  } = useAddressModal();
  const { addresses } = useSelector((state)=> state.address); 
  const { checkoutAddress, setCheckoutAddress } = useCheckoutContext();

  const dispatch = useDispatch()

  const isLimitReached = addresses.length >= 3; 


  const handleDelete = (addressId) => {
    const dispatchMessage = dispatch(deleteAddress(addressId))

    if(deleteAddress.fulfilled.match(dispatchMessage))
      toast.success(dispatchMessage);
  }

  return (
    <div className='flex flex-col mt-10 w-full max-w-4xl mx-auto px-4'>
      <div className='relative flex justify-end my-10 '>
        <button onClick={()=>openAddressFormModal(null)} className='cursor-pointer relative group'>
          <BsBuildingAdd className={`${isLimitReached ? 'text-gray-600/40' : 'text-white'} text-2xl sm:text-3xl `} />
          <span className='p-2 absolute invisible opacity-0 group-hover:opacity-100 group-hover:visible duration-200 ease-in-out
          transition-transform bottom-[120%] font-light text-white text-sm bg-amber-50/20 rounded-lg
          -translate-x-1/2  ' >{ isLimitReached ? 'You reached the limit of saved addresses': 'Add a new address' }</span>
        </button>
      </div>

      <div className='grid grid-cols-1 sm:grid-cols-2 gap-6'>
        {
          addresses.map((address) => (
            <div key={address.addressId}
              className={`text-white w-full flex flex-col md:p-4 sm:p-7 rounded-2xl transition-all ease-in-out
              border-2 ${ checkoutAddress?.addressId === address.addressId ? 'border-green-400' : 'border-amber-50 hover:border-amber-200' } `}
              
              onClick={()=>setCheckoutAddress(address)}
              >
                <div className='flex flex-row justify-between items-end gap-3'>
                  <div>
                    { checkoutAddress?.addressId === address.addressId && <MdCheckBox className='text-green-400 text-2xl'/>  }
                  </div>

                  <div className='flex flex-row'>
                    <button className='group relative flex cursor-pointer items-center'
                    onClick={()=>{setEditingAddress(address), openAddressFormModal(address)}}>
                      <MdEdit className='text-white text-2xl'/>
                      <span className='absolute z-10 opacity-0 group-hover:opacity-100 transition-transform duration-300 
                      ease-in-out text-white font-light text-sm bg-amber-300/30 bottom-[110%] 
                      rounded-lg p-2'>Edit address</span>
                    </button> 
                    <button className='group relative flex cursor-pointer items-center' onClick={()=>handleDelete(address.addressId)} >
                      <MdDelete className='text-red-600 text-2xl' />
                      <span className='absolute z-10 opacity-0 group-hover:opacity-100 transition-transform duration-300 
                      ease-in-out text-white font-light text-sm bg-amber-300/30 bottom-[110%] 
                      rounded-lg p-2'>Delete address</span>
                    </button>
                  </div>
                </div>

              <div className='flex flex-row justify-between'>
                <div className='flex flex-row items-center gap-2'><GiStreetLight className='text-white text-xl' /><h3 className='font-light text-xl text-amber-200'>Street: </h3></div>
                <p className='text-white'>{truncateText (address.street)}</p>
              </div>
              <div className='flex flex-row justify-between'>
                <div className='flex flex-row items-center gap-2'><FaMountainCity className='text-white text-xl' /><h3 className='font-light text-xl text-amber-200'>City: </h3></div>
                <p className='text-white'>{address.city}</p>
              </div>
              <div className='flex justify-between'>
                <div className='flex flex-row items-center gap-2'><GrMap className='text-xl text-white' /><h3 className='font-light text-xl text-amber-200'>State: </h3></div>
                <p className='text-white'>{address.state}</p>
              </div>
              <div className='flex flex-row justify-between'>
                <div className='flex flex-row items-center gap-2'><FaHouse className='text-xl text-white' /><h3 className='font-light text-xl text-amber-200'>Neighborhood: </h3></div>
                <p className='text-white'>{address.neighborhood}</p>
              </div>
              <div className='flex flex-row justify-between'>
                <div className='flex flex-row items-center gap-2'><TbMapPinCode className='text-white text-xl' /><h3 className='font-light text-xl text-amber-200'>Zipcode: </h3></div>
                <p className='text-white'>{address.zipCode}</p>
              </div>
              <div className='flex flex-row justify-center'>
                <p className='text-white text-xl'>{address.country}</p>
              </div>
            </div>
          ))
        }
      </div>

      <AddressFormModal >
        <ConditionalForm  />
      </AddressFormModal>
    </div>
  )
}

export default AddressList