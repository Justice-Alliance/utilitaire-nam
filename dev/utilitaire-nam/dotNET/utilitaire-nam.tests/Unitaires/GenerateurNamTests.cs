using System;
using System.Collections.Generic;
using FluentAssertions;
using Moq;
using NUnit.Framework;
using utilitaire_nam.Exceptions;

namespace utilitaire_nam.tests.Unitaires
{
    public class GenerateurNamTests
    {
        private Mock<IFabriquePersonne> _mockFabriquePersonne;
        private Mock<ICalculatriceChiffrevalidateur> _mockCalculatrice;
        private GenerateurNam _generateur;

        [SetUp]
        public void Setup()
        {
            _mockFabriquePersonne = new Mock<IFabriquePersonne>();
            _mockCalculatrice = new Mock<ICalculatriceChiffrevalidateur>();
            _generateur = new GenerateurNam(_mockFabriquePersonne.Object, _mockCalculatrice.Object);
        }

        public class Generer : GenerateurNamTests
        {
            [Test, Sequential]
            public void SiNomVide_AlorsLancerNomInvalideException([Values("", " ", null)]string nom)
            {
                // Arranger
                var prenom = "prenom";
                var dateNaissance = new DateTime(1975,5,5);
                bool estUneFemme = false;

                // Agir
                Action action = () => _generateur.Generer(nom, prenom, dateNaissance, estUneFemme);

                // Assurer
                action.Should().Throw<NomInvalideException>();
            }

            [Test, Sequential]
            public void SiPrnomVide_AlorsLancerNomInvalideException([Values("", " ", null)]string prenom)
            {
                // Arranger
                var nom = "nom";
                var dateNaissance = new DateTime(1975, 5, 5);
                bool estUneFemme = false;

                // Agir
                Action action = () => _generateur.Generer(nom, prenom, dateNaissance, estUneFemme);

                // Assurer
                action.Should().Throw<PrenomInvalideException>();
            }

            [Test]
            public void DevraitGenererNam()
            {
                // Arranger
                string nom = "Côté";
                string prenom = "Sébastien";
                DateTime dateNaissance = new DateTime(1980, 5, 10);
                bool estUneFemme = false;

                var personne = new Homme(nom, prenom, dateNaissance);
                _mockFabriquePersonne.Setup(m => m.Creer(nom, prenom, dateNaissance, estUneFemme)).Returns(personne);

                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05101")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05102")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05103")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05104")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05105")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05106")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05107")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05108")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M05109")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510A")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510B")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510C")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510D")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510E")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510F")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510G")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510H")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510J")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510K")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510L")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510M")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510N")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510P")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510Q")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510R")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510S")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510T")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510U")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510V")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510W")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510X")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510Y")).Returns(1);
                _mockCalculatrice.Setup(m => m.CaculerValidateur("COTS1980M0510Z")).Returns(1);

                var namAttendu = new List<string>
                {
                    "COTS80051011",
                    "COTS80051021",
                    "COTS80051031",
                    "COTS80051041",
                    "COTS80051051",
                    "COTS80051061",
                    "COTS80051071",
                    "COTS80051081",
                    "COTS80051091",
                    "COTS800510A1",
                    "COTS800510B1",
                    "COTS800510C1",
                    "COTS800510D1",
                    "COTS800510E1",
                    "COTS800510F1",
                    "COTS800510G1",
                    "COTS800510H1",
                    "COTS800510J1",
                    "COTS800510K1",
                    "COTS800510L1",
                    "COTS800510M1",
                    "COTS800510N1",
                    "COTS800510P1",
                    "COTS800510Q1",
                    "COTS800510R1",
                    "COTS800510S1",
                    "COTS800510T1",
                    "COTS800510U1",
                    "COTS800510V1",
                    "COTS800510W1",
                    "COTS800510X1",
                    "COTS800510Y1",
                    "COTS800510Z1"
                };

                // Agir
                var resultat = _generateur.Generer(nom, prenom, dateNaissance, estUneFemme);

                // Assurer
                _mockFabriquePersonne.Verify(m => m.Creer(nom, prenom, dateNaissance, estUneFemme));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05101"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05102"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05103"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05104"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05105"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05106"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05107"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05108"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M05109"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510A"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510B"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510C"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510D"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510E"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510F"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510G"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510H"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510J"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510K"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510L"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510M"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510N"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510P"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510Q"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510R"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510S"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510T"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510U"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510V"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510W"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510X"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510Y"));
                _mockCalculatrice.Verify(m => m.CaculerValidateur("COTS1980M0510Z"));
                resultat.Should().BeEquivalentTo(namAttendu);
            }
        }
    }
}
