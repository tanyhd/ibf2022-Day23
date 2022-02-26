import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { lineItem, Po } from '../models';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-process-order',
  templateUrl: './process-order.component.html',
  styleUrls: ['./process-order.component.css']
})
export class ProcessOrderComponent implements OnInit {

  form: FormGroup = new FormGroup({})

  listOfLineItem: lineItem[] = []

  constructor(private fb: FormBuilder, private orderService: OrderService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: this.fb.control(""),
      email: this.fb.control("")
    })
  }

  addItemToList(name: string, quantity: string, price: string) {
    let item = new lineItem(name, +quantity, +price)
    this.listOfLineItem.push(item)
  }

  placeOrder() {
    console.log(this.form.value.name)
    console.log(this.form.value.email)
    console.log(this.listOfLineItem)
    let po = new Po(this.form.value.name, this.form.value.email, this.listOfLineItem)
    console.log(po)
    this.orderService.placeOrder(po)
  }

}
