import axios from "axios";



const BASE_URL = import.meta.env.VITE_API_BASE_URL +'/objects'

export const getAllObjects = () =>{
  return axios.get(BASE_URL)
}

export const getObjectTypes = () => {
  return axios.get(BASE_URL + '/object-types')
}

export const getObjectStatuses = () =>{
  return axios.get(BASE_URL + '/object-statuses')
}
