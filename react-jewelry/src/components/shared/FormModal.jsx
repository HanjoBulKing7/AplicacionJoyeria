import { Dialog, DialogBackdrop, DialogPanel, DialogTitle } from '@headlessui/react'
import { dialogTitleClasses } from '@mui/material/DialogTitle';
import React from 'react'
import { ImCross } from "react-icons/im";

const FormModal = ({ open, setOpen, children, title = "", selected  }) => {

  return (

    <Dialog open={open} onClose={()=>setOpen(false)} className='relative z-10' >
        <DialogBackdrop className="fixed inset-0 bg-gray-400/75 " />
        <div className='fixed flex flex-col items-center justify-center h-full right-0 inset-y-0'>
            <DialogPanel className='h-full bg-white left-0 w-screen max-w-200 transform
            transition duration-500 ease-linear data-closed:translate-x-full'>
                <div>
                    <h1>{title+=selected?.name}</h1>
                    <button onClick={()=>setOpen(false)}>
                    <ImCross size={30} />
                    </button>
                </div>
                <div>
                </div>
            </DialogPanel>
        </div>

    </Dialog>
  )
}

export default FormModal