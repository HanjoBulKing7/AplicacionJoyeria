import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import toast from 'react-hot-toast';

const useAuthToast = () => {
    const message = useSelector((state) => state.auth.message);
    
    useEffect(() => {
        if(message) toast.success(message);
    }, [message]);
}

export default useAuthToast;