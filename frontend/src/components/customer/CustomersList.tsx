import { useEffect, useState } from 'react'
import type { Customer } from '../../types/customer/Customer';
import { getAllCustomers, getCustomerTypes } from '../../service/CustomerService';
import { useNavigate } from 'react-router-dom';
import type { CustomerTypes } from '../../types/customer/CustomerTypes';

const CustomersList = () => {
  
  const [customers,setCustomers] = useState<Customer[]>([])
  const [customerTypes,setCustomerTypes] = useState<CustomerTypes[]>([])
  
  
    
  const navigator = useNavigate()

  useEffect(() => {
    getAllCustomers().then(response => {
      console.log(response.data)
      let customersData: Customer[] = response.data
      customersData.sort((a,b) => a.id-b.id)
      setCustomers(customersData)
    }).catch(error => console.error(error));

    getCustomerTypes().then(response => {
          const data = response.data;
          const types: CustomerTypes[] = Object.entries(data).map(([name, translate]) => ({ name, translate: translate as string }));
          setCustomerTypes(types)
        }).catch((err) => console.error(err))

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
              <tr key={customer.id} onClick={() => navigator(`/customers/${customer.id}`)} style={{cursor: 'pointer'}}>
                <td>{customer.id}</td>
                <td>{customer.firstName}</td>
                <td>{customer.lastName}</td>
                <td>{customer.patronymic}</td>
                <td>{customer.email}</td>
                <td>{customerTypes.find(t => t.name === customer.type)?.translate}</td>
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