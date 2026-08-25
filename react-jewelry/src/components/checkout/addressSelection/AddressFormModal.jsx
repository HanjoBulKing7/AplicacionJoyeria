import React, { Fragment } from 'react'
import { Dialog, Transition } from '@headlessui/react'
import { AddressModalProvider, useAddressModal } from '../../hooks/useAddressContext'
import { RxCrossCircled } from "react-icons/rx";


const AddressFormModal = ({ children }) => {
  const { openModal, closeAddressFormModal , editingAddress } = useAddressModal();

  return (
    <Transition show={openModal} as={Fragment}>
      <Dialog as='div' className='relative z-20' onClose={() => closeAddressFormModal()}>
        <Transition.Child
          as={Fragment}
          enter='ease-out duration-200'
          enterFrom='opacity-0'
          enterTo='opacity-100'
          leave='ease-in duration-200'
          leaveFrom='opacity-100'
          leaveTo='opacity-0'
        >
          <div className='fixed inset-0 bg-white/40 backdrop-blur-lg' />
        </Transition.Child>

        <div className='fixed inset-0 flex items-center justify-center p-4'>
          <Transition.Child
            as={Fragment}
            enter='ease-out duration-200'
            enterFrom='opacity-0 scale-95'
            enterTo='opacity-100 scale-100'
            leave='ease-in duration-200'
            leaveFrom='opacity-100 scale-100'
            leaveTo='opacity-0 scale-95'
          >
            <Dialog.Panel className='w-full max-w-md sm:max-w-lg bg-zinc-900 rounded-2xl p-6 sm:p-8'>
              <Dialog.Title as="h3" className="flex justify-between text-lg font-semibold leading-6 text-gray-900 mb-1">
                <p className='text-4xl text-white tracking-wider '>{ editingAddress?.addressId ? 'Update address' : 'Add a new address'}</p>
                <button onClick={()=>closeAddressFormModal()}
                  className='group relative cursor-pointer items-center justify-end'
                  >
                  <RxCrossCircled className='text-red-500 text-4xl'/>
                  <span 
                  className='z-10 bg-amber-100/20 rounded-md invisible opacity-0 group-hover:visible group-hover:opacity-100
                   text-white text-sm font-light p-2 transition-transform ease-in-out duration-200
                   -translate-x-1/2 left-[110%]'  >
                    Close form
                  </span>
                </button>
              </Dialog.Title>
              {children}
            </Dialog.Panel>
          </Transition.Child>
        </div>
      </Dialog>
    </Transition>
  )
}

export default AddressFormModal