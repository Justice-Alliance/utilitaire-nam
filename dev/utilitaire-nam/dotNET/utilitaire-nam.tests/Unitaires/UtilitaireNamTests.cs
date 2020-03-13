using System;
using System.Collections.Generic;
using FluentAssertions;
using Moq;
using NUnit.Framework;

namespace utilitaire_nam.tests.Unitaires
{
    [TestFixture]
    public class UtilitaireNamTests
    {
        private Mock<IGenerateurNam> _mockGenerateurNam;
        private Mock<IValidateur> _mockValidateurNam;
        private UtilitaireNam _utilitaireNam;

        [SetUp]
        public void SetUp()
        {
            _mockGenerateurNam = new Mock<IGenerateurNam>();
            _mockValidateurNam = new Mock<IValidateur>();
            _utilitaireNam = new UtilitaireNam(_mockGenerateurNam.Object, _mockValidateurNam.Object);
        }

        public class GenererNam : UtilitaireNamTests
        {
            [Test]
            public void DevraitAppelerGenererNam()
            {
                // Arranger
                string nom = "nom";
                string prenom = "prenom";
                DateTime dateNaissance = new DateTime(1975,5,12);
                bool estUneFemme = false;

                var resultatRetourne = new List<string> { "NAMU75051212" };
                _mockGenerateurNam.Setup(m => m.Generer(nom, prenom, dateNaissance, estUneFemme))
                    .Returns(resultatRetourne);

                var resultatAttendu = new List<string> {"NAMU75051212"};

                // Agir
                var resultat = _utilitaireNam.GenereNam(nom, prenom, dateNaissance, estUneFemme);

                // Assurer
                _mockGenerateurNam.Verify(m => m.Generer(nom, prenom, dateNaissance, estUneFemme));
                resultat.Should().BeEquivalentTo(resultatAttendu);
            }
        }

        public class ValiderNam : UtilitaireNamTests
        {
            [Test, Sequential]
            public void DevraitAppelerValiderNam([Values(true, false)] bool resultatRetourne)
            {
                // Arranger
                string nam = "NAMU85041211";

                _mockValidateurNam.Setup(m => m.ValiderNam(nam)).Returns(resultatRetourne);

                // Agir
                var resultat = _utilitaireNam.ValiderNam(nam);

                // Assurer
                resultat.Should().Be(resultatRetourne);
                _mockValidateurNam.Verify(m => m.ValiderNam(nam));
            }
        }
    }
}
