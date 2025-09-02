import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLogin } from './login';
import { By } from '@angular/platform-browser';
import { provideZonelessChangeDetection } from '@angular/core';

describe('AdminLogin', () => {
  let component: AdminLogin;
  let fixture: ComponentFixture<AdminLogin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminLogin],
      providers: [provideZonelessChangeDetection()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminLogin);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
   it('should check all the components visible', ()=>{

 const h2Element = fixture.nativeElement.querySelector("h2");
 expect(h2Element).toBeTruthy(); //checking the component is present or not
 expect(h2Element.textContent).toContain("Login"); //checking title of the page is equal to "Login"

const emailElement = fixture.debugElement.query(By.css('input'));
const emailPlaceHolder = emailElement.nativeElement.getAttribute('placeholder');
expect(emailPlaceHolder).toBe('Email');

const passwordElement = fixture.debugElement.query(By.css('input[placeholder="Password"]'));
expect(passwordElement).toBeTruthy();
 
const loginButton = fixture.nativeElement.querySelector("button"); //Arrange
expect(loginButton).toBeTruthy(); //checking the component is present or not
expect(loginButton.textContent).toContain("Login"); //checking title of the page is equal to "Login"
 });

 //functionality test case

 it('should enter email and password & click login', ()=>{

  //Arrange
  const emailElement = fixture.debugElement.query(By.css('input[placeholder="Email"]')).nativeElement;
  const passwordElement = fixture.debugElement.query(By.css('input[placeholder="Password"]')).nativeElement;
  const loginButton = fixture.nativeElement.querySelector("button"); //Arrange
  //Action
  emailElement.value="test@gmail.com";
  emailElement.dispatchEvent(new Event('input'));
  passwordElement.value="123456";
  passwordElement.dispatchEvent(new Event('input'));
  spyOn(component, 'onSubmit');

  loginButton.click();
  //Assertion
expect(component.onSubmit).toHaveBeenCalled();
 });
});
