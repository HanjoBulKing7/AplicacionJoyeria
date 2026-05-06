import { Dialog, DialogBackdrop, DialogPanel, DialogTitle, Transition } from '@headlessui/react'
import { Fragment } from 'react';
import { useDispatch } from 'react-redux';
import { cartAction } from '../../redux/slices/cartSlice';
import { MdShoppingCart } from "react-icons/md";
import toast from 'react-hot-toast';

const ItemModal = ({ item, open, setOpen }) => {

    const { id, name, description, image, quantity, price, discount, stock } = item;
    
    const dispatch = useDispatch();

    const addItem = (item) => {
        dispatch(cartAction({item: item, qty: 1}));
    };

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
                                onClick={()=>{
                                    addItem(item) 
                                    toast.success("Item added succesfully")
                                }}
                                className='bg-blue-800 border flex flex-row border-yellow-300 rounded-lg p-3 h-fit text-white gap-3'>
                                    <MdShoppingCart size={20}/>Add to cart
                                </button>
                                <button 
                                className='border justify-center bg-red-600 border-red-600 w-15 h-8 rounded-xl text-white text-sm' 
                                onClick={()=>setOpen(false)}>
                                    Close
                                </button>
                            </div>
                        </DialogPanel>
                    </div>
                </div>
            </Dialog>
    );
}
export default ItemModal;