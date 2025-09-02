import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from "@angular/material/card";
import { MatInputModule } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatButtonModule } from "@angular/material/button";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatNativeDateModule } from "@angular/material/core";
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registration',
  standalone: true,
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
  imports: [
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    CommonModule
  ]
})
export class Register implements OnInit {

  registrationForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      address: ['', Validators.required],
      dateOfBirth: ['', [Validators.required, this.minAgeValidator(18)]],
      cibilScore: ['', [Validators.required, Validators.min(300), Validators.max(900)]]
    });
  }

  // ✅ Age validation
  minAgeValidator(minAge: number) {
    return (control: any) => {
      if (!control.value) return null;
      const dob = new Date(control.value);
      const today = new Date();
      let age = today.getFullYear() - dob.getFullYear();
      const month = today.getMonth() - dob.getMonth();
      if (month < 0 || (month === 0 && today.getDate() < dob.getDate())) {
        age--;
      }
      return age >= minAge ? null : { minAge: true };
    };
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  onSubmit(): void {
    if (this.registrationForm.valid) {
      let formValue = { ...this.registrationForm.value };

      // ✅ Format DOB as yyyy-MM-dd
      if (formValue.dateOfBirth) {
        const dob = new Date(formValue.dateOfBirth);
        const year = dob.getFullYear();
        const month = String(dob.getMonth() + 1).padStart(2, '0');
        const day = String(dob.getDate()).padStart(2, '0');
        formValue.dateOfBirth = `${year}-${month}-${day}`;
      }

      // ✅ Ensure CIBIL is number
      formValue.cibilScore = Number(formValue.cibilScore);

      console.log('Form value before POST (transformed):', formValue);

      this.http.post('http://localhost:8081/api/v1/customers/register', formValue)
        .subscribe({
          next: (res) => {
            console.log('✅ Registration successful', res);
            alert('Registration Successful!');
            this.router.navigate(['/login']);
          },
          error: (err) => {
            console.error('❌ Registration failed', err);
            if (err.error?.message?.includes("Email already registered")) {
              alert("⚠️ This email is already registered. Please try another one.");
            } else {
              alert("Registration Failed! Please check your inputs.");
            }
          }
        });
    }
  }
}
