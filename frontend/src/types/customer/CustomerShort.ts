export interface CustomerShort {
  id: number;
  firstName: string;
  lastName: string;
  patronymic: string;
  email: string;
  type: string;
}

export const createDefaultCustomerShort = () =>{
  const customer = {
    id: 0,
    firstName:'',
    lastName:'',
    patronymic:'',
    email:'',
    type:''
  }
  return customer;
}