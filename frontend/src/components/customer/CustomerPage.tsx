import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import type { Customer } from '../../types/customer/Customer'
import { getCustomerById } from '../../service/CustomerService'

const CustomerPage = () => {
  const {id} = useParams()

  const [customer, setCustomer] = useState<Customer>()

  useEffect( () => {
    if(!id)
      return;
    getCustomerById(id).then(response =>{
      console.log(response.data)
      let responseData : Customer = response.data
      responseData?.objects.sort((a,b) => a.id-b.id)
      setCustomer(responseData)
    })
  },[])


  return (
    <div className='container mt-3'>
      <h2 className='text-center mb-4'>Картка клієнта</h2>
      <div className='card mb-4'>
        <div className='card-body'>
          <table className='table table-bordered mb-0'>
            <tbody>
              <tr>
                <th className='w-25'>Id</th>
                <td>{customer?.id}</td>
              </tr>
              <tr>
                <th>Ім'я</th>
                <td>{customer?.firstName}</td>
              </tr>
              <tr>
                <th>Прізвище</th>
                <td>{customer?.lastName}</td>
              </tr>
              <tr>
                <th>По батькові</th>
                <td>{customer?.patronymic}</td>
              </tr>
              <tr>
                <th>Email</th>
                <td>{customer?.email}</td>
              </tr>
              <tr>
                <th>Кількість об'єктів</th>
                <td>{customer?.objects?.length}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <h4 className='mb-3'>Об'єкти охорони</h4>
      {customer?.objects.map(obj => (
        <div className='card mb-3' key={obj.id}>
          <div className='card-header'>
            Об'єкт #{obj.id}
          </div>
          <div className='card-body'>
            <table className='table table-sm table-bordered mb-0'>
              <tbody>
                <tr>
                  <th className='w-25'>Адреса</th>
                  <td>{obj.address}</td>
                </tr>
                <tr>
                  <th>Площа</th>
                  <td>{obj.area}</td>
                </tr>
                <tr>
                  <th>Поверх</th>
                  <td>{obj.floor}</td>
                </tr>
                <tr>
                  <th>Тип</th>
                  <td>{obj.type}</td>
                </tr>
                <tr>
                  <th>Статус</th>
                  <td>{obj.status}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      ))}
    </div>
  )
}

export default CustomerPage