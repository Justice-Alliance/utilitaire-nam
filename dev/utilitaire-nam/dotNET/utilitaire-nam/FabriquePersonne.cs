using System;

namespace utilitaire_nam
{
    public class FabriquePersonne : IFabriquePersonne
    {
        public Personne Creer(string nom, string prenom, DateTime dateNaissance, bool estUneFemme)
        {
            if (estUneFemme)
            {
                return new Femme(nom, prenom, dateNaissance);
            }
            else
            {
                return new Homme(nom, prenom, dateNaissance);
            }
        }
    }
}
