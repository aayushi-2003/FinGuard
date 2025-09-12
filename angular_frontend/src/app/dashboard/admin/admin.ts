import { Component, OnInit } from '@angular/core';
import { Auth } from '../../auth/auth'; 
@Component({
  selector: 'app-admin',
  standalone: false,
  templateUrl: './admin.html',
  styleUrl: './admin.css'
})
export class Admin implements OnInit {
  users: any[] = [];

  constructor(private auth: Auth) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.auth.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error('Failed to load users', err);
      }
    });
  }

  deleteUser(userId: number): void {
    const confirmed = confirm('Are you sure you want to delete this user?');
    if (confirmed) {
      this.auth.deleteUser(userId).subscribe({
        next: () => {
          alert('User deleted successfully');
          this.loadUsers(); // refresh list
        },
        error: (err) => {
          console.error('Failed to delete user', err);
        }
      });
    }
  }
}