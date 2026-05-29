export interface InstallationWorkRequest {
  name:string,
  description:string,
  status: string,
  startTime: string,
  plannedEndTime:string,
  realEndTime?:string | null,
  workersId:number[],
  predecessorIds:number[],
  objectId:number
}

export const createDefaultWorkRequest = () =>{
    const work: InstallationWorkRequest = {
        name:'',
        description:'',
        status: '',
        startTime: '',
        plannedEndTime:'',
        realEndTime:null,
        workersId:[],
        predecessorIds:[],
        objectId:0
    }
    return work;
}