using System.Collections.Generic;
using utilitaire_nam.Exceptions;

namespace utilitaire_nam
{
    public class CalculatriceChiffrevalidateur : ICalculatriceChiffrevalidateur
    {
        private List<int> MULTIPLICATEUR = new List<int> { 1, 3, 7, 9, 1, 7, 1, 3, 4, 5, 7, 6, 9, 1 };
        private readonly IEvaluateur _evaluateur;

        public CalculatriceChiffrevalidateur(IEvaluateur evaluateur)
        {
            _evaluateur = evaluateur;
        }

        public int CaculerValidateur(string chaine)
        {
            int longueurChaine = chaine.Length;
            if (longueurChaine < 14)
            {
                throw new ChaineTropPetiteException();
            }

            if (longueurChaine > 14)
            {
                throw new ChaineTropLongueException();
            }

            int total = 0;
            int index = 0;
            foreach (var c in chaine)
            {
                total += _evaluateur.Evaluer(c) * MULTIPLICATEUR[index];
                index++;
            }

            int validateur = total % 10;

            return validateur;
        }
    }
}
