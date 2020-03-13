using System;
using System.Collections.Generic;

namespace utilitaire_nam
{
    public class UtilitaireNam : IUtilitaireNam
    {
        private readonly IGenerateurNam _generateurNam;
        private readonly IValidateur _validateur;

        public UtilitaireNam(IGenerateurNam generateurNam, IValidateur validateur)
        {
            _generateurNam = generateurNam;
            _validateur = validateur;
        }

        public IEnumerable<string> GenereNam(string nom, string prenom, DateTime dateNaissance, bool estUneFemme)
        {
            return _generateurNam.Generer(nom, prenom, dateNaissance, estUneFemme);
        }

        public bool ValiderNam(string nam)
        {
            return _validateur.ValiderNam(nam);
        }
    }
}
