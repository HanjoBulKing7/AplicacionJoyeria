import { useSelector } from 'react-redux'
import CartItem from './CartItem';
import { formatPrice } from '../../utils/formatPrice';

const Cart = () => {
    const cart = useSelector((state) => state.cart.cart);

    const subtotal = parseInt(cart?.reduce((acc, cur) => acc += (cur.quantity * cur.price), 0));

    return (
        <div className="bg-gray-900 min-h-screen pt-10 px-4 sm:px-8 lg:px-16 space-y-6">
            <h1 className='text-white text-center text-3xl sm:text-4xl tracking-[0.2em] mb-8'>
                Shopping Cart
            </h1>

            {cart.length === 0 ? (
                <div className="flex items-center justify-center h-64">
                    <h2 className='text-white text-xl sm:text-3xl text-center px-4'>
                        Your shopping cart is empty, let's get started!
                    </h2>
                </div>
            ) : (
                <>
                <div className="w-full max-w-5xl mx-auto">
                    <ul className='hidden sm:grid sm:grid-cols-4 text-white text-base sm:text-lg tracking-widest mb-4 px-2 text-neutral-400'>
                        <li>Product</li>
                        <li className="text-center">Price</li>
                        <li className="text-center">Quantity</li>
                        <li className="text-right pr-4">Total</li>
                    </ul>

                    <div className="space-y-4 sm:space-y-2">
                        {cart.map((item) => (
                            <CartItem key={item.id} {...item} />
                        ))}
                    </div>
                </div>
                <div className='flex flex-col md:items-end gap-5 sm:items-center'>
                    <h1 className='text-white md:text-2xl sm:text-lg'>Subtotal:</h1> 
                    <span className='text-white md:text-3xl sm:text-2xl'>{formatPrice(subtotal)}</span>
                </div>
                </>
            )}


        </div>
    );
}

export default Cart;