import { PaymentElement, useElements, useStripe } from '@stripe/react-stripe-js'
import React, { useState } from 'react'

const StripePaymentForm = ({ clientSecret, totalPrice }) => {

    const stripe = useStripe();
    const elements = useElements();
    const [errorMessage, setErrorMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!stripe || !elements) return;

        const { error: submitError } = await elements.submit();
        if (submitError) {
            setErrorMessage(submitError.message);
            return;
        }

        const { error } = await stripe.confirmPayment({
            elements,
            clientSecret,
            confirmParams: {
                return_url: `${import.meta.env.VITE_FRONTEND_URL}/order-confirmed`
            },
        });

        if (error) {
            setErrorMessage(error.message);
        }
    };

    const elementOptions = {
        layout: "tabs",
    };

    const isLoading = !clientSecret || !stripe || !elements;

    return (
        <form onSubmit={handleSubmit} className='max-w-lg mx-auto p-4'>
            <h2 className='text-xl font-semibold m-4 text-white'>Payment information</h2>

            <>
                {clientSecret && <PaymentElement options={elementOptions} />}
                {errorMessage && (
                    <div className='text-red-500 mt-2 text-sm'>{errorMessage}</div>
                )}
            </>

            <button
                className='text-white w-full px-5 p-2.5 bg-black mt-4 rounded-md font-bold disabled:opacity-50 disabled:animate-pulse'
                disabled={!stripe || isLoading}
            >
                {!isLoading ? `Pay $${Number(totalPrice).toFixed(2)}` : "Processing..."}
            </button>
        </form>
    );
};

export default StripePaymentForm;