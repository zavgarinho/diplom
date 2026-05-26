import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createDefaultObject, type SecurityObject } from '../../types/object/SecurityObject'
import type { EnumTranslate } from '../../types/EnumTranslate'
import { createObject, getObjectById, getObjectStatuses, getObjectTypes, updateObject } from '../../service/SecurityObjectService'
import type { CustomerShort } from '../../types/customer/CustomerShort'
import { getAllCustomers, getCustomerById } from '../../service/CustomerService'
import type { SecurityObjectRequest } from '../../types/object/SecurityObjectRequest'


const AddOrEditObject = () => {
  
  const {id} = useParams()
  const navigator = useNavigate()
  const [currentObject, setCurrentObject] = useState<SecurityObject>(createDefaultObject())
  const [objectTypes,setObjectTypes] = useState<EnumTranslate[]>([])
  const [objectStatuses,setObjectStatuses] = useState<EnumTranslate[]>([])
  const [customers,setCustomers] = useState<CustomerShort[]>([])


  useEffect(()=>{
    getObjectTypes().then(response => {
      const objectTypesData = Object.entries(response.data).map(([name, translate]) => ({ name, translate: translate as string }));
      setObjectTypes(objectTypesData)

      getAllCustomers().then(response =>{
          console.log(response.data)
          setCustomers(response.data)
        })
      
      if(id){
        getObjectById(id).then(response =>{
         
         let object = response.data
         console.log(object)
         setCurrentObject(object)
         
        })
      }else{
        getAllCustomers().then(response =>{
          console.log(response.data)
          setCustomers(response.data)
          setCurrentObject(prev => ({...prev, type: objectTypesData[0].name, customer: response.data[0]}))
        })
      }
    })

    getObjectStatuses().then(response =>{
      const objectStatusesData = Object.entries(response.data).map(([name, translate]) => ({ name, translate: translate as string }));
      setObjectStatuses(objectStatusesData)
      if(!id){
        setCurrentObject(prev => ({...prev, status: objectStatusesData[0].name}))
      }
    })
  },[])

  const addOrEditObject = (e:React.MouseEvent) =>{
    e.preventDefault();
    
    if(id){
      let object:SecurityObjectRequest = {
        address:currentObject.address,
        area:currentObject.area,
        floor:currentObject.floor,
        type:currentObject.type,
        status:currentObject.status,
        customerId:currentObject.customer.id,
        equipmentIds:currentObject.equipment?.map(e => e.id)!,
        worksId:currentObject.works?.map(w => w.id)!
      }
      updateObject(`${id}`,object).then(response=>{
        console.log(response.data)
      }).catch(e => console.error(e))
      navigator("/objects")
    }else{
      let object:SecurityObjectRequest = {
        address:currentObject.address,
        area:currentObject.area,
        floor:currentObject.floor,
        type:currentObject.type,
        status:currentObject.status,
        customerId:currentObject.customer.id,
        equipmentIds:[],
        worksId:[]
      }
      console.log(object)
      createObject(object).then(response =>{
        console.log(response.data)
      })
      navigator("/objects")
    }
  }

  return (
    <div className="container">

      <h2 className='text-center'>{id ? "Редагувати об'єкт" : "Створити об'єкт"} </h2>
      <form action="">
        <div className="form-group">
          <label htmlFor="address">Введіть адресу</label>
          <input type="text" className="form-control" placeholder="Адреса" id='address' value={currentObject.address} onChange={(e)=> {
            let changedObject = {...currentObject}
            changedObject.address = e.target.value
            console.log(changedObject)
            setCurrentObject(changedObject)
          }}/>
        </div>
        <div className="form-group">
          <label htmlFor="area">Введіть площу</label>
          <input type="number" className="form-control" placeholder="Площа" id='area' min = "0" value={currentObject.area ==0?'':currentObject.area} onChange={(e)=> {
            let changedObject = {...currentObject}
            changedObject.area = Number(e.target.value)
            console.log(changedObject)
            setCurrentObject(changedObject)
          }}/>
        </div>

        <div className="form-group">
          <label htmlFor="floor">Введіть поверх</label>
          <input type="number" className="form-control" placeholder="Поверх" id='floor' min = "0" value={currentObject.floor ==0? '': currentObject.floor} onChange={(e)=> {
            let changedObject = {...currentObject}
            changedObject.floor = Number(e.target.value)
            console.log(changedObject)
            setCurrentObject(changedObject)
            console.log(currentObject)
          }}/>
        </div>


        <div className="form-group">
          <label htmlFor="objectType">Введіть тип об'єкта</label>
         <select className="form-control" id="objectType" value={currentObject.type} onChange={(e) => {
            let changedObject = {...currentObject}
            changedObject.type = e.target.value
            console.log(changedObject.type)
            setCurrentObject(changedObject)
          }}>
            {objectTypes.map((type) => (
              <option key={type.name} value={type.name}>{type.translate}</option>
            ))}
        </select>
        </div>

        <div className="form-group">
          <label htmlFor="objectStatus">Введіть статус об'єкта</label>
         <select className="form-control" id="objectStatus" value={currentObject.status} onChange={(e) => {
            let changedObject = {...currentObject}
            changedObject.status = e.target.value
            console.log(changedObject.status)
            setCurrentObject(changedObject)
          }}>
            {objectStatuses.map((type) => (
              <option key={type.name} value={type.name}>{type.translate}</option>
            ))}
        </select>
        </div>

        <div className="form-group">
          <label htmlFor="customer">Оберіть замовника</label>
         <select className="form-control" id="customer" value={currentObject.customer.id} onChange={(e) => {
            let changedObject = {...currentObject}
            changedObject.customer = customers.find(c => c.id == Number(e.target.value))!
            console.log(changedObject.customer)
            setCurrentObject(changedObject)
          }}>
            {customers.map((customer) => (
              <option key={customer.id} value={customer.id}>{customer.firstName + ' ' + customer.lastName}</option>
            ))}
        </select>
        <button className='btn btn-secondary' onClick={()=>navigator("/add-customer")}>Додати замовника</button>

        </div>
            <br />
        <button className='btn btn-success' onClick={addOrEditObject}>{id? 'Редагувати' : 'Додати'}</button>
        

      </form>

    </div>
    
    
  )
}

export default AddOrEditObject