import { FaTrashCan } from "react-icons/fa6";
import { useDispatch } from 'react-redux'
import Action from './QtyAction'
import { cartAction } from "../../redux/slices/cartSlice";
import toast from "react-hot-toast";

const CartItem = (item) => {

    const dispatch = useDispatch();

    return (
        <>
            {/* Mobile: card vertical */}
            <div className="sm:hidden flex gap-4 text-white border-b border-r border-neutral-600 p-4 rounded-sm">
                <img
                    className="w-20 h-20 object-cover shrink-0"
                    src={`${import.meta.env.VITE_BACKEND_URL}/public/images/${item.image}`}
                    alt={item.name}
                />
                <div className="flex flex-col gap-1 flex-1">
                    <span className="font-medium text-base">{item.name}</span>
                    <div className="flex justify-between text-sm text-neutral-300 mt-auto">
                        <span>Price: <strong className="text-white">${item.price}</strong></span>
                        <span>Qty: <strong className="text-white">{item.quantity}</strong></span>
                        <span>Total: <strong className="text-white">${item.quantity * item.price}</strong></span>
                    </div>
                </div>
            </div>

            {/* Tablet / Desktop: fila de tabla */}
            <div className="hidden sm:grid sm:grid-cols-4 text-white border-b border-r border-neutral-600 items-center py-3 px-2">
                <div className="flex items-center gap-4">
                    <img
                        className="w-16 h-16 lg:w-20 lg:h-20 object-cover flex-shrink-0"
                        src={`${import.meta.env.VITE_BACKEND_URL}/public/images/${item.image}`}
                        alt={item.name}
                    />
                    <span className="text-sm lg:text-base">{item.name}</span>
                </div>
                <div className="text-center text-sm lg:text-base">${item.price}</div>
                <Action item={item} className='text-end text-sm lg:text-base' />
                <div className="text-right pr-4 text-sm lg:text-base">
                    <div className="flex flex-col items-end gap-2">
                        ${item.quantity * item.price}
                        <button onClick={() => {
                            dispatch(cartAction({ item, qty: -item.quantity }));
                            toast.success("Item removed from shopping cart")
                            } }>
                            <FaTrashCan className="text-red-950" size={20}/>
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default CartItem;