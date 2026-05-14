import axios from 'axios'
import type { Customer } from '../types/customer/Customer';

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

export const createCustomer = (customer : Customer) =>{
    return axios.post(BASE_URL, customer)
} 

export const editCustomer = (customer: Customer, id: string) =>{
    return axios.put(BASE_URL+'/'+id, customer) 
}

export const deleteCustomer = (id:string) =>{
    return axios.delete(BASE_URL+'/'+id)
}