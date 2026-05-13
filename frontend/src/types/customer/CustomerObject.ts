import type { CustomerShort } from "./CustomerShort";

export interface CustomerObject {
  id: number;
  address: string;
  area: number;
  floor: number;
  type: string;
  status: string;
  customer: CustomerShort;
}