import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PoRetrive } from '../models';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-list-order',
  templateUrl: './list-order.component.html',
  styleUrls: ['./list-order.component.css']
})
export class ListOrderComponent implements OnInit {

  poList: PoRetrive[] = []

  constructor(private router: Router, private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.getAllPo()
      .then(data => {
        data.forEach(element => {
          this.poList.push(element as PoRetrive)
        })
      })
  }

  placeOrder() {
    this.router.navigate(['/order'])
  }

}
