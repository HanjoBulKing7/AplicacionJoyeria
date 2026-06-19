import React from 'react'
import { BsBuildingAdd } from "react-icons/bs";

const AddressList = ({ openModal }) => {

  return (
    <div className='mt-10 w-full max-w-4xl mx-auto px-4'> 
      <div className='relative flex'>
        <div className='right-0 absolute'>
          <button onClick={()=>openModal()}>
            <BsBuildingAdd className='text-black text-3xl'/>
          </button>
        </div>
        Your addresses here!
      </div>
    </div>
  )
}

export default AddressList