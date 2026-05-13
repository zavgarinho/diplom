import React, { useEffect, useState } from 'react'
import { getCustomerTypes } from '../service/CustomerService'
import type { CustomerTypes } from '../types/customer/CustomerTypes'

const AddCustomer = () => {
  const [customerTypes,setCustomerTypes] = useState<CustomerTypes[]>([])


  useEffect(()=>{
    getCustomerTypes().then(response => {
      const data = response.data;
      const types: CustomerTypes[] = Object.entries(data).map(([name, translate]) => ({ name, translate: translate as string }));
      setCustomerTypes(types)
    }).catch((err) => console.error(err))

  },[])

  return (
    <div className='container '>
      <h2>Додати Клієнта</h2>
      <form action="">
        <div className="form-group">
          <label htmlFor="firstName">Введіть ім'я</label>
          <input type="text" className="form-control" placeholder="Ім'я" id='firstName'/>
        </div>
        <div className="form-group">
          <label htmlFor="lastName">Введіть прізвище</label>
          <input type="text" className="form-control" placeholder="Прізвище" id='lastName'/>
        </div>
        <div className="form-group">
          <label htmlFor="patronymic">Введіть по батькові</label>
          <input type="text" className="form-control" placeholder="По батькові" id='patronymic'/>
        </div>
        <div className="form-group">
          <label htmlFor="email">Введіть пошту</label>
          <input type="text" className="form-control" placeholder="Email" id='email'/>
        </div>
        <div className="form-group">
          <label htmlFor="clientType">Введіть тип клієнта</label>
         <select className="form-control" id="clientType">
            {customerTypes.map((type) => (
              <option key={type.name} value={type.name}>{type.translate}</option>
            ))}
        </select>
        <button className='btn btn-success'>Додати</button>
        </div>


      </form>
      </div>
  )
}

export default AddCustomer