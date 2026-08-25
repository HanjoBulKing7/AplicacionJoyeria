import React from 'react'
import { Description, Dialog, DialogBackdrop, DialogPanel, DialogTitle, TransitionChild } from '@headlessui/react';

const AdminLayout = () => {

    const [ sideBarOpen, setSideBarOpen ] = useState(false)

  return (
    <div>
        <Dialog
            open={sideBarOpen} onClose={()=>setSideBarOpen(false)} className="relative z-50 xl:hidden"
        >
            <DialogBackdrop transition className="fixed inset-0 bg-gray-900/80 transition-opacity duration-300 ease-linear data-closed:opacity-30" />
            <div className='fixed inset-0 flex'>
                <DialogPanel className="relative mr-16 flex  max-w-xs flex-1 transform transition duration-300 ease-in-out data-closed:-translate-x-full"
                >
                    <TransitionChild>
                        <div>
                            
                        </div>
                    </TransitionChild>
                </DialogPanel>
            </div>
        </Dialog>
    </div>
  )
}

export default AdminLayout