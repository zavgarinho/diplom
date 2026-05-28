import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { getObjectById, getObjectStatuses, getObjectTypes } from '../../service/SecurityObjectService'
import type { SecurityObject } from '../../types/object/SecurityObject'
import type { EnumTranslate } from '../../types/EnumTranslate'
import { Gantt, type Task, type EventOption, type StylingOption, ViewMode, type DisplayOption, DependenciesColumn } from '@wamra/gantt-task-react'
import type { Dependency } from '@wamra/gantt-task-react'
import '@wamra/gantt-task-react/dist/style.css'
import type { InstallationWorkShort } from '../../types/work/InstallationWorkShort'
import { getWorksForObject } from '../../service/InstallationWorkService'


const SecurityObjectPage = () => {
  
  const {id} = useParams()!
  const [object,setObject] = useState<SecurityObject>()
  const [objectTypes,setObjectTypes] = useState<EnumTranslate[]>([])
  const [objectStatuses, setObjectStatuses] = useState<EnumTranslate[]>([])
  const [works, setWorks] = useState<InstallationWorkShort[]>([])



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
      responseObject.equipment?.sort((a,b) =>a.id-b.id)
      responseObject.works?.sort((a,b) => {
        return new Date(a.startTime).getTime() - new Date(b.startTime).getTime()
      })
      console.log(responseObject)
      setObject(responseObject)
      // getWorksForObject(`${id}`).then(response => {
      //   console.log(response.data)
      // })
    })
  },[])


  const calculateProgress = (start:Date, end:Date) =>{
    console.log("Начало = " + start)
    console.log("Конец = " + end)
    const workDays = (end.getTime() - start.getTime()) /(1000*60*60*24)
    console.log("Days for start " + start + " end " +end +" = " + workDays)
  }
  
  const getDependencyIds = (ids:number[]):Dependency[] =>{
    return ids.map(id => ({
      sourceId: String(id),
      sourceTarget: 'endOfTask',
      ownTarget: 'startOfTask',
    }))
  }

  
  const createTask = () => {
    if(!object?.works)
      return []
    calculateProgress(new Date(object.works[1].startTime), new Date(object.works[1].plannedEndTime) )
    let tasks: Task[] = object.works.map(w => {
      let dependencyIds = getDependencyIds(w.predecessorIds)
      let task: Task = {
        start: new Date(w.startTime),
        end: new Date(w.plannedEndTime),
        name: w.name,
        id: String(w.id),
        type:'task',
        //Доделать расчет прогресса по датам
        progress: 45,
        isDisabled: true,
        styles: { barProgressColor: '#ffbb54', barProgressSelectedColor: '#ff9e0d' },
        dependencies: dependencyIds
      }
        
        return task;
  })
   
    return tasks;
  }
  
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
        <div className='card mb-4'>
        <div className='card-body'>
      <h2>Діаграма робіт </h2>
        <Gantt tasks={createTask()} distances={{ titleCellWidth: 100, dateCellWidth: 150 }}  />
        </div>
        </div>
      </div>

      <div className='container mt-3'>
        
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