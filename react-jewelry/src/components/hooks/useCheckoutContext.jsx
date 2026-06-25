import { createContext, useContext , useState } from "react"; 

const CheckoutContext = createContext();

export const CheckoutProvider = ({children}) => {

    const [ checkoutAddress, setCheckoutAddress ] = useState(null);
    const [ checkoutMethod, setCheckoutMethod ] = useState(null)


    return(
        <CheckoutContext.Provider value={{ checkoutAddress, setCheckoutAddress, checkoutMethod, setCheckoutMethod }} >
            {children}
        </CheckoutContext.Provider>
    )
}

export const useCheckoutContext = () => useContext(CheckoutContext);