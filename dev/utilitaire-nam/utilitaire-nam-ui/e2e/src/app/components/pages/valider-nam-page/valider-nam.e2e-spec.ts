import { ValiderNamPage } from './valider-nam.po';

describe('Page Validation du NAM', () => {
  let pageValidationNAM: ValiderNamPage;

  beforeEach(() => {
    pageValidationNAM = new ValiderNamPage();
  });
  
  it('devrait activer le button "valider" si on a saisie le bon texte dans le champs NAM', () => {
    pageValidationNAM.aller();
    pageValidationNAM.getNAMForm().sendKeys('GAUA97612726');
    expect(pageValidationNAM.getBoutonValider().isEnabled()).toBeTruthy();
  });

  it('devrait désactiver le button "valider" si le champs NAM est vide', () => {
    pageValidationNAM.aller();
    pageValidationNAM.getNAMForm().sendKeys('');
    expect(pageValidationNAM.getBoutonValider().isEnabled()).toBeFalsy();
  });

/*  it('devrait séléctionner le bouton valider si on click dessus puis afficher une alerte si on valide un NAM', () => {
    pageValidationNAM.aller();
    pageValidationNAM.getNAMForm().sendKeys('GAUA97612726');
    pageValidationNAM.getBoutonValider().click();
    expect(pageValidationNAM.getBoutonValider().isSelected).toBeTruthy();
    expect(pageValidationNAM.getAlerteValidationSuccess().isDisplayed() || pageValidationNAM.getAlerteValidationErreur().isDisplayed()).toBeTruthy();
  }); */


});
