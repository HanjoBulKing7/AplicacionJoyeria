import { Step, StepLabel, Stepper } from '@mui/material'
import React,{ useState } from 'react'
import Address from './addressSelection/Address';

const Checkout = () => {

    const [ activeStep , setActiveStep ] = useState(0)
    const steps = [
        { id: 1, label: "Select address"},
        { id: 2, label: "Select payment method"},
        { id: 3, label: "Order summary"},
        { id: 4, label: "Complete payment"},
    ];


  return (
    <div className='w-full mt-10'>
        <Stepper activeStep={activeStep} alternativeLabel>
            {steps.map((step) => (
                <Step key={step.id}>
                    <StepLabel>{step.label}</StepLabel>
                </Step>
            ))}
        </Stepper>
        {
            <div className='mt-5'>
                { activeStep === 0 && <Address /> }
            </div>
        }

    </div>
  )
}

export default Checkout