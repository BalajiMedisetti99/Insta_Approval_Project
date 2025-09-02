import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Dashboard } from './dashboard';
import { RouterTestingModule } from '@angular/router/testing';

describe('Dashboard', () => {
  let component: Dashboard;
  let fixture: ComponentFixture<Dashboard>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Dashboard ],
      imports: [ RouterTestingModule ]
    }).compileComponents();

    fixture = TestBed.createComponent(Dashboard);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to apply-for-loan', () => {
    const navigateSpy = spyOn(router, 'navigate');
    component.goToApplyForLoan();
    expect(navigateSpy).toHaveBeenCalledWith(['/apply-for-loan']);
  });

  it('should navigate to loan-tracking', () => {
    const navigateSpy = spyOn(router, 'navigate');
    component.goToTrackingStatus();
    expect(navigateSpy).toHaveBeenCalledWith(['/loan-tracking']);
  });
});
