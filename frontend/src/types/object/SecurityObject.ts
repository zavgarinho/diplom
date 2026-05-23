import { createDefaultCustomerShort, type CustomerShort } from "../customer/CustomerShort";
import type { EquipmentShort } from "../equipment/EquipmentShort";
import type { InstallationWorkShort } from "../work/InstallationWorkShort";

export interface SecurityObject{
    id: number;
    address: string;
    area: number;
    floor: number;
    type: string;
    status: string;
    customer: CustomerShort;
    works?: InstallationWorkShort[]
    equipment?: EquipmentShort[]
}

export const createDefaultObject = () =>{
    const object: SecurityObject = {
        id:0,
        address:'',
        area:0,
        floor:0,
        type:'',
        status:'',
        customer: createDefaultCustomerShort(),
        works:[],
        equipment:[]
    }
    return object;
}
