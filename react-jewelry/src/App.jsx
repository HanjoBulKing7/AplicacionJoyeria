import { BrowserRouter, Routes, Route } from 'react-router-dom' 
import './App.css'
import MainItems from './components/products/MainItems'
import Navbar from './components/shared/Navbar'
import Home from './components/home/Home'

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path='/' element={ <Home /> } />
        <Route path='/items' element={ <MainItems /> } />
      </Routes>
    </BrowserRouter>
  )
}

export default App
