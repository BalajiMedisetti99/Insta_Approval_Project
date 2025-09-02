
export interface ApplyLoan{
  userId:number;
  loanType: string;
  loanAmount: number;
}


export interface LoanApplication {
  applicationId: number;
  userId: number;
  loanType: {
    id: number;
    name: string;
  };
  loanAmount: number;
  applicationDate: string;
  status: string;
  remarks: string;
  cibilSnapshot: number;
}