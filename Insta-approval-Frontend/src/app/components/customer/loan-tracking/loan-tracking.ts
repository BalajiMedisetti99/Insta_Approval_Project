import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Loanservice } from '../service/loan-service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-loan-tracking',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './loan-tracking.html',
  styleUrls: ['./loan-tracking.css']
})
export class LoanTracking implements OnInit {
  loanId: number | null = null;
  loanStatus: any = null;
  isLoading = false;
  errorMessage = '';

  constructor(
    private loanService: Loanservice,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loanId = Number(id);
        this.trackLoan();
      }
    });
  }

  trackLoan() {
    if (!this.loanId) {
      this.errorMessage = '⚠ Please enter a Loan ID';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.loanStatus = null;

    this.loanService.getLoanStatus(this.loanId).subscribe({
      next: (res) => {
        console.log(' Loan status response:', res);
        if (res.applicationDate) {
          res.applicationDate = new Date(res.applicationDate);
        }
        this.loanStatus = res;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching loan status:', err);
        this.errorMessage = 'No loan found with this ID or server error.';
        this.isLoading = false;
      }
    });
  }
}