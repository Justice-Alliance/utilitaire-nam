using System;
using System.Collections.Generic;

namespace utilitaire_nam
{
    public interface IUtilitaireNam
    {
        IEnumerable<string> GenereNam(string nom, string prenom, DateTime dateNaissance, bool estUneFemme);

        bool ValiderNam(string nam);
    }
}
