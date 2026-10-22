import { Dialog, DialogBackdrop, DialogPanel } from '@headlessui/react'
import React from 'react'
import { MdDelete } from "react-icons/md";

const DeletionModal = ({ open , setOpen , itemName , customDelete }) => {
  return (
    <Dialog open={open} onClose={()=>setOpen(false)} className='relative z-10' >
        <DialogBackdrop className='fixed inset-0 bg-gray-400/75' />

        <div className='fixed flex flex-col items-center justify-center inset-0'>
            <DialogPanel>
              <div className='flex flex-col gap-5 hfit w-fit bg-amber-50 p-5 rounded-lg '>
                <div className='border-b-2 border-black/40 flex justify-center pb-2'><span className='text-2xl' >Deletion Confirmation</span></div>
                <div className=' flex flex-col gap-10'> 
                  <h2>{`Are you sure you want to delete ${itemName}?`}</h2>
                  <div className='flex flex-row justify-between items-center'>
                    <button className='bg-blue-600 text-white text-lg p-1 rounded-lg' 
                      onClick={()=>setOpen(false)} >Cancel
                    </button>
                    <button className='flex flex-row items-center bg-red-600 text-white text-lg p-1 rounded-lg gap-2' 
                     onClick={()=>customDelete()} >
                      <MdDelete />Delete
                    </button>
                  </div>
                </div>
              </div>
            </DialogPanel>
        </div>
    </Dialog>
  )
}

export default DeletionModal