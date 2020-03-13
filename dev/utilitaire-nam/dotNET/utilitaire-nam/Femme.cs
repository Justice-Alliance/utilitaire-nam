using System;

namespace utilitaire_nam
{
    public class Femme : Personne
    {
        public Femme(string nom, string prenom, DateTime dateNaissance) : base(nom, prenom, dateNaissance)
        {
        }

        public override string PrefixeDecomposition()
        {
            var nomNormalise = RetirerAccent(Nom).ToUpper();
            var prefixeNom = ConstruirePrefixeNom(nomNormalise);
            var prenomNormalise = RetirerAccent(Prenom).ToUpper();

            string annee = DateNaissance.Year.ToString();
            string mois = DateNaissance.Month.ToString("00");
            string jour = DateNaissance.Day.ToString("00");

            var prefixe = prefixeNom +
                          prenomNormalise.Substring(0, 1) +
                          annee +
                          'F' +
                          mois +
                          jour;
            return prefixe;
        }

        public override string PrefixeNam()
        {
            var nomNormalise = RetirerAccent(Nom).ToUpper();
            var prefixeNom = ConstruirePrefixeNom(nomNormalise);
            var prenomNormalise = RetirerAccent(Prenom).ToUpper();

            string annee = DateNaissance.Year.ToString().Substring(2, 2);
            string mois = (DateNaissance.Month + 50).ToString("00");
            string jour = DateNaissance.Day.ToString("00");

            string prefixe = prefixeNom +
                             prenomNormalise.Substring(0, 1) +
                             annee +
                             mois +
                             jour;

            return prefixe;
        }
    }
}
