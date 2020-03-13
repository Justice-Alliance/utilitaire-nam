using System;
using FluentAssertions;
using NUnit.Framework;

namespace utilitaire_nam.tests.Unitaires
{
    [TestFixture]
    public class FemmeTests
    {
        public class PrefixeDecomposition : FemmeTests
        {
            [Test]
            public void DevraitRetournerPrefixeDecomposition()
            {
                // Arranger
                string nom = "Côté";
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nom, prenom, dateNaissance);

                var decompositionAttendu = "COTE1975F1105";

                // Agir
                var decomposition = femme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleMoinsDeTroisCaractere_AlorsCompleterAvecX([Values("L", "Lu")] string nomEnvoye, [Values("LXX", "LUX")] string prefixeNom)
            {
                // Arranger
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nomEnvoye, prenom, dateNaissance);

                var decompositionAttendu = prefixeNom + "E1975F1105";

                // Agir
                var decomposition = femme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleCommenceParSaintOuEquivalent_AlorsRemplacerParAbreviation([Values("Saint", "St", "Saints", "Sainte", "Ste", "Saintes")] string debutNom)
            {
                // Arranger
                string nom = debutNom + "-Nicolas";
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nom, prenom, dateNaissance);

                var decompositionAttendu = "STNE1975F1105";

                // Agir
                var decomposition = femme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleComporteCaractereSpeciaux_AlorsRetirerCaractere([Values(" ", "-")] string caractere)
            {
                // Arranger
                // Arranger
                string nom = "L" + caractere + "Normand";
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nom, prenom, dateNaissance);

                var decompositionAttendu = "LNOE1975F1105";

                // Agir
                var decomposition = femme.PrefixeDecomposition();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }
        }

        public class PrefixeNam : FemmeTests
        {
            [Test]
            public void DevraitRetournerPrefixeNam()
            {
                // Arranger
                string nom = "Côté";
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nom, prenom, dateNaissance);

                var prefixeNamAttendu = "COTE756105";

                // Agir
                var prefixeNam = femme.PrefixeNam();

                // Assurer
                prefixeNam.Should().BeEquivalentTo(prefixeNamAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleMoinsDeTroisCaractere_AlorsCompleterAvecX([Values("L", "Lu")] string nomEnvoye, [Values("LXX", "LUX")] string prefixeNom)
            {
                // Arranger
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nomEnvoye, prenom, dateNaissance);

                var decompositionAttendu = prefixeNom + "E756105";

                // Agir
                var decomposition = femme.PrefixeNam();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleCommenceParSaintOuEquivalent_AlorsRemplacerParAbreviation([Values("Saint", "St", "Saints", "Sainte", "Ste", "Saintes")] string debutNom)
            {
                // Arranger
                string nom = debutNom + "-Nicolas";
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nom, prenom, dateNaissance);

                var decompositionAttendu = "STNE756105";

                // Agir
                var decomposition = femme.PrefixeNam();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }

            [Test, Sequential]
            public void SiNomFamilleComporteCaractereSpeciaux_AlorsRetirerCaractere([Values(" ", "-")] string caractere)
            {
                // Arranger
                // Arranger
                string nom = "L" + caractere + "Normand";
                string prenom = "Émilie";
                DateTime dateNaissance = new DateTime(1975, 11, 5);

                var femme = new Femme(nom, prenom, dateNaissance);

                var decompositionAttendu = "LNOE756105";

                // Agir
                var decomposition = femme.PrefixeNam();

                // Assurer
                decomposition.Should().BeEquivalentTo(decompositionAttendu);
            }
        }
    }
}
