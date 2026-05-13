import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import CustomersList from './components/CustomersList.tsx'
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom'
import AddCustomer from './components/AddCustomer.tsx'
import CustomerPage from './components/CustomerPage.tsx'

function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path='/' element = { <CustomersList/> }></Route>
        <Route path='/customers' element = { <CustomersList/> }></Route>
        <Route path='/add-customer' element = {<AddCustomer/>}></Route>
        <Route path='/customers/:id' element = { <CustomerPage/>}></Route>
      </Routes>

    </BrowserRouter>
    </>
  )
}

export default App
