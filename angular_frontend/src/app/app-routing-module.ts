import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Signup } from './auth/signup/signup';
import { Login} from './auth/login/login';
import { Admin } from './dashboard/admin/admin';
import { User } from './dashboard/user/user';
import { Advisor } from './dashboard/advisor/advisor';
import { UserProfile } from './dashboard/user-profile/user-profile';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'signup', component: Signup },
  { path: 'login', component: Login },
  { path: 'admin', component: Admin },
  { path: 'user', component: User },
  { path: 'advisor', component: Advisor },
  { path: 'profile', component: UserProfile },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
