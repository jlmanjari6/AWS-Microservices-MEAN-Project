import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import Amplify from 'aws-amplify';

if (environment.production) {
  enableProdMode();
}

Amplify.configure({
  Auth: {
    mandatorySignId: true,
    region: "us-east-1",
    userPoolId:"us-east-1_tAUvN2DVv",
    userPoolWebClientId: "58mahng8emhcqosb4pe8bvec13"
  }
});

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

  
