import { useSelector } from 'react-redux';
import { Navigate, Outlet } from 'react-router-dom';

function RouteProtector({ isAuthPage = false }) {

  const  token  = useSelector((state) => state.auth.accessToken);

  if (isAuthPage)
    return token ? <Navigate to="/" /> : <Outlet />;
  
  return token ? <Outlet /> : <Navigate to="/login" />;
}

export default RouteProtector