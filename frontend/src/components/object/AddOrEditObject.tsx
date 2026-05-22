import { useState } from 'react'
import { useParams } from 'react-router-dom'
import type { SecurityObject } from '../../types/object/SecurityObject'


const AddOrEditObject = () => {
  
  const {id} = useParams()
  id: number;
      address: string;
      area: number;
      floor: number;
      type: string;
      status: string;
      customer: CustomerShort;
      works?: InstallationWorkShort[]
      equipment?: EquipmentShort[]
  const [currentObject, setCurrentObject] = useState<SecurityObject>({id:0,address:'',area:0,floor:0,type:'',status:'',customer: Customer})
  
  return (
    <div className="container">

      <h2>{id ? "Редагувати об'єкт" : "Створити об'єкт"} </h2>
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
        <button className='btn btn-success' onClick={addOrEditCustomer}>{id? 'Редагувати' : 'Додати'}</button>
        </div>


      </form>

    </div>
    
    
  )
}

export default AddOrEditObject