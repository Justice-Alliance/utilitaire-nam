using System;

namespace utilitaire_nam
{
    public interface IFabriquePersonne
    {
        Personne Creer(string nom, string prenom, DateTime dateNaissance, bool estUneFemme);
    }
}
