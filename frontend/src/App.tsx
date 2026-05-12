import 'bootstrap/dist/css/bootstrap.min.css'
import CustomersList from './components/CustomersList.tsx'
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom'
import AddCustomer from './components/AddCustomer.tsx'

function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path='/customers' element = { <CustomersList/> }></Route>
        <Route path='/add-customer' element = {<AddCustomer/>}></Route>
      </Routes>

    </BrowserRouter>
    </>
  )
}

export default App
