import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Po, PoRetrive } from "./models";

@Injectable()
export class OrderService {

  constructor(private http: HttpClient) {}

  async placeOrder(po: Partial<Po>) {
    return await(lastValueFrom(
      this.http.post<any>("http://localhost:8080/order", JSON.stringify(po))
    ))
  }

  getAllPo() {
    return(lastValueFrom(
      this.http.get<PoRetrive[]>("http://localhost:8080/orderList")
    ))
  }


}
