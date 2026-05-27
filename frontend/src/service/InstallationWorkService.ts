import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_BASE_URL + '/works'

export const getWorksForObject = (id:string) =>{
  return axios.get(BASE_URL+ '/' + id);
}