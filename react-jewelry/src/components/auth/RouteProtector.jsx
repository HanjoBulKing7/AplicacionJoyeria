import { useSelector } from 'react-redux';
import { Navigate, Outlet } from 'react-router-dom';

function RouteProtector({ isAuthPage = false , adminOnly = false }) {

  const { accessToken , roles }  = useSelector((state) => state.auth);

  const isAdmin = roles && roles.includes("ADMIN");

  if (isAuthPage)
    return accessToken ? <Navigate to="/" /> : <Outlet />;

  if(adminOnly && !isAdmin)
    return <Navigate to="/" />
  
  return accessToken ? <Outlet /> : <Navigate to="/login" />;
}

export default RouteProtector