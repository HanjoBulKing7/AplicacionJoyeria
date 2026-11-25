import React, { useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useDispatch } from 'react-redux' 
import { toast } from 'react-hot-toast';
import { fetchDashboardData } from '../../../redux/actions/authActions';

const AdminDashboard = () => {

  const { dashboardData } = useSelector((state) => state.auth );
  const dispatch = useDispatch();

  useEffect(()=>{
      try{
        dispatch(fetchDashboardData()).unwrap();
      }catch(e){
          toast.error( typeof e === 'string' ? e : 'Error fecthing dashboard data' )
      }
  }, [dispatch])

    console.log("Data: ", dashboardData);

  return (
    <div className='h-full'>
    </div>
  )
}

export default AdminDashboard