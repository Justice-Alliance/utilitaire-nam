import { InfoNamPage } from './info-nam.po';

describe('Page Obtenir Information du NAM', () => {
  let pageInformationNAM: InfoNamPage;

  beforeEach(() => {
    pageInformationNAM = new InfoNamPage();
  });

  it('devrait activer le button "obtenir information" si on a saisie le bon texte dans le champs NAM', () => {
    pageInformationNAM.aller();
    pageInformationNAM.getZoneTexteNAM().sendKeys('GAUA97612726');
    expect(pageInformationNAM.getBoutonObtenirInfoNAM().isEnabled()).toBeTruthy();
  });

  it('devrait désactiver le button  "obtenir information" si le champs NAM est vide', () => {
    pageInformationNAM.aller();
    pageInformationNAM.getZoneTexteNAM().sendKeys('');
    expect(pageInformationNAM.getBoutonObtenirInfoNAM().isEnabled()).toBeFalsy();
  });

  /*it('devrait séléctionner le bouton Obtenir info puis afficher une alerte si on click dessus ', () => {
    pageInformationNAM.aller();
    pageInformationNAM.getZoneTexteNAM().sendKeys('GAUA97612726');
    pageInformationNAM.getBoutonObtenirInfoNAM().click();
    expect(pageInformationNAM.getBoutonObtenirInfoNAM().isSelected).toBeTruthy();
    expect(pageInformationNAM.getAlerteValidationSuccess().isDisplayed() || pageInformationNAM.getAlerteValidationErreur().isDisplayed()).toBeTruthy();
  });*/
});
