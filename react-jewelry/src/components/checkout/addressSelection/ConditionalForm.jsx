import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form';
import { useAddressModal } from '../../hooks/useAddressContext';
import { useDispatch } from 'react-redux'
import { updateAddress } from '../../../redux/actions/checkoutActions';

const ConditionalForm = () => {

  const { handleSubmit, register, setValue , formState: { errors  } } = useForm({mode: "onTouched"});
  const { editingAddress , setOpenModal } = useAddressModal();
  const dispatch = useDispatch();

  useEffect(()=>{
    if(editingAddress?.addressId){
      setValue("street", editingAddress?.street);
      setValue("neighborhood", editingAddress?.neighborhood); 
      setValue("city", editingAddress?.city);
      setValue("zipCode", editingAddress?.zipCode)
      setValue("state", editingAddress?.state)
      setValue("country", editingAddress?.country)
    }
  },[editingAddress])

  const onSaveAddress = async (data) => {

    if(editingAddress?.addressId){
      console.log({...data, addressId: editingAddress.addressId})
      console.log("Upadting address")
      dispatch(updateAddress(editingAddress?.addressId, data));
    }
  };

  return (
    <div className='flex justify-center items-center text-white '>
        <form onSubmit={handleSubmit(onSaveAddress)}
          className='flex flex-col items-center justify-center gap-4'
        >
          <label className='text-2xl font-light'  >Street </label>
          <input type="text" 
          {...register("street", {
            required: { value: true, message: "Street is required" },
            minLength: { value: 4 , message: 'A valid street is at least 4 characters' }
          })  }
          className='border-2 border-white rounded-md text-xl font-thin p-2 focus:bg-amber-50/20'
          />

          <label className='text-2xl font-light'  >Neighborhood: </label>
          <input type="text" 
          { ...register("neighborhood", {            
            required: { value: true, message: "Neighborhood is required" },
            minLength: { value: 4 , message: 'A valid neighborhood is at least 4 characters' }          
          })  } 
            className='border-2 border-white rounded-md text-xl font-thin p-2 focus:bg-amber-50/20'
          />

          <label className='text-2xl font-light'  >City: </label>
          <input type="text" 
            {...register("city", {
            required: { value: true, message: "City is required" },
            minLength: { value: 4 , message: 'A valid city is at least 4 characters' }
            })  }
            className='border-2 border-white rounded-md text-xl font-thin p-2 focus:bg-amber-50/20'
          />

          <label className='text-2xl font-light'  >Zipcode: </label>
          <input type="text" 
            { ...register("zipCode",  {
              required: { value: true, message: "Zipcode is required" },
              minLength: { value: 4 , message: 'A valid zipcode is at least 4 characters' }
            })  }
            className='border-2 border-white rounded-md text-xl font-thin p-2 focus:bg-amber-50/20'
          />

          <label className='text-2xl font-light'>State: </label>
          <input type="text" 
            { ...register("state",  {
              required: { value: true, message: "State is required" },
              minLength: { value: 4 , message: 'A valid state is at least 4 characters' }
            })  }
            className='border-2 border-white rounded-md text-xl font-thin p-2 focus:bg-amber-50/20'
          />

          <label className='text-2xl font-light' >Country: </label>
          <input type="text"
            { ...register("country",  {
              required: { value: true, message: "Country is required" },
              minLength: { value: 4 , message: 'A valid country is at least 4 characters' }
            })  }
            className='border-2 border-white rounded-md text-xl font-thin p-2 focus:bg-amber-50/20'
          />

          <button
            className='text-white h-15 w-auto bg-gray-800/70 px-4 py-2 rounded-lg mt-4'
          >
            Save
          </button>
        </form>
    </div>
  )
}

export default ConditionalForm