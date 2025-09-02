
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdminService } from './admin-service';

describe('AdminService', () => {
  let service: AdminService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AdminService]
    });
    service = TestBed.inject(AdminService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getPendingLoans', () => {
    const dummyLoans = [
      { applicationId: 1, loanAmount: 50000, status: 'PENDING' }
    ];

    service.getPendingLoans().subscribe(loans => {
      expect(loans.length).toBe(1);
      expect(loans[0].status).toBe('PENDING');
    });

    const req = httpMock.expectOne('http://localhost:8082/admin/loans/pending');
    expect(req.request.method).toBe('GET');
    req.flush(dummyLoans);
  });

  it('should approve a loan', () => {
    const loanId = 5;
    const remarks = 'Looks good';

    service.approveLoan(loanId, remarks).subscribe(res => {
      expect(res).toEqual({ message: 'Loan approved' });
    });

    const req = httpMock.expectOne(`http://localhost:8082/admin/loans/${loanId}/approve`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ remarks });
    req.flush({ message: 'Loan approved' });
  });

  it('should reject a loan', () => {
    const loanId = 5;
    const remarks = 'Not eligible';

    service.rejectLoan(loanId, remarks).subscribe(res => {
      expect(res).toEqual({ message: 'Loan rejected' });
    });

    const req = httpMock.expectOne(`http://localhost:8082/admin/loans/${loanId}/reject`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ remarks });
    req.flush({ message: 'Loan rejected' });
  });
});
