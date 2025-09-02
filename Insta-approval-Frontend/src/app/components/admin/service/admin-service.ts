import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {

  private baseUrl = 'http://localhost:8082/admin/loans';
  private token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWxhamltZWRpc2V0dGk5OUBnbWFpbC5jb20iLCJ1c2VySWQiOjMsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTc1NjgxMDc2NywiZXhwIjoxNzU2ODk3MTY3fQ.CBg-1Yabizv2oxs_YwiUStMm4LDFXDfoymcBmZb8aaY'; 

  constructor(private http: HttpClient) {}

  approveLoan(loanId: number, remarks: string): Observable<any> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${this.token}` });
    const body = { remarks }; 
    return this.http.post(`${this.baseUrl}/${loanId}/approve`, body, { headers });
  }

  rejectLoan(loanId: number, remarks: string): Observable<any> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${this.token}` });
    const body = { remarks };
    return this.http.post(`${this.baseUrl}/${loanId}/reject`, body, { headers });
  }

  getPendingLoans(): Observable<any[]> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${this.token}` });
    return this.http.get<any[]>(`${this.baseUrl}/pending`, { headers });
  }
}
