import { Dialog, DialogBackdrop } from '@headlessui/react'
import React from 'react'

const DeletionModal = ({ open , setOpen , itemName , customDelete }) => {
  return (
    <Dialog open={open} onClose={()=>setOpen(false)} className='relative z-10' >
        <DialogBackdrop className='fixed inset-0 bg-gray-400/75' />

        <div className='fixed flex flex-col items-center justify-center h-fit'>
            <DialogPanel>
              <div><span className='text-2xl' >Deletion Confirmation</span></div>
              <div> 
                <h2>{`Are you sure you want to delete ${itemName}?`}</h2>
              </div>
            </DialogPanel>
        </div>
    </Dialog>
  )
}

export default DeletionModal