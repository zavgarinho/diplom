import type { CustomerShort } from "../customer/CustomerShort";
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