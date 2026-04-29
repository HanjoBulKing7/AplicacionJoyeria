import { Dialog, DialogBackdrop, DialogPanel, DialogTitle, Transition } from '@headlessui/react'
import { Fragment } from 'react';

const ItemModal = ({ item, open, setOpen }) => {

    const { id, name, description, image, quantity, price, discount, stock } = item;

    return (
            <Dialog open={open} onClose={close} as="div" className='relative z-10 focus:outline-violet-700' >
                <Transition.Child as={Fragment}>
                    <DialogBackdrop className='fixed inset-0 bg-gray-500 opacity-75 transition-opacity' />
                </Transition.Child>

                <div className='fixed inset-0 z-10 w-screen overflow-y-auto'>
                    <div className=' bg-gray-500 opacity-75 flex flex-col min-h-full items-center justify-center p-4'>
                        {/*IMAGE ON THE MODAL */}
                        <DialogPanel 
                            className='relative bg-white  transform flex flex-col items-center overflow-hidden rounded-lg shadow-xl transition-all w-60 max-w-lg md:max-w-155' >
                            { image && 
                                <div className='flex w-50 justify-center'>
                                <img src={`${import.meta.env.VITE_BACKEND_URL}/public/images/${image}`} alt={name} />
                                </div>
                            }
                            <div className=' flex flex-col items-center p-7 gap-5'>
                                <DialogTitle>{name}</DialogTitle>
                                <p>{price}</p>
                                { stock > 0 ? 
                                    <p>Available</p>
                                    :
                                    <p>Not available</p>
                                }
                                <button 
                                className='border justify-center py-2 bg-red-600 border-red-600 p-3 w-15 h-8 rounded-xl text-white text-sm' 
                                onClick={()=>setOpen(false)}
                                >Close</button>
                                </div>
                        </DialogPanel>
                    </div>
                </div>
            </Dialog>
    );
}
export default ItemModal;