using System.Collections.Generic;

namespace utilitaire_nam
{
    public class Evaluateur : IEvaluateur
    {
        private Dictionary<char, int> _valeur;
        public Evaluateur()
        {
            _valeur = new Dictionary<char, int>();
            _valeur.Add('A', 193);
            _valeur.Add('B', 194);
            _valeur.Add('C', 195);
            _valeur.Add('D', 196);
            _valeur.Add('E', 197);
            _valeur.Add('F', 198);
            _valeur.Add('G', 199);
            _valeur.Add('H', 200);
            _valeur.Add('I', 201);
            _valeur.Add('J', 209);
            _valeur.Add('K', 210);
            _valeur.Add('L', 211);
            _valeur.Add('M', 212);
            _valeur.Add('N', 213);
            _valeur.Add('O', 214);
            _valeur.Add('P', 215);
            _valeur.Add('Q', 216);
            _valeur.Add('R', 217);
            _valeur.Add('S', 226);
            _valeur.Add('T', 227);
            _valeur.Add('U', 228);
            _valeur.Add('V', 229);
            _valeur.Add('W', 230);
            _valeur.Add('X', 231);
            _valeur.Add('Y', 232);
            _valeur.Add('Z', 233);
            _valeur.Add('0', 240);
            _valeur.Add('1', 241);
            _valeur.Add('2', 242);
            _valeur.Add('3', 243);
            _valeur.Add('4', 244);
            _valeur.Add('5', 245);
            _valeur.Add('6', 246);
            _valeur.Add('7', 247);
            _valeur.Add('8', 248);
            _valeur.Add('9', 249);
        }

        public int Evaluer(char valeur)
        {
            return _valeur[valeur];
        }
    }
}
