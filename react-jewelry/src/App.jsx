import { useState } from 'react'
import './App.css'
import MainItems from './components/products/MainItems'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <MainItems />
    </>
  )
}

export default App
