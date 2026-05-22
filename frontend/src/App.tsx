import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import CustomersList from './components/customer/CustomersList.tsx'
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom'
import AddAndEditCustomer from './components/customer/AddAndEditCustomer.tsx'
import CustomerPage from './components/customer/CustomerPage.tsx'
import Header from './components/Header.tsx'
import SecurityObjectList from './components/object/SecurityObjectList.tsx'

function App() {
  return (
    <>
    <BrowserRouter>
    <Header/>
      <Routes>
        <Route path='/' element = { <CustomersList/> }></Route>
        <Route path='/customers' element = { <CustomersList/> }></Route>
        <Route path='/add-customer' element = {<AddAndEditCustomer/>}></Route>
        <Route path='/edit-customer/:id' element = { <AddAndEditCustomer/>}></Route>
        <Route path='/customers/:id' element = { <CustomerPage/>}></Route>
         <Route path='/objects' element = { <SecurityObjectList/>}></Route>
      </Routes>

    </BrowserRouter>
    </>
  )
}

export default App
