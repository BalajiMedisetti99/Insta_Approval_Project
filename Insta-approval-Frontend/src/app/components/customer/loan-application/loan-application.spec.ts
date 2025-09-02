import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoanApplication } from './loan-application';

describe('LoanApplication', () => {
  let component: LoanApplication;
  let fixture: ComponentFixture<LoanApplication>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanApplication],
      imports: [ReactiveFormsModule, HttpClientTestingModule]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanApplication);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
});
