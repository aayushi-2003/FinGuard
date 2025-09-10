import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { App } from './app';
import { Signup } from './auth/signup/signup';
import { Login} from './auth/login/login';
import { Admin } from './dashboard/admin/admin';
import { User } from './dashboard/user/user';
import { Advisor } from './dashboard/advisor/advisor';

import { AppRoutingModule } from './app-routing-module';

@NgModule({
  declarations: [
    App,
    Signup,
    Login,
    Admin,
    User,
    Advisor
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [App]
})
export class AppModule { }
