import { GenererNamPage } from './generer-nam.po';

describe('Page Gén. du NAM', () => {
  let pageGenererNAM: GenererNamPage;

  beforeEach(() => {
    pageGenererNAM = new GenererNamPage();
  });

  it('devrait afficher la liste des NAMs dans une alerte de type succés si les informations saisies sont bonnes', () => {
    pageGenererNAM.aller();
    pageGenererNAM.getNomForm().sendKeys('Alain');
    pageGenererNAM.getPrenomForm().sendKeys('Dupont');
    pageGenererNAM.getDateNaissanceFormCalendrier().sendKeys('2020-10-10');
    pageGenererNAM.getSexeTypeFormListe().then((options)=>{
      options[1].click();
    });
    pageGenererNAM.getBoutonGenererNAM().click();

    expect(pageGenererNAM.getAlerteValidationSuccess().isDisplayed()).toBeTruthy();
  });

  it('devrait afficher une alerte erreur si on ne saisi pas la date de naissance', () => {
    pageGenererNAM.aller();
    pageGenererNAM.getNomForm().sendKeys('Alain');
    pageGenererNAM.getPrenomForm().sendKeys('Dupont');
    pageGenererNAM.getDateNaissanceFormCalendrier().sendKeys('');
    pageGenererNAM.getSexeTypeFormListe().then((options)=>{
      options[1].click();
    });
    pageGenererNAM.getBoutonGenererNAM().click();

    expect(pageGenererNAM.getAlerteValidationErreur().isDisplayed()).toBeTruthy();
  });

  it('devrait afficher une alerte erreur si on ne saisi pas le sexe', () => {
    pageGenererNAM.aller();
    pageGenererNAM.getNomForm().sendKeys('Alain');
    pageGenererNAM.getPrenomForm().sendKeys('Dupont');
    pageGenererNAM.getDateNaissanceFormCalendrier().sendKeys('2020-10-10');
    pageGenererNAM.getBoutonGenererNAM().click();
    expect(pageGenererNAM.getAlerteValidationErreur().isDisplayed()).toBeTruthy();
  });
});
