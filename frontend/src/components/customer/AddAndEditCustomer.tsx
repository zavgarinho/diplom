import React, { useEffect, useState } from 'react'
import { createCustomer, editCustomer, getCustomerById, getCustomerTypes } from '../../service/CustomerService'
import type { EnumTranslate } from '../../types/EnumTranslate'
import { createDefaultCustomer, type Customer } from '../../types/customer/Customer'
import { useNavigate, useParams } from 'react-router-dom'

const AddEndEditCustomer = () => {
  const [customerTypes,setCustomerTypes] = useState<EnumTranslate[]>([])
  const [currentCustomer, setCustomer] = useState<Customer>(createDefaultCustomer())
  const navigator = useNavigate()
  const { id } = useParams()



  useEffect(()=>{
    getCustomerTypes().then(response => {
      const data = response.data;
      const types: EnumTranslate[] = Object.entries(data).map(([name, translate]) => ({ name, translate: translate as string }));
      setCustomerTypes(types)
      if(!id){
        const changedCustomer = {...currentCustomer}
        changedCustomer.type = types[0].name
        setCustomer(changedCustomer)
      }else{
        getCustomerById(id).then(response =>{
            setCustomer(response.data)
        }).catch(error => console.error(error))

      }
        
      
    }).catch((err) => console.error(err))
  },[])

  const addOrEditCustomer = (e: React.MouseEvent) => {
      e.preventDefault();
      console.log(currentCustomer)
      if(id){
        editCustomer(currentCustomer, id).then(response =>{
          console.log(response.data)
        }).catch(error => console.error(error))
      }else{
        
      createCustomer(currentCustomer).then(response =>{
        console.log(response.data)
      }).catch(error => console.error(error))
    }
    navigator('/customers')
  }

  return (
    <div className='container '>
      <h2>{id ? 'Редагувати Клієнта' : 'Додати Клієнта'}</h2>
      <form action="">
        <div className="form-group">
          <label htmlFor="firstName">Введіть ім'я</label>
          <input type="text" className="form-control" placeholder="Ім'я" id='firstName' value={currentCustomer.firstName} onChange={(e)=> {
            let changedCustomer = {...currentCustomer}
            changedCustomer.firstName = e.target.value
            setCustomer(changedCustomer)
          }}/>
        </div>
        <div className="form-group">
          <label htmlFor="lastName">Введіть прізвище</label>
          <input type="text" className="form-control" placeholder="Прізвище" id='lastName' value = {currentCustomer.lastName} onChange={(e)=> {
            let changedCustomer = {...currentCustomer}
            changedCustomer.lastName = e.target.value
            setCustomer(changedCustomer)
          }}/>
        </div>
        <div className="form-group">
          <label htmlFor="patronymic">Введіть по батькові</label>
          <input type="text" className="form-control" placeholder="По батькові" id='patronymic' value={currentCustomer.patronymic} onChange={(e)=> {
            let changedCustomer = {...currentCustomer}
            changedCustomer.patronymic = e.target.value
            setCustomer(changedCustomer)
          }}/>
        </div>
        <div className="form-group">
          <label htmlFor="email">Введіть пошту</label>
          <input type="text" className="form-control" placeholder="Email" id='email' value={currentCustomer.email} onChange={(e)=> {
            let changedCustomer = {...currentCustomer}
            changedCustomer.email = e.target.value
            setCustomer(changedCustomer)
          }}/>
        </div>
        <div className="form-group">
          <label htmlFor="clientType">Введіть тип клієнта</label>
         <select className="form-control" id="clientType" value={currentCustomer.type} onChange={(e) => {
            const changedCustomer = {...currentCustomer}
            changedCustomer.type = e.target.value
            setCustomer(changedCustomer)
          }}>
            {customerTypes.map((type) => (
              <option key={type.name} value={type.name}>{type.translate}</option>
            ))}
        </select>
        </div>
        <button className='btn btn-success' onClick={addOrEditCustomer}>{id? 'Редагувати' : 'Додати'}</button>
        

      </form>
      </div>
  )
}

export default AddEndEditCustomer