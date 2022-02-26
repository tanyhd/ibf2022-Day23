import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Po } from "./models";

@Injectable()
export class OrderService {

  constructor(private http: HttpClient) {}

  placeOrder(po: Po): Promise<Po> {
    return(lastValueFrom(
      this.http.post<Po>("http://localhost:8080/order", po)
    ))

  }
}
