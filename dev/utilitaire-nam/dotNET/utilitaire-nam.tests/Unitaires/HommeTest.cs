using System;
using FluentAssertions;
using NUnit.Framework;

namespace utilitaire_nam.tests.Unitaires
{
    [TestFixture]
    public class HommeTest
    {
        [SetUp]
        public void SetUp()
        {

        }

        public class PrefixeDecomposition : HommeTest
        {
            [Test]
            public void DevraitRetournerPrefixeDecomposition()
            {
                // Arranger
                string nom = "Côté";
                string prenom = "Sébastien";
                DateTime dateNaissance = new DateTime(1975,11,5);
                
                var hommme = new Homme(nom, prenom, dateNaissance);

                var decompositionAttendu = "COTS1975M1105";

                // Agir
                var decomposition = hommme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleMoinsDeTroisCaractere_AlorsCompleterAvecX([Values("L", "Lu")] string nomEnvoye, [Values("LXX", "LUX")] string prefixeNom)
            {
                // Arranger
                string prenom = "Émile";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var homme = new Homme(nomEnvoye, prenom, dateNaissance);

                var decompositionAttendu = prefixeNom + "E1975M1105";

                // Agir
                var decomposition = homme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleCommenceParSaintOuEquivalent_AlorsRemplacerParAbreviation([Values("Saint", "St", "Saints", "Sainte", "Ste", "Saintes")] string debutNom)
            {
                // Arranger
                string nom = debutNom + "-Nicolas";
                string prenom = "Émile";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var homme = new Homme(nom, prenom, dateNaissance);

                var decompositionAttendu = "STNE1975M1105";

                // Agir
                var decomposition = homme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleComporteCaractereSpeciaux_AlorsRetirerCaractere([Values(" ", "-")] string caractere)
            {
                // Arranger
                // Arranger
                string nom = "L" + caractere + "Normand";
                string prenom = "Émile";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var homme = new Homme(nom, prenom, dateNaissance);

                var decompositionAttendu = "LNOE1975M1105";

                // Agir
                var decomposition = homme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }
        }

        public class PrefixeNam : HommeTest
        {
            [Test]
            public void DevraitRetournerPrefixeNam()
            {
                // Arranger
                string nom = "Côté";
                string prenom = "Sébastien";
                DateTime dateNaissance = new DateTime(1975, 11, 5);
                
                var hommme = new Homme(nom, prenom, dateNaissance);

                var prefixeNamAttendu = "COTS751105";

                // Agir
                var prefixeNam = hommme.PrefixeNam();

                // Assurer
                prefixeNam.Should().BeEquivalentTo(prefixeNamAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleMoinsDeTroisCaractere_AlorsCompleterAvecX([Values("L", "Lu")] string nomEnvoye, [Values("LXX", "LUX")] string prefixeNom)
            {
                // Arranger
                string prenom = "Émile";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var homme = new Homme(nomEnvoye, prenom, dateNaissance);

                var decompositionAttendu = prefixeNom + "E751105";

                // Agir
                var decomposition = homme.PrefixeNam();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleCommenceParSaintOuEquivalent_AlorsRemplacerParAbreviation([Values("Saint", "St", "Saints", "Sainte", "Ste", "Saintes")] string debutNom)
            {
                // Arranger
                string nom = debutNom + "-Nicolas";
                string prenom = "Émile";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var homme = new Homme(nom, prenom, dateNaissance);

                var decompositionAttendu = "STNE751105";

                // Agir
                var decomposition = homme.PrefixeNam();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleComporteCaractereSpeciaux_AlorsRetirerCaractere([Values(" ", "-")] string caractere)
            {
                // Arranger
                // Arranger
                string nom = "L" + caractere + "Normand";
                string prenom = "Émile";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var homme = new Homme(nom, prenom, dateNaissance);

                var decompositionAttendu = "LNOE751105";

                // Agir
                var decomposition = homme.PrefixeNam();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }
        }
    }
}
