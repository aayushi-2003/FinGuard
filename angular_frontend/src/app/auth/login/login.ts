import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../auth';

@Component({
  selector: 'app-login',
    standalone: false,
  templateUrl: './login.html',
    styleUrl: './login.css'

})
export class Login {
  username: string = '';
  password: string = '';
errorMessage: string = '';

  constructor(private auth: Auth, private router: Router) {}

  onLogin() {
    this.auth.login(this.username, this.password).subscribe({
      next: (response) => {
        console.log('Login response:', response);

      // Store user in local storage (optional)
      localStorage.setItem('user', JSON.stringify(response));

      // Redirect based on roleId
      if (response.roleId.toLowerCase() === 'admin') {
        this.router.navigate(['/admin']);
      } else if (response.roleId.toLowerCase() === 'advisor') {
        this.router.navigate(['/advisor']);
      } else {
        this.router.navigate(['/user']);
      }
    },
    error: (err) => {
      console.error('Login error:', err);
      this.errorMessage = 'Invalid username or password';
    }
  });
  }
}
