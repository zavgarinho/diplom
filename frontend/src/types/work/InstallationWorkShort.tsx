export interface InstallationWorkShort{
  id: number,
  name: string,
  description: string,
  status: string,
  startTime: string,
  plannedEndTime:string,
  realEndTime: string,
  predecessorIds: number[]
}