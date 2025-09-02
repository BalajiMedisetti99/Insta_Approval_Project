import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Loanservice } from '../service/loan-service';
import { Router } from '@angular/router';

type LoanTypeVm = { id: number; name: string };

@Component({
  selector: 'app-loan-application',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './loan-application.html',
  styleUrls: ['./loan-application.css']
})
export class LoanApplication implements OnInit {
  loanApplication: { userId: number; loanTypeId: number | null; loanAmount: number | null } = {
    userId: 0,
    loanTypeId: null,
    loanAmount: null
  };

  loanTypes: LoanTypeVm[] = [];
  message = '';

  constructor(private loanService: Loanservice, private router: Router) {}

  ngOnInit() {
    this.loanService.getAllLoanTypes().subscribe({
      next: (data: any[]) => {
        console.log('Raw loan types:', data);

        
        this.loanTypes = (data || [])
          .map((t: any) => ({
            id: Number(t.id ?? t.loanTypeId ?? t.typeId ?? t.loanTypeID),
            name: String(t.name ?? t.typeName ?? t.loanTypeName ?? t.type ?? t.title ?? 'Unnamed')
          }))
          .filter(t => Number.isFinite(t.id));

        console.log('Normalized loan types:', this.loanTypes);

       
      },
      error: (err) => console.error('Failed to fetch loan types', err)
    });
  }

  onSubmit() {
    const payload = {
      userId: Number(this.loanApplication.userId),
      loanTypeId: Number(this.loanApplication.loanTypeId),
      loanAmount: Number(this.loanApplication.loanAmount)
    };

    if (!payload.loanTypeId) {
      this.message = 'Please select a loan type.';
      return;
    }

    console.log('Submitting application:', payload);

    this.loanService.applyLoan(payload).subscribe({
      next: (response) => {
        console.log('Application saved:', response);
        this.message = 'Application added successfully!'; 
       setTimeout(() => {
          this.router.navigate(['/loan-tracking']);
        }, 1500);
      },
      error: (err) => {
        console.error('Error while saving application:', err);
        this.message = 'Error adding application!';
      }
    });
  }
}