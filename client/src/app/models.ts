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

export interface PoRetrive {
  ord_id: number,
  name: string,
  email: string,
  lineItemList: LineItemRetrive[]
  totalCost: number
}

export interface LineItemRetrive {
  item_id: number
  name: string
  quantity: number
  price: number
}
