export class LineItem {
  constructor (
    public name: string,
    public quantity: string,
    public price: string)
    {}
}


export class Po {
  constructor(
    public name: string,
    public email: string,
    public listOfItems: LineItem[]
  )
  {}
}
