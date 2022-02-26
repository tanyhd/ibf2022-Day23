import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators } from '@angular/forms';
import { LineItem, Po } from '../models';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-process-order',
  templateUrl: './process-order.component.html',
  styleUrls: ['./process-order.component.css']
})
export class ProcessOrderComponent implements OnInit {

  form!: FormGroup
  lineItemFormArray!: FormArray

  listOfLineItem: LineItem[] = []

  constructor(private fb: FormBuilder, private orderService: OrderService) { }

  ngOnInit(): void {
    this.lineItemFormArray = this.fb.array([])

    this.form = this.fb.group({
      name: this.fb.control("", [Validators.required, Validators.minLength(3)]),
      email: this.fb.control("", [Validators.email, Validators.required]),
      listOfItems: this.lineItemFormArray
    })
  }

  addInput() {
    const lineItemGroup = this.fb.group({
      name: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      quantity: this.fb.control('', [Validators.required]),
      price: this.fb.control('', Validators.required)
    })

    this.lineItemFormArray.push(lineItemGroup)
  }

  placeOrder() {
    for (let i =0; i < this.lineItemFormArray.length; i++) {
      let item = new LineItem(this.lineItemFormArray.value[i].name, this.lineItemFormArray.value[i].quantity.toString(), this.lineItemFormArray.value[i].price.toString())
      this.listOfLineItem.push(item)
    }
    let po = new Po(this.form.value.name, this.form.value.email, this.listOfLineItem)
    this.orderService.placeOrder(po)
  }

  deleteLineItem(itemNumber: number) {
    this.lineItemFormArray.removeAt(itemNumber)
  }

}
