import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { getObjectById, getObjectStatuses, getObjectTypes } from '../../service/SecurityObjectService'
import type { SecurityObject } from '../../types/object/SecurityObject'
import type { EnumTranslate } from '../../types/EnumTranslate'

const SecurityObjectPage = () => {
  
  const {id} = useParams()!
  const [object,setObject] = useState<SecurityObject>()
  const [objectTypes,setObjectTypes] = useState<EnumTranslate[]>([])
  const [objectStatuses, setObjectStatuses] = useState<EnumTranslate[]>([])

  useEffect(()=>{
    getObjectStatuses().then(response => {
      let statuses :EnumTranslate[] = Object.entries(response.data).map(([name,translate]) => ({name,translate:translate as string}))
      setObjectStatuses(statuses)
    })

    getObjectTypes().then(response => {      
      let types :EnumTranslate[] = Object.entries(response.data).map(([name,translate]) => ({name,translate:translate as string}))
      setObjectTypes(types)
    })

    getObjectById(`${id}`).then(response=>{
      let responseObject :SecurityObject = response.data
      //добавить дату для работ
      responseObject.equipment?.sort((a,b) =>a.id-b.id)
      setObject(responseObject)

    })
  },[])
  
  
  
  return (
    <div className='container mt-3'>
      <h2 className='text-center mb-4'>Картка об'єкта</h2>
      <div className='card mb-4'>
        <div className='card-body'>
          <table className='table table-bordered mb-0'>
            <tbody>
              <tr>
                <th className='w-25'>Id</th>
                <td>{object?.id}</td>
              </tr>
              <tr>
                <th>Адреса</th>
                <td>{object?.address}</td>
              </tr>
              <tr>
                <th>Загальна площа</th>
                <td>{object?.area + ' м²'}</td>
              </tr>
              <tr>
                <th>Поверх</th>
                <td>{object?.floor}</td>
              </tr>
              <tr>
                <th>Тип</th>
                <td>{objectTypes.find(o=> o.name === object?.type)?.translate}</td>
              </tr>
              <tr>
                <th>Статус</th>
                <td>{objectStatuses.find(o => o.name === object?.status)?.translate}</td>
              </tr>
              <tr>
                <th>Замовник</th>
                <td>{object?.customer.firstName + ' ' + object?.customer.lastName}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      {/* <h4 className='mb-3'>Об'єкти охорони</h4>
      {customer?.objects?.map(obj => (
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
      ))} */}
    </div>
  )
}

export default SecurityObjectPage