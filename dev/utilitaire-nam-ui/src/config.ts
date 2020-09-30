import {InjectionToken } from '@angular/core';
export interface EnvironmentConfig {
    [key: string]: any;
  }
  
  export const ENVIRONMENT_CONFIG = new InjectionToken<EnvironmentConfig>('ENVIRONMENT_CONFIG');