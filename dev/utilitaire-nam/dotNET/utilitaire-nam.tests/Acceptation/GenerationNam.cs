using System;
using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;

namespace utilitaire_nam.tests.Acceptation
{
    [TestFixture]
    public class GenerationNam
    {
        private IFabriquePersonne _fabriquePersonne;
        private IEvaluateur _evaluateur;
        private ICalculatriceChiffrevalidateur _validateur;
        private GenerateurNam _generateur;

        [SetUp]
        public void SetUp()
        {
            _fabriquePersonne = new FabriquePersonne();
            _evaluateur = new Evaluateur();
            _validateur = new CalculatriceChiffrevalidateur(_evaluateur);

            _generateur = new GenerateurNam(_fabriquePersonne, _validateur);
        }

        [Test]
        public void DevraitGenererNamPourHomme()
        {
            // Arranger
            string nom = "Côté";
            string prenom = "Sébastien";
            DateTime dateNaissance = new DateTime(1980, 5, 10);
            bool estUneFemme = false;

            var namAttendu = new List<string>
            {
                "COTS80051012",
                "COTS80051023",
                "COTS80051034",
                "COTS80051045",
                "COTS80051056",
                "COTS80051067",
                "COTS80051078",
                "COTS80051089",
                "COTS80051090",
                "COTS800510A4",
                "COTS800510B5",
                "COTS800510C6",
                "COTS800510D7",
                "COTS800510E8",
                "COTS800510F9",
                "COTS800510G0",
                "COTS800510H1",
                "COTS800510J0",
                "COTS800510K1",
                "COTS800510L2",
                "COTS800510M3",
                "COTS800510N4",
                "COTS800510P6",
                "COTS800510Q7",
                "COTS800510R8",
                "COTS800510S7",
                "COTS800510T8",
                "COTS800510U9",
                "COTS800510V0",
                "COTS800510W1",
                "COTS800510X2",
                "COTS800510Y3",
                "COTS800510Z4"
            };

            // Agir
            var resultat = _generateur.Generer(nom, prenom, dateNaissance, estUneFemme);

            // Assurer
            resultat.Should().BeEquivalentTo(namAttendu);
        }

        [Test]
        public void DevraitGenererNamPourFemme()
        {
            // Arranger
            string nom = "Séraphin";
            string prenom = "Élise";
            DateTime dateNaissance = new DateTime(1975, 11, 30);
            bool estUneFemme = true;

            var namAttendu = new List<string>
            {
                "SERE75613018",
                "SERE75613029",
                "SERE75613030",
                "SERE75613041",
                "SERE75613052",
                "SERE75613063",
                "SERE75613074",
                "SERE75613085",
                "SERE75613096",
                "SERE756130A0",
                "SERE756130B1",
                "SERE756130C2",
                "SERE756130D3",
                "SERE756130E4",
                "SERE756130F5",
                "SERE756130G6",
                "SERE756130H7",
                "SERE756130J6",
                "SERE756130K7",
                "SERE756130L8",
                "SERE756130M9",
                "SERE756130N0",
                "SERE756130P2",
                "SERE756130Q3",
                "SERE756130R4",
                "SERE756130S3",
                "SERE756130T4",
                "SERE756130U5",
                "SERE756130V6",
                "SERE756130W7",
                "SERE756130X8",
                "SERE756130Y9",
                "SERE756130Z0"
            };

            // Agir
            var resultat = _generateur.Generer(nom, prenom, dateNaissance, estUneFemme);

            // Assurer
            resultat.Should().BeEquivalentTo(namAttendu);
        }
    }
}
