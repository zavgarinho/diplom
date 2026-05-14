import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import CustomersList from './components/customer/CustomersList.tsx'
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom'
import AddCustomer from './components/customer/AddCustomer.tsx'
import CustomerPage from './components/customer/CustomerPage.tsx'
import Header from './components/Header.tsx'

function App() {
  return (
    <>
    <BrowserRouter>
    <Header/>
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
