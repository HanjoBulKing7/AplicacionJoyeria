import { Navigate, Outlet } from 'react-router-dom';
import toast from "react-hot-toast";

function RouteProtector({ isAuthPage = false, adminOnly = false }) {

  const user = JSON.parse(localStorage.getItem('userInfo'));

  const isAuthenticated = !!user?.accessToken;
  const isAuthorized = user?.roles?.includes("ADMIN");

  if (isAuthPage) {
    return isAuthenticated
      ? <Navigate to="/" />
      : <Outlet />;
  }

  if (adminOnly && !isAuthorized) {
    toast.error('You are not authorized to acccess')
    return <Navigate to="/" />;
  }

  return isAuthenticated
    ? <Outlet />
    : <Navigate to="/login" />;
}

export default RouteProtector;