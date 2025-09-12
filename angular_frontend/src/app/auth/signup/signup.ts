import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../auth';

@Component({
  selector: 'app-signup',
  standalone: false,
  templateUrl: './signup.html',
  styleUrl: './signup.css'
})
export class Signup {
  signupData = {
    username: '',
    email: '',
    password: ''
  };

  errorMessage = '';

  constructor(private auth: Auth, private router: Router) {}

  onSignup() {
    this.auth.signup(this.signupData).subscribe({
      next: (response) => {
        console.log('Signup success:', response);
        alert('Signup successful! Please login.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Signup failed:', err);
        this.errorMessage = 'Signup failed. Try again.';
      }
    });
  }

}
