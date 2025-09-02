import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone:true,
  imports:[CommonModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class Dashboard {
  userName = "Dashboard"

  constructor(private router: Router) {}

  goToApplyForLoan(): void {
    this.router.navigate(['/apply-for-loan']);
  }

  goToTrackingStatus(): void {
    this.router.navigate(['/loan-tracking']);
  }
}
