import { Component } from '@angular/core';

@Component({
  selector: 'app-user',
  standalone: false,
  templateUrl: './user.html',
  styleUrl: './user.css'
})
export class User {
  user = JSON.parse(localStorage.getItem('user') || '{}');
  username = this.user.username || 'Guest';
}
