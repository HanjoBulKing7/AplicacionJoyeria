import React from 'react'
import { useCheckoutContext } from '../hooks/useCheckoutContext'
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import { FaPaypal } from "react-icons/fa";
import { FaStripeS } from "react-icons/fa";

const PaymentMethod = () => {

    const { checkoutMethod, setCheckoutMethod } = useCheckoutContext()



    const paymentMethodHandler = (method) => {
        setCheckoutMethod(method);
    }

    return (
        <div className='w-full flex flex-col items-center justify-center mt-4 gap-7'>
            <h1 className='text-5xl text-white font-light tracking-wider'>Payment Method</h1>
            <FormControl>
                <RadioGroup
                    aria-labelledby="payment method"
                    defaultValue={null}
                    name="radio-buttons-group"
                    onChange={(e)=>paymentMethodHandler(e.target.value)}
                    className='gap-10'
                >
                    <FormControlLabel value="Stripe" className='text-white' control={  <Radio color='primary' className='text-white' /> } 
                     label={             
                        <div className="flex items-center gap-2">
                            <FaPaypal className="text-2xl" />
                            <span>Paypal</span>
                        </div>
                        } >
                        <FaPaypal />
                    </FormControlLabel>
                    <FormControlLabel value="Paypal" className='text-white' control={ <Radio color='primary' className='text-white'/> } 
                    label={              
                        <div className="flex items-center gap-2">
                            <FaStripeS className="text-2xl" />
                            <span>Stripe</span>
                        </div>
                    } >
                    </FormControlLabel>
                </RadioGroup>
            </FormControl>
        </div>
    )
}   

export default PaymentMethod