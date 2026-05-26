import React, { useEffect, useState } from 'react'
import type { SecurityObject } from '../../types/object/SecurityObject'
import { deleteObject, getAllObjects, getObjectStatuses, getObjectTypes } from '../../service/SecurityObjectService'
import { useNavigate } from 'react-router-dom'
import type { EnumTranslate } from '../../types/EnumTranslate'

const SecurityObjectList = () => {
  const[objects,setObjects] = useState<SecurityObject[]>([])
  const[objectTypes,setObjectTypes] = useState<EnumTranslate[]>([])
  const[objectStatuses, setObjectStatuses] = useState<EnumTranslate[]>([])

  const navigator = useNavigate()

  useEffect( ()=>{
    getAllObjects().then(response =>{
      console.log(response.data)
      setObjects(response.data)
    })
    getObjectTypes().then(response =>{
      console.log(response.data)
      let translate:EnumTranslate[] = Object.entries(response.data).map(([name, translate]) => ({ name, translate: translate as string }));
      setObjectTypes(translate)
    })
    getObjectStatuses().then(response =>{
      console.log(response.data)
      let translate:EnumTranslate[] = Object.entries(response.data).map(([name, translate]) => ({ name, translate: translate as string }));
      setObjectStatuses(translate)
    })

  },[])

  const deleteObjectFunc = (e:React.MouseEvent, id:number) =>{
    e.stopPropagation()
    e.preventDefault()
    deleteObject(`${id}`).then( ()=>{
      setObjects(objects.filter(o => o.id!=id))
    }).catch(error => console.error(error))
  }

  return (
    <div className='container mt-3'>
        <h2 className='text-center'>Список об'єктів</h2>
        <button className='btn btn-primary' onClick={() =>{
          navigator('/add-object')
        }}>
          Додати об'єкт
          </button>
        <table className='table table-striped text-center align-middle'>
          
          
          <thead>
            <tr>
              <th>Id</th>
              <th>Адрес</th>
              <th>Площа</th>
              <th>Поверх</th>
              <th>Тип</th>
              <th>Статус</th>
              <th>Замовник</th>
              <th>Дії</th>
            </tr>
          </thead>

      
          <tbody>
            {objects.map((object) => (
              <tr key={object.id} onClick={() => navigator(`/objects/${object.id}`)} style={{cursor: 'pointer'}}>
                <td>{object.id}</td>
                <td>{object.address}</td>
                <td>{object.area + ' м²'}</td>
                <td>{object.floor}</td>
                <td>{objectTypes.find(t => t.name === object.type)?.translate}</td>
                <td>{objectStatuses.find(s => s.name == object.status)?.translate}</td>
                <td>{object.customer.firstName + ' ' + object.customer.lastName}</td>
                <td>
                  <button className='btn btn-secondary me-2' onClick={(e) => { e.stopPropagation(); navigator(`/edit-object/${object.id}`)}}> Редагувати</button>
                  <button className='btn btn-danger' onClick={(e) => {deleteObjectFunc(e, object.id!)}}> Видалити </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

  )
}

export default SecurityObjectList