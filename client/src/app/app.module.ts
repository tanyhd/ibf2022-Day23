import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProcessOrderComponent } from './process-order/process-order.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrderService } from './order.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';

const  appRoutes: Routes = [
  { path: "", component: ProcessOrderComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    ProcessOrderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [OrderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
