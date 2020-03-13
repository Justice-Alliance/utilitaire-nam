using System;
using System.Collections.Generic;

namespace utilitaire_nam
{
    public interface IGenerateurNam
    {
        IEnumerable<string> Generer(string nom, string prenom, DateTime dateNaissance, bool estUneFemme);
    }
}
