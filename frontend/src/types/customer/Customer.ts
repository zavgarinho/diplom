import type { CustomerObject } from "./CustomerObject";

export interface Customer {
  id: number;
  firstName: string;
  lastName: string;
  patronymic: string;
  email: string;
  type: string;
  objects: CustomerObject[];
}