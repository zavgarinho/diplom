export interface SecurityObjectRequest{
  address:string,
  area:number,
  floor:number,
  type:string,
  status:string,
  customerId:number,
  equipmentIds:number[],
  worksId:number[]
}