import React, { useState } from 'react'
import { Description, Dialog, DialogBackdrop, DialogPanel, DialogTitle, TransitionChild } from '@headlessui/react';
import { ImCross } from "react-icons/im";
import Sidebar from './Sidebar';
import { Outlet } from 'react-router-dom';
import { FaBars } from 'react-icons/fa';

const AdminLayout = () => {

    const [ sideBarOpen, setSideBarOpen ] = useState(false)

  return (
    <div>

        {/*MODAL FOR MOBILE */}
        <Dialog
            open={sideBarOpen} onClose={()=>setSideBarOpen(false)} className="relative z-50 xl:hidden"
        >
            <DialogBackdrop transition className="fixed inset-0 bg-gray-900/80 transition-opacity duration-300 ease-linear data-closed:opacity-30" />
            <div className='fixed inset-0 flex'>
                <DialogPanel className="relative mr-16 flex  max-w-xs flex-1 transform transition duration-300 ease-in-out data-closed:-translate-x-full"
                >
                    <TransitionChild>
                        <div className="absolute left-full top-0 flex w-16 justify-center pt-5 duration-300 ease-in-out">
                            <button tpye='button' onClick={()=>setSideBarOpen(false)}>
                                <span className='sr-only'>Close bar</span>
                                <ImCross className="text-red text-5xl"/>
                            </button>
                        </div>
                    </TransitionChild>
                    <Sidebar />
                </DialogPanel>
            </div>
        </Dialog>

    
        {/*MODAL FOR DESKTOP */}
        <div className='hidden xl:fixed xl:inset-y-0 xl:z-50 xl:flex xl:flex-col xl:w-72 xl:mt-20 xl:h-full'>
            <Sidebar />
        </div>

        {/*OUTLET OR MAIN CONTENT INSIDE THE LAYOUT */}
        <div className='xl:pl-80'>
            <button type="button" className="xl:hidden p-4" onClick={()=>setSideBarOpen(true)} >
                <span className='sr-only'>Open Admin Bar</span>
                <FaBars className="text-black text-2xl tracking-tight"/>
            </button>
            <main className='w-full sm:p-5 xl:p-9'>
                <Outlet />
            </main>
        </div>

    </div>
  )
}

export default AdminLayout