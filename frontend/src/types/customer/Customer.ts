import type { SecurityObjectShort } from "../object/SecurityObjectShort";

export interface Customer {
  id?: number;
  firstName: string;
  lastName: string;
  patronymic: string;
  email: string;
  type: string;
  objects?: SecurityObjectShort[];
}

export const createDefaultCustomer = () =>{
  const customer: Customer = {
    firstName:'',
    lastName:'',
    patronymic:'',
    email:'',
    type:'',
  }
  return customer;
}