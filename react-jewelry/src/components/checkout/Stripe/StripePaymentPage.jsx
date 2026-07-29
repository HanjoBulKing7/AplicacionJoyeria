
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useCheckoutContext } from '../../hooks/useCheckoutContext';
import { createStripeSecret } from '../../../redux/actions/authActions';

const stripePromise = loadStripe( import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);


const StripePaymentPage = () => {


    const dispatch = useDispatch();
    const { clientSecret , username } = useSelector((state)=> state.auth );
    const { cart } = useSelector((state) => state.cart )

    const totalPrice = parseInt(cart?.reduce((acc, cur) => acc += (cur.quantity * cur.price), 0));

      const { checkoutAddress: currentAddress } = useCheckoutContext();

    useEffect(()=>{
        if(!clientSecret){
            const orderData = {
                addressId: currentAddress.addressId,
                amount: Number(totalPrice * 100),
                currency: "mxn",
                name: username,
                description: `Order for ${username}`,
                metadata: {
                    test: "1"
                }
            };
            dispatch(createStripeSecret(orderData));
        }

    },[dispatch, clientSecret])

    const options = {   clientSecret: clientSecret  };


  return (
    <>
    {
        clientSecret && (
            <Elements   stripe={stripePromise} options={options} >
                <PaymentForm clientSecret={clientSecret} totalPrice={totalPrice}/>
            </Elements>
        )
    }
    </>
  )
}

export default StripePaymentPage