using System;
using FluentAssertions;
using Moq;
using NUnit.Framework;
using utilitaire_nam.Exceptions;

namespace utilitaire_nam.tests.Unitaires
{
    [TestFixture]
    public class ValidateurTests
    {
        private Mock<ICalculatriceChiffrevalidateur> _mockCalculatrice;
        private Validateur _validateur;

        [SetUp]
        public void SetUp()
        {
            _mockCalculatrice = new Mock<ICalculatriceChiffrevalidateur>();
            _validateur = new Validateur(_mockCalculatrice.Object);
        }

        public class ValiderNam : ValidateurTests
        {
            [Test, Sequential]
            public void SiNamContientEstVide_AlorsRetournerFaux([Values(" ", "", null)] string nam)
            {
                // Arranger

                // Agir
                var resultat = _validateur.ValiderNam(nam);

                // Assurer
                resultat.Should().BeFalse();
            }

            [Test, Sequential]
            public void SiNamContientPas14Caractere_AlorsRetournerFaux([Values("ABCD1234567", "ABCD123456789")] string nam)
            {
                // Arranger

                // Agir
                var resultat = _validateur.ValiderNam(nam);

                // Assurer
                resultat.Should().BeFalse();
            }

            [Test, Sequential]
            public void SiLeFormatDuNamEstInvalide_AlorsRetournerFaux([Values("AAAAA1234567", "AAA123456789", "AAAA123456I8", "AAAA123456O8", "AAAA12-456O8")] string nam)
            {
                // Arranger

                // Agir
                var resultat = _validateur.ValiderNam(nam);

                // Assurer
                resultat.Should().BeFalse();
            }

            [Test, Sequential]
            public void SiDateDeNaissanceInvalide_AlorsRetournerFaux([Values("AAAA02400210", "AAAA02900210", "AAAA02103210", "AAAA02603210")] string nam)
            {
                // Arranger
                
                // Agir
                var resultat = _validateur.ValiderNam(nam);

                // Assurer
                resultat.Should().BeFalse();
            }

            [Test, Sequential]
            public void SiChiffreValidateurCalculerEstIdentique_AlorsRetournerVrai([Values("ABCD89020314", "ABCD89520314")] string nam, [Values('M', 'F')] char sexe)
            {
                // Arranger
                var decompositionAttendu = "ABCD1989" + sexe + "02031";
                int validateur = 4;
                _mockCalculatrice.Setup(m => m.CaculerValidateur(decompositionAttendu))
                    .Returns(validateur);
                
                // Agir
                var resultat = _validateur.ValiderNam(nam);

                // Assurer
                _mockCalculatrice.Verify(m => m.CaculerValidateur(decompositionAttendu));
                
                resultat.Should().BeTrue();
            }

            [Test, Sequential]
            public void SiChiffreValidateurCalculerDifferentDeRecu_AlorsRetournerEssayerAvecPlusDe100Ans([Values("ABCD99020311", "ABCD99520311")] string nam, [Values('M','F')] char sexe)
            {
                // Arranger
                var decompositionTropJeune = "ABCD1999"+ sexe +"02031";
                int mauvaisValidateur = 4;
                _mockCalculatrice.Setup(m => m.CaculerValidateur(decompositionTropJeune))
                    .Returns(mauvaisValidateur);

                var decompositionAttendu = "ABCD1899"+ sexe +"02031";
                int validateurAttendu = 1;
                _mockCalculatrice.Setup(m => m.CaculerValidateur(decompositionAttendu))
                    .Returns(validateurAttendu);

                // Agir
                var resultat = _validateur.ValiderNam(nam);

                // Assurer
                _mockCalculatrice.Verify(m => m.CaculerValidateur(decompositionTropJeune));
                _mockCalculatrice.Verify(m => m.CaculerValidateur(decompositionAttendu));
                resultat.Should().BeTrue();
            }

            [Test, Sequential]
            public void SiChiffreValidateurCalculerDifferentPour100Ans_AlorsRetournerFaux([Values("ABCD99020313", "ABCD99520313")] string nam, [Values('M', 'F')] char sexe)
            {
                // Arranger
                var decompositionTropJeune = "ABCD1999" + sexe + "02031";
                int mauvaisValidateur = 4;
                _mockCalculatrice.Setup(m => m.CaculerValidateur(decompositionTropJeune))
                    .Returns(mauvaisValidateur);

                var decompositionPlusvielle = "ABCD1899" + sexe + "02031";
                int deuxiemeMauvaisValidateur = 1;
                _mockCalculatrice.Setup(m => m.CaculerValidateur(decompositionPlusvielle))
                    .Returns(deuxiemeMauvaisValidateur);

                // Agir
                var resultat = _validateur.ValiderNam(nam);

                // Assurer
                _mockCalculatrice.Verify(m => m.CaculerValidateur(decompositionTropJeune));
                _mockCalculatrice.Verify(m => m.CaculerValidateur(decompositionPlusvielle));
                resultat.Should().BeFalse();
            }
        }
    }
}
