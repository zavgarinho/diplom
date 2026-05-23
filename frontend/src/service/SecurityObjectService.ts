import axios from "axios";
import type { SecurityObjectRequest } from "../types/object/SecurityObjectRequest";



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

export const getObjectById = (id:string) =>{
  return axios.get(BASE_URL+ '/'+id)
}

export const createObject = (object:SecurityObjectRequest) =>{
  return axios.post(BASE_URL,object)
}
