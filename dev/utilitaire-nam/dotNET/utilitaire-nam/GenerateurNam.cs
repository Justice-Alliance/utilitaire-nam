using System;
using System.Collections.Generic;
using utilitaire_nam.Exceptions;

namespace utilitaire_nam
{
    public class GenerateurNam : IGenerateurNam
    {
        private readonly IFabriquePersonne _fabriquePersonne;
        private readonly ICalculatriceChiffrevalidateur _calculatrice;
        
        private const string DIFFERENCIATEUR = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ";

        public GenerateurNam(IFabriquePersonne fabriquePersonne, ICalculatriceChiffrevalidateur calculatrice)
        {
            _fabriquePersonne = fabriquePersonne;
            _calculatrice = calculatrice;
        }

        public IEnumerable<string> Generer(string nom, string prenom, DateTime dateNaissance, bool estUneFemme)
        {
            if (String.IsNullOrWhiteSpace(nom))
            {
                throw new NomInvalideException();
            }

            if (String.IsNullOrWhiteSpace(prenom))
            {
                throw new PrenomInvalideException();
            }

            var nams = new List<string>();

            var personne = _fabriquePersonne.Creer(nom, prenom, dateNaissance, estUneFemme);

            var prefixeDecomposition = personne.PrefixeDecomposition();
            var prefixeNam = personne.PrefixeNam();

            foreach (var c in DIFFERENCIATEUR)
            {
                var validateur = _calculatrice.CaculerValidateur(prefixeDecomposition + c);
                nams.Add(prefixeNam + c + validateur);
            }

            return nams;
        }
    }
}
