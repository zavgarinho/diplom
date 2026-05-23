import type { CustomerShort } from "../customer/CustomerShort";

export interface SecurityObjectShort {
  id: number;
  address: string;
  area: number;
  floor: number;
  type: string;
  status: string;
  customer: CustomerShort;
}