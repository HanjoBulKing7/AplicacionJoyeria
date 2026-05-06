import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import store from './redux/store.js'
import { Provider } from 'react-redux'
import { Toaster } from 'react-hot-toast'
import { TOAST_OPTIONS } from './utils/toastStlying.js'

createRoot(document.getElementById('root')).render(
  
  <Provider store={store}>
    <StrictMode>
      <Toaster position="center" toastOptions={TOAST_OPTIONS}/>
      <App />
    </StrictMode>
  </Provider>,
)
