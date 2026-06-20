import React, { Fragment } from 'react'
import { Dialog, Transition } from '@headlessui/react'
import { AddressModalProvider, useAddressModal } from '../../hooks/useAddressContext'

const AddressFormModal = ({ children }) => {
  const { openModal, setOpenModal , editingAddress } = useAddressModal();

  return (
    <Transition show={openModal} as={Fragment}>
      <Dialog as='div' className='relative z-20' onClose={() => setOpenModal(false)}>
        <Transition.Child
          as={Fragment}
          enter='ease-out duration-200'
          enterFrom='opacity-0'
          enterTo='opacity-100'
          leave='ease-in duration-200'
          leaveFrom='opacity-100'
          leaveTo='opacity-0'
        >
          <div className='fixed inset-0 bg-black/40 backdrop-blur-lg' />
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
              <Dialog.Title as="h3" className="text-lg font-semibold leading-6 text-gray-900 mb-1">
                <p className='text-3xl text-white'>{ editingAddress?.addressId ? 'Update address' : 'Add a new address'}</p>
                { console.log(editingAddress?.addressId)}
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