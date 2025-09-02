import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { App } from './app/app';
import { appConfig } from './app/app.config';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideNativeDateAdapter } from '@angular/material/core';

bootstrapApplication(App, {
  ...appConfig,
  providers: [
    ...appConfig.providers || [],
    importProvidersFrom(HttpClientModule), provideRouter(routes),provideNativeDateAdapter() 
  ]
})
  .catch((err) => console.error(err));
