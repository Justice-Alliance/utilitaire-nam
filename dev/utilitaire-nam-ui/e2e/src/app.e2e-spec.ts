import { AppPage } from './app.po';
import { browser, logging } from 'protractor';

describe('Page principale', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('devrait afficher le nom de l\'application', () => {
    page.aller();
    expect(page.getNomApplication()).toEqual('Utilitaire NAM Application');
  });

  it('devrait afficher un menu valider NAM', () => {
    page.aller();
    expect(page.getMenuValider()).toEqual('Valider');
  });

  it('devrait afficher un menu générer NAM', () => {
    page.aller();
    expect(page.getMenuGenerer()).toEqual('Générer NAM');
  });

  it('devrait afficher un menu obtenir information sur le NAM', () => {
    page.aller();
    expect(page.getMenuInfo()).toEqual('Obtenir information sur le NAM');
  });
  
  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
