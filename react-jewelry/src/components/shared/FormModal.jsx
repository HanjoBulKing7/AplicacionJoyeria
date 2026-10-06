import { Dialog, DialogBackdrop, DialogPanel, DialogTitle } from '@headlessui/react'
import React from 'react'

const FormModal = ({open, setOpenModal, children, title = ""}) => {
  return (

    <Dialog open={open} onClose={()=>setOpenModal(null)} className='relative z-10' >
        <DialogBackdrop className="fixed inset-0 bg-gray/75 transition-opacity duration-400 ease-in-out" />

        <div>
            <DialogPanel>
                <div>
                    <DialogTitle>{title}</DialogTitle>
                </div>
                <div>
                    {children}
                </div>
            </DialogPanel>  
        </div>
    </Dialog>
  )
}

export default FormModal