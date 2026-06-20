import React from 'react'
import { BsBuildingAdd } from "react-icons/bs";
import { GiStreetLight } from "react-icons/gi";
import { FaMountainCity } from "react-icons/fa6";
import { GrMap } from "react-icons/gr";
import { FaHouse } from "react-icons/fa6";
import { TbMapPinCode } from "react-icons/tb";
import { CiFlag1 } from "react-icons/ci";
import AddressFormModal from './AddressFormModal';
import { AddressModalProvider, useAddressModal } from '../../hooks/useAddressContext'

const AddressList = ({ addresses }) => {
  const { setEditingAddress , openAddressFormModal } = useAddressModal();

  return (
    <div className='flex flex-col mt-10 w-full max-w-4xl mx-auto px-4'>
      <div className='relative flex justify-end mb-4'>
        <button onClick={()=>openAddressFormModal(null)}>
          <BsBuildingAdd className='text-white text-2xl sm:text-3xl' />
        </button>
      </div>

      <div className='grid grid-cols-1 sm:grid-cols-2 gap-6'>
        {
          addresses.map((address) => (
            <div key={address.addressId}
              className='text-white w-full flex flex-col hover:cursor-pointer p-6 sm:p-10
              border-2 border-amber-50 rounded-2xl hover:border-amber-200 transition-all ease-in-out'
              onClick={()=>{setEditingAddress(address), openAddressFormModal(addresscls
                
              )}}
              >
              <div className='flex flex-row justify-between'>
                <div className='flex flex-row items-center gap-2'><GiStreetLight className='text-white text-xl' /><h3 className='font-light text-xl text-amber-200'>Street: </h3></div>
                <p className='text-white'>{address.street}</p>
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

      <AddressFormModal />
    </div>
  )
}

export default AddressList