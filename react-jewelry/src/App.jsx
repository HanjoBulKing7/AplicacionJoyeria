import { BrowserRouter, Routes, Route } from 'react-router-dom' 
import MainItems from './components/products/MainItems'
import Navbar from './components/shared/Navbar'
import Home from './components/home/Home'
import About from './components/About'
import Contact from './components/contact/Contact'
import Cart from './components/cart/Cart'
import Login from './components/auth/Login'
import Register from './components/auth/Register'
import RouteProtector from './components/auth/RouteProtector'
import useAuthToast from './components/hooks/useAuthToast'
import Checkout from './components/checkout/Checkout'
import SuccessPage from './components/checkout/Stripe/SuccessPage'

function App() {
  useAuthToast();
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path='/' element={ <Home /> } />
        <Route path='/items' element={ <MainItems /> } />
        <Route path='/contact' element={ <Contact /> } />
        <Route path='/about' element={ <About /> } />
        <Route path='/cart' element={ <Cart />} />
        <Route path='/checkout' element={ <Checkout />} />
        <Route path='/order-confirmed' element={  <SuccessPage />} />

        <Route element={ <RouteProtector isAuthPage={true} /> } >
          <Route path='/login' element={ <Login /> } />
          <Route path='/signup' element={ <Register /> } />
        </Route>
        
      </Routes>
    </BrowserRouter>
  )
}

export default App