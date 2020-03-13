using System;
using System.Text;
using System.Text.RegularExpressions;

namespace utilitaire_nam
{
    public class Validateur : IValidateur
    {
        private ICalculatriceChiffrevalidateur _calculateur;

        public Validateur(ICalculatriceChiffrevalidateur calculateur)
        {
            _calculateur = calculateur;
        }

        public bool ValiderNam(string nam)
        {
            if (String.IsNullOrWhiteSpace(nam))
            {
                return false;
            }

            var namMajuscule = nam.ToUpper();
            var resultatRegexNam = Regex.Match(namMajuscule, "^[A-Z]{4}[0-9]{6}[1-9,A-H,J-N,P-Z][0-9]$");
            if (resultatRegexNam.Length != 12)
            {
                return false;
            }

            string namFormate = resultatRegexNam.Value;

            int anneeDuJour = DateTime.Today.Year;
            int deuxDernierChiffreAnnee = Convert.ToInt32(namFormate.Substring(4, 2));

            // i.e. Supposont que nous sommes en 2020 (année du jour) et que l'année dans le nam est 89
            // 2020 - 89 = 1931
            // (2020 - 89) % 100 = 31
            // 2020 - 31 = 1989
            int annee = anneeDuJour - (anneeDuJour - deuxDernierChiffreAnnee) % 100;
            
            int mois = Convert.ToInt32(namFormate.Substring(6, 2));
            char sexe = 'M';
            if (mois > 50)
            {
                sexe = 'F';
                mois -= 50;
            }
            int jour = Convert.ToInt32(namFormate.Substring(8, 2));
            
            // On confirme que la date est réelle
            try
            {
                new DateTime(Convert.ToInt32(annee), Convert.ToInt32(mois), Convert.ToInt32(jour));
            }
            catch (ArgumentOutOfRangeException ex)
            {
                return false;
            }

            string differenciateur = namFormate.Substring(10, 1);

            var stringBuilder = new StringBuilder();
            stringBuilder.Append(namFormate.Substring(0, 4));
            stringBuilder.Append(annee);
            stringBuilder.Append(sexe);
            stringBuilder.Append(mois.ToString("00"));
            stringBuilder.Append(jour.ToString("00"));
            stringBuilder.Append(differenciateur);
            string decomposition = stringBuilder.ToString();
            var validateurCalcule = _calculateur.CaculerValidateur(decomposition);

            int validateur = Convert.ToInt32(namFormate.Substring(11, 1));
            if (validateur == validateurCalcule)
            {
                return true;
            }
            
            // La personne à peut-être plus de 100 ans
            annee -= 100;
            stringBuilder.Clear();
            stringBuilder.Append(namFormate.Substring(0, 4));
            stringBuilder.Append(annee);
            stringBuilder.Append(sexe);
            stringBuilder.Append(mois.ToString("00"));
            stringBuilder.Append(jour.ToString("00"));
            stringBuilder.Append(differenciateur);
            decomposition = stringBuilder.ToString();
            validateurCalcule = _calculateur.CaculerValidateur(decomposition);
            return validateur == validateurCalcule;
        }
    }
}
