import { BrowserRouter, Routes, Route } from 'react-router-dom' 
import './App.css'
import MainItems from './components/products/MainItems'
import Navbar from './components/shared/Navbar'
import Home from './components/home/Home'
import About from './components/About'
import Contact from './components/contact/Contact'
import Cart from './components/cart/Cart'

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path='/' element={ <Home /> } />
        <Route path='/items' element={ <MainItems /> } />
        <Route path='/contact' element={ <Contact /> } />
        <Route path='/about' element={ <About /> } />
        <Route path='/cart' element={ <Cart />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App