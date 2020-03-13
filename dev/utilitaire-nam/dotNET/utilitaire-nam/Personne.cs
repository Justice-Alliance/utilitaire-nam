using System;
using System.Globalization;
using System.Text;
using System.Text.RegularExpressions;

namespace utilitaire_nam
{
    public abstract class Personne
    {
        public string Nom { get; }
        public string Prenom { get; }
        public DateTime DateNaissance { get; }

        public Personne(string nom, string prenom, DateTime dateNaissance)
        {
            Nom = nom;
            Prenom = prenom;
            DateNaissance = dateNaissance;
        }

        public abstract string PrefixeDecomposition();

        public abstract string PrefixeNam();

        protected string RetirerAccent(string texte)
        {
            var texteNormalise = texte.Normalize(NormalizationForm.FormD);
            var stringBuilder = new StringBuilder();

            foreach (var c in texteNormalise)
            {
                var categorieUnicode = CharUnicodeInfo.GetUnicodeCategory(c);
                if (categorieUnicode != UnicodeCategory.NonSpacingMark)
                {
                    stringBuilder.Append(c);
                }
            }

            return stringBuilder.ToString().Normalize(NormalizationForm.FormC);
        }

        protected string ConstruirePrefixeNom(string nomNormalise)
        {
            string nom = nomNormalise.ToUpper();
            nom = Regex.Replace(nom, "STE-|STE |SAINT-|SAINT |SAINTE-|SAINTE |SAINTS-|SAINTS |SAINTES-|SAINTES ", "ST");
            nom = RetirerCaractereSpeciaux(nom);
            for (int i = nom.Length; i < 3; i++)
            {
                nom = nom + "X";
            }
            return nom.Substring(0, 3);
        }

        protected string RetirerCaractereSpeciaux(string nom)
        {
            var nomModifie = Regex.Replace(nom, "[^A-Z]", "");
            return nomModifie;
        }
    }
}
