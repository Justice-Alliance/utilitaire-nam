using System;
using FluentAssertions;
using NUnit.Framework;

namespace utilitaire_nam.tests.Unitaires
{
    [TestFixture]
    public class FabriquePersonneTests
    {
        private FabriquePersonne _fabrique;

        [SetUp]
        public void SetUp()
        {
            _fabrique = new FabriquePersonne();
        }

        [Test]
        public void SiPasUneFemme_AlorsRetourneHomme()
        {
            // Arranger
            string nom = "Côté";
            string prenom = "Sébastien";
            DateTime dateNaissance = new DateTime(1975, 5, 9);
            bool estUneFemme = false;

            var resultatAttendu = new Homme(nom, prenom, new DateTime(1975, 5, 9));

            // Agir
            var resultat = _fabrique.Creer(nom, prenom, dateNaissance, estUneFemme);

            // Assurer
            resultat.Should().BeEquivalentTo(resultatAttendu);
        }

        [Test]
        public void SiEstUneFemme_AlorsRetourneFemme()
        {
            // Arranger
            string nom = "Côté";
            string prenom = "Émilie";
            DateTime dateNaissance = new DateTime(1975,5,9);
            bool estUneFemme = true;

            var resultatAttendu = new Femme(nom, prenom, new DateTime(1975, 5, 9));

            // Agir
            var resultat = _fabrique.Creer(nom, prenom, dateNaissance, estUneFemme);

            // Assurer
            resultat.Should().BeEquivalentTo(resultatAttendu);
        }
    }
}
