import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatCardModule } from "@angular/material/card";
import { FormsModule } from '@angular/forms';
import { MatInputModule } from "@angular/material/input";
import { CommonModule } from '@angular/common';
import { MatIconModule } from "@angular/material/icon";

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  imports: [MatCardModule, FormsModule, MatInputModule, CommonModule, MatIconModule]
})
export class AdminLogin {
hide: any;
  constructor(private readonly http: HttpClient, private readonly router: Router) {}

 onSubmit(formValue: { email: string; password: string }) {
  this.http.post<{ token: string }>(
    'http://localhost:8081/api/v1/customers/login',
    { email: formValue.email, password: formValue.password }
  ).subscribe({
     next: (response) => {
        
        localStorage.setItem('token', response.token);
        console.log('Login successful, token saved.');
      
      this.router.navigate(['/view-applications']);
    },
    error: (err) => {
      console.error('❌ Login failed:', err);
      alert('Invalid credentials or server error');
    }
  });
}
}