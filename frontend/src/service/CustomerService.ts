import axios from 'axios'

const BASE_URL = import.meta.env.VITE_CUSTOMER_API_BASE_URL;


export const getAllCustomers = () => {
    return axios.get(BASE_URL)

}