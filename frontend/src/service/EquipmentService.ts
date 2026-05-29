import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_BASE_URL+"/equipment";

export const getEquipmentTypes = () => {
  return axios.get(BASE_URL+"/equipment-types")
}
