import axios from 'axios'

const BASE_URL = import.meta.env.VITE_CUSTOMER_API_BASE_URL;


export const getAllCustomers = () => {
    return axios.get(BASE_URL)

}

export const getCustomerTypes = () => {
    return axios.get(BASE_URL + "/customer-types")
}

export const getCustomerById = (id:string) => {
    return axios.get(BASE_URL + "/" + id)
}