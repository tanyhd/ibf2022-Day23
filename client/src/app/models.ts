export class lineItem {
  constructor (
    public name: string,
    public quantity: number,
    public price: number)
    {}
}


export class Po {
  constructor(
    public name: string,
    public email: string,
    public listOfItems: lineItem[]
  )
  {}
}
