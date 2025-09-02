import { Routes } from '@angular/router';
import { Register } from './components/customer/register/register/register';
import { Login } from './components/customer/login/login';
import { Menu } from './components/customer/menu/menu';
import { Dashboard } from './components/customer/dashboard/dashboard';
import { LoanApplication } from './components/customer/loan-application/loan-application';
import { AdminLogin } from './components/admin/login/login';
import { ViewApplications } from './components/admin/view-applications/view-applications';
import { LoanTracking } from './components/customer/loan-tracking/loan-tracking';

export const routes: Routes = [
    {path:'',component:Menu},
    {path:'customer', component:Register},
    {path:'login', component:Login},
    {path:'dashboard', component:Dashboard},
    {path:'apply-for-loan', component:LoanApplication},
    { path: 'loan-tracking', component: LoanTracking },
    { path: 'loan-tracking/:id', component: LoanTracking },

    {path:'admin', component:AdminLogin},
    {path:'view-applications', component:ViewApplications}


];