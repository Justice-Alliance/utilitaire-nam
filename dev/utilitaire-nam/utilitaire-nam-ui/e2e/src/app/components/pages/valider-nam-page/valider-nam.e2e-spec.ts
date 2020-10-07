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

  it('devrait afficher une alerte de type succés si on valide un bon NAM ', () => {
    pageValidationNAM.aller();
    pageValidationNAM.getNAMForm().sendKeys('GAUA97612726');
    pageValidationNAM.getBoutonValider().click();
    expect(pageValidationNAM.getAlerteValidationSuccess().isDisplayed()).toBeTruthy();
  });

  it('devrait afficher une alerte de type erreur si on valide un mauvais NAM ', () => {
    pageValidationNAM.aller();
    pageValidationNAM.getNAMForm().sendKeys('AAAAAaAa');
    pageValidationNAM.getBoutonValider().click();
    expect(pageValidationNAM.getAlerteValidationErreur().isDisplayed()).toBeTruthy();
  });  
});
