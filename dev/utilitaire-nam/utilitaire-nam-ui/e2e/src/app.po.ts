import { browser, by, element } from 'protractor';

export class AppPage {
  aller() {
    return browser.get('/');
  }

  getNomApplication() {
    return browser.getTitle();
  }

  getMenuValider() {
    return element(by.id('menu-valider')).getText();
  }

  getMenuGenerer() {
    return element(by.id('menu-generer')).getText();
  }

  getMenuInfo() {
    return element(by.id('menu-information')).getText();
  }
 
}
