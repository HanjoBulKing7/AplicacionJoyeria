import { Step, StepLabel, Stepper } from '@mui/material'
import React,{ useState } from 'react'
import Address from './addressSelection/Address';
import { CheckoutProvider, useCheckoutContext } from '../hooks/useCheckoutContext';
import PaymentMethod from './PaymentMethod';


const stepperStyle = { 
    py: 4, 
    bgcolor: 'transparent',
    '& .MuiStepConnector-line': {
      borderColor: 'rgba(255, 255, 255, 0.3)', // Línea blanca sutil
      borderTopWidth: 1,
    }
};

const stepLabelStyle = {
          // Estilo del Texto (Label)
          '& .MuiStepLabel-label': {
            color: 'rgba(255, 255, 255, 0.5)', // Texto apagado
            fontFamily: 'Urbanist, sans-serif', // Fuente elegante
            fontSize: '0.85rem',
          },
          '& .MuiStepLabel-label.Mui-active': {
            color: '#ffffff', // Blanco brillante al estar activo
            fontWeight: 600,
          },
          '& .MuiStepLabel-label.Mui-completed': {
            color: '#ffffff',
          },
          // Estilo del Círculo/Icono
          '& .MuiStepIcon-root': {
            color: 'transparent',
            border: '1px solid rgba(255, 255, 255, 0.5)',
            borderRadius: '50%',
            '& .MuiStepIcon-text': { fill: '#ffffff' }, // Número blanco
          },
          '& .MuiStepIcon-root.Mui-active': {
            color: '#ffffff', // Fondo blanco al estar activo
            border: 'none',
            '& .MuiStepIcon-text': { fill: '#000000' }, // Número negro para contraste
          },
          '& .MuiStepIcon-root.Mui-completed': {
            color: '#ffffff', // Blanco al completar
            '& .MuiStepIcon-text': { fill: '#000000' },
          },
};

const CheckoutContent = () => {

    const [ activeStep , setActiveStep ] = useState(0)
    const steps = [
        { id: 1, label: "Select address"},
        { id: 2, label: "Select payment method"},
        { id: 3, label: "Order summary"},
        { id: 4, label: "Complete payment"},
    ];

    const { checkoutAddress , checkoutMethod } = useCheckoutContext();

  return (
    <div className='flex flex-col w-full min-h-[calc(100vh-80px)] pt-4 bg-slate-950'>
        <Stepper activeStep={activeStep} alternativeLabel
        sx={stepperStyle}>
            {steps.map((step) => (
                <Step key={step.id}>
                    <StepLabel sx={stepLabelStyle} >{step.label}</StepLabel>
                </Step>
            ))}
        </Stepper>

        {
            <div className='flex-1 h-auto'>
                { activeStep === 0 && <Address /> }
                { activeStep === 1 && <PaymentMethod /> }
            </div>
        }

        <div className='w-full h-22 bg-gray-800/90 flex justify-between items-center p-4'>
          <button
            className='h-12 border-2 rounded-lg bg-black text-white tracking-normal text-xl font-light 
             hover:bg-black/50 hover:cursor-pointer hover:border-amber-400 p-3'
             disabled={ activeStep === 0 }
             onClick={  ()=>setActiveStep(prev => prev-1) }
          >
            Back
          </button>
          <button
            className='h-12 border-2 rounded-lg bg-black text-white tracking-normal text-xl font-light
             hover:bg-black/50 hover:cursor-pointer hover:border-amber-400 p-3'
             disabled={
                    activeStep === 0 ? !checkoutAddress 
                    : activeStep === 1 ? !checkoutMethod 
                    : false
             }
             onClick={  ()=>setActiveStep(prev => prev+1) }
          >
            Proceed
          </button>
        </div>
    </div>
  )
}

const Checkout = () => {

  return(
  <CheckoutProvider>
    <CheckoutContent />
  </CheckoutProvider>
  )
}

export default Checkout;