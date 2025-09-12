import { Component } from '@angular/core';

@Component({
  selector: 'app-user-profile',
  standalone: false,
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css'
})
export class UserProfile {
    user = JSON.parse(localStorage.getItem('user') || '{}');
  username = this.user.username || 'Guest';
  email = this.user.email || 'Not provided';
  userRole = this.user.roleId || 'Not provided';
}
