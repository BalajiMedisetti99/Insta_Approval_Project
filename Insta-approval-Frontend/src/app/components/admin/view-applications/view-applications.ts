import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';  // For *ngFor, *ngIf
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../service/admin-service';
import { LoanApplication } from '../../../types';

@Component({
  selector: 'app-view-applications',
  templateUrl: './view-applications.html',
  styleUrls: ['./view-applications.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule]
})
export class ViewApplications {

  loans: LoanApplication[] = [];
  filteredLoans: { loanType?: { name: string }; [key: string]: any }[] = [];
  filterForm: FormGroup;
  remarksMap: { [key: number]: string } = {};

  constructor(private adminService: AdminService, private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      status: [''],
      minAmount: [''],
      maxAmount: [''],
      minCibil: [''],
      maxCibil: ['']
    });

    this.filterForm.valueChanges.subscribe(() => this.applyFilters());
    this.loadPendingLoans();
  }

  loadPendingLoans() {
    this.adminService.getPendingLoans().subscribe(data => {
      this.loans = data;
      this.filteredLoans = data;
    });
  }

  applyFilters() {
    const { status, minAmount, maxAmount, minCibil, maxCibil } = this.filterForm.value;
    this.filteredLoans = this.loans.filter(loan => {
      return (!status || loan.status === status)
        && (!minAmount || loan.loanAmount >= minAmount)
        && (!maxAmount || loan.loanAmount <= maxAmount)
        && (!minCibil || loan.cibilSnapshot >= minCibil)
        && (!maxCibil || loan.cibilSnapshot <= maxCibil);
    });
  }

  approveLoan(loanId: number) {
    const remarks = this.remarksMap[loanId] || '';
    this.adminService.approveLoan(loanId, remarks).subscribe(() => {
      alert('Loan approved!');
      this.loadPendingLoans();
    });
  }

  rejectLoan(loanId: number) {
    const remarks = this.remarksMap[loanId] || '';
    this.adminService.rejectLoan(loanId, remarks).subscribe(() => {
      alert('Loan rejected!');
      this.loadPendingLoans();
    });
  }
}