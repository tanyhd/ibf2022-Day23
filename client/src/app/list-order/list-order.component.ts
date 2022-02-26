import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-order',
  templateUrl: './list-order.component.html',
  styleUrls: ['./list-order.component.css']
})
export class ListOrderComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  placeOrder() {
    this.router.navigate(['/order'])
  }

}
