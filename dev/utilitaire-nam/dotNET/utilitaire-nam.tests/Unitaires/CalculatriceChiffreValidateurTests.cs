using System;
using FluentAssertions;
using Moq;
using NUnit.Framework;
using utilitaire_nam.Exceptions;

namespace utilitaire_nam.tests.Unitaires
{
    [TestFixture]
    public class CalculatriceChiffreValidateurTests
    {
        private Mock<IEvaluateur> _mockEvaluateur;
        private CalculatriceChiffrevalidateur _calculatrice;

        [SetUp]
        public void SetUp()
        {
            _mockEvaluateur = new Mock<IEvaluateur>();
            _calculatrice = new CalculatriceChiffrevalidateur(_mockEvaluateur.Object);
        }

        public class CaculerValidateur : CalculatriceChiffreValidateurTests
        {
            [Test]
            public void SiLaChaineEstMoinsQue14Caractere_AlorsLancerChaineTropPetiteException()
            {
                // Arranger
                var chaine = "ABCDEFGHIJKLM";

                // Agir
                Action action = () => _calculatrice.CaculerValidateur(chaine);

                // Assurer
                action.Should().Throw<ChaineTropPetiteException>();
            }

            [Test]
            public void SiLaChaineEstPlusQue14Caractere_AlorsLancerChaineTropLongueException()
            {
                // Arranger
                var chaine = "ABCDEFGHIJKLMNO";

                // Agir
                Action action = () => _calculatrice.CaculerValidateur(chaine);

                // Assurer
                action.Should().Throw<ChaineTropLongueException>();
            }

            [Test]
            public void DevraitCalculerChiffreValidateur()
            {
                // Arranger
                string chaine = "AAAAAAAAAAAAAA";

                _mockEvaluateur.Setup(m => m.Evaluer('A')).Returns(1);

                int validateurAttendu = 4;

                // Agir
                var resultat = _calculatrice.CaculerValidateur(chaine);

                // Assurer
                _mockEvaluateur.Verify(m => m.Evaluer('A'), Times.Exactly(14));
                resultat.Should().Be(validateurAttendu);
            }
        }
    }
}
