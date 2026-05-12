import { useEffect, useState } from 'react'
import type { Customer } from '../types/Customer';
import { getAllCustomers } from '../service/CustomerService';
import { useNavigate } from 'react-router-dom';

const CustomersList = () => {
  
  const [customers,setCustomers] = useState<Customer[]>([])
  const navigator = useNavigate()

  useEffect(() => {
    getAllCustomers().then(response => {
      console.log(response.data)
      setCustomers(response.data)
    }).catch(error => console.error(error));

  },[])

  

  return (
    <>
    
      <div className='container mt-3'>
        <h2 className='text-center'>Список клієнтів</h2>
        <button className='btn btn-primary' onClick={() =>{
          navigator('/add-customer')
        }}>
          Додати клієнта
          </button>
        <table className='table table-striped'>
          <thead>
            <tr>
              <th>Id</th>
              <th>Ім'я</th>
              <th>Прізвище</th>
              <th>По батькові</th>
              <th>Email</th>
              <th>Тип</th>
              <th>Кількість об'єктів</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((customer) => (
              <tr key={customer.id}>
                <td>{customer.id}</td>
                <td>{customer.firstName}</td>
                <td>{customer.lastName}</td>
                <td>{customer.patronymic}</td>
                <td>{customer.email}</td>
                <td>{customer.type}</td>
                <td>{customer.objects?.length}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

    </>
  )
}

export default CustomersList