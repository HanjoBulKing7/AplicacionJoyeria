import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux';
import { useLocation } from 'react-router-dom'; // ← faltaba este import
import { confirmPayment } from '../../../redux/actions/authActions';
import { clearClientSecret } from '../../../redux/slices/authSlice';
import { clearCart } from '../../../redux/slices/cartSlice';
import { FaCheckCircle } from 'react-icons/fa';

function SuccessPage() {

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search); // ← URLSearchParams, no UrlSearchParams
    const dispatch = useDispatch();

    const paymentIntent = searchParams.get("payment_intent");
    const clientSecret = searchParams.get("payment_intent_client_secret");
    const redirectStatus = searchParams.get("redirect_status");
    const orderId = localStorage.getItem("currentOrderId");

    const [confirmed, setConfirmed] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (paymentIntent && clientSecret && redirectStatus && orderId) {
            const sendData = {
                orderId: Number(orderId),
                paymentId: paymentIntent,
                pgRes: redirectStatus
            };

            dispatch(confirmPayment(sendData))
                .then((result) => {
                    if (result.meta.requestStatus === 'fulfilled') {
                        localStorage.removeItem("currentOrderId");
                        localStorage.removeItem("Cart");
                        dispatch(clearClientSecret());
                        dispatch(clearCart());
                        setConfirmed(true);
                    } else {
                        setError("Could not confirm your order. Please contact support.");
                    }
                });
        }
    }, [paymentIntent, clientSecret, redirectStatus]);

    return (
        <div className='min-h-screen flex items-center justify-center bg-slate-950'>
            <div className='p-8 rounded-2xl text-center max-w-md mx-auto bg-zinc-900 border border-white/5'>
                {confirmed ? (
                    <>
                        <div className='text-emerald-400 mb-4 flex justify-center'>
                            <FaCheckCircle size={64} />
                        </div>
                        <h1 className='text-3xl font-bold text-white mb-2'>Payment completed</h1>
                        <p className='text-zinc-400'>
                            Your order was successfully paid. Thanks for your purchase!
                        </p>
                    </>
                ) : error ? (
                    <>
                        <h1 className='text-3xl font-bold text-red-400 mb-2'>Something went wrong</h1>
                        <p className='text-zinc-400'>{error}</p>
                    </>
                ) : (
                    <>
                        <div className='w-8 h-8 border-2 border-white/20 border-t-white rounded-full animate-spin mx-auto mb-4' />
                        <p className='text-zinc-400'>Confirming your order...</p>
                    </>
                )}
            </div>
        </div>
    );
}

export default SuccessPage;