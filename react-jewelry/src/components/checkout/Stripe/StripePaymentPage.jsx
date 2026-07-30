import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useCheckoutContext } from '../../hooks/useCheckoutContext';
import { createStripeSecret } from '../../../redux/actions/authActions';
import StripePaymentForm from './StripePaymentForm';
import { current } from '@reduxjs/toolkit';
import { pgTypes } from '../../../domain/pgNames'

const stripePromise = loadStripe( import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);


const StripePaymentPage = () => {


    const dispatch = useDispatch();
    const { clientSecret , username , email } = useSelector((state)=> state.auth );
    const { cart } = useSelector((state) => state.cart )
    const { checkoutAddress: currentAddress } = useCheckoutContext();

    useEffect(()=>{
        if(!clientSecret && currentAddress?.addressId ){
            const orderData = {
                addressId: currentAddress.addressId,
                currency: "mxn",
                pgName: PG_TYPES.STRIPE,
            };
            dispatch(createStripeSecret(orderData));
        }

    },[dispatch, clientSecret, currentAddress])

    const options = {   clientSecret: clientSecret  };


  return (
    <>
    {
        clientSecret && (
            <Elements   stripe={stripePromise} options={options} >
                <StripePaymentForm clientSecret={clientSecret} totalPrice={totalPrice}/>
            </Elements>
        )
    }
    </>
  )
}

export default StripePaymentPage