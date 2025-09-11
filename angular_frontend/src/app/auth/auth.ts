import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private apiUrl = 'http://localhost:8060/users/auth'; // 👉 your backend base URL

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { username, password });
  }
  signup(signupData: { username: string; email: string; password: string }): Observable<any> {
  const payload = {
    username: signupData.username,
    email: signupData.email,
    password: signupData.password,
    roleId: 'user',            // default role
    currentBalance: 0.0          // default balance
  };

  return this.http.post(`http://localhost:8060/users`, payload);
}

}
