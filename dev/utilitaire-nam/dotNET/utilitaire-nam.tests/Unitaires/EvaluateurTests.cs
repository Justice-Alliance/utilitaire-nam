using System;
using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;

namespace utilitaire_nam.tests.Unitaires
{
    [TestFixture]
    public class EvaluateurTests
    {
        private Evaluateur _evaluateur;

        [SetUp]
        public void SetUp()
        {
            _evaluateur = new Evaluateur();
        }

        public class Evaluer : EvaluateurTests
        {
            [Test]
            public void SiValeurA_AlorsRetourner193()
            {
                // Arranger
                char valeur = 'A';
                int valeurAttendu = 193;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurB_AlorsRetourner194()
            {
                // Arranger
                char valeur = 'B';
                int valeurAttendu = 194;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurC_AlorsRetourner195()
            {
                // Arranger
                char valeur = 'C';
                int valeurAttendu = 195;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurD_AlorsRetourner196()
            {
                // Arranger
                char valeur = 'D';
                int valeurAttendu = 196;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurE_AlorsRetourner197()
            {
                // Arranger
                char valeur = 'E';
                int valeurAttendu = 197;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurF_AlorsRetourner198()
            {
                // Arranger
                char valeur = 'F';
                int valeurAttendu = 198;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurG_AlorsRetourner199()
            {
                // Arranger
                char valeur = 'G';
                int valeurAttendu = 199;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurH_AlorsRetourner200()
            {
                // Arranger
                char valeur = 'H';
                int valeurAttendu = 200;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurI_AlorsRetourner201()
            {
                // Arranger
                char valeur = 'I';
                int valeurAttendu = 201;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurJ_AlorsRetourner209()
            {
                // Arranger
                char valeur = 'J';
                int valeurAttendu = 209;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurK_AlorsRetourner210()
            {
                // Arranger
                char valeur = 'K';
                int valeurAttendu = 210;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurL_AlorsRetourner211()
            {
                // Arranger
                char valeur = 'L';
                int valeurAttendu = 211;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurM_AlorsRetourner212()
            {
                // Arranger
                char valeur = 'M';
                int valeurAttendu = 212;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurN_AlorsRetourner213()
            {
                // Arranger
                char valeur = 'N';
                int valeurAttendu = 213;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurO_AlorsRetourner214()
            {
                // Arranger
                char valeur = 'O';
                int valeurAttendu = 214;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurP_AlorsRetourner215()
            {
                // Arranger
                char valeur = 'P';
                int valeurAttendu = 215;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurQ_AlorsRetourner216()
            {
                // Arranger
                char valeur = 'Q';
                int valeurAttendu = 216;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurR_AlorsRetourner217()
            {
                // Arranger
                char valeur = 'R';
                int valeurAttendu = 217;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurS_AlorsRetourner226()
            {
                // Arranger
                char valeur = 'S';
                int valeurAttendu = 226;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurT_AlorsRetourner227()
            {
                // Arranger
                char valeur = 'T';
                int valeurAttendu = 227;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurU_AlorsRetourner228()
            {
                // Arranger
                char valeur = 'U';
                int valeurAttendu = 228;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurV_AlorsRetourner229()
            {
                // Arranger
                char valeur = 'V';
                int valeurAttendu = 229;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurW_AlorsRetourner230()
            {
                // Arranger
                char valeur = 'W';
                int valeurAttendu = 230;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurX_AlorsRetourner231()
            {
                // Arranger
                char valeur = 'X';
                int valeurAttendu = 231;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurY_AlorsRetourner232()
            {
                // Arranger
                char valeur = 'Y';
                int valeurAttendu = 232;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeurZ_AlorsRetourner233()
            {
                // Arranger
                char valeur = 'Z';
                int valeurAttendu = 233;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur0_AlorsRetourner240()
            {
                // Arranger
                char valeur = '0';
                int valeurAttendu = 240;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur1_AlorsRetourner241()
            {
                // Arranger
                char valeur = '1';
                int valeurAttendu = 241;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur2_AlorsRetourner242()
            {
                // Arranger
                char valeur = '2';
                int valeurAttendu = 242;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur3_AlorsRetourner243()
            {
                // Arranger
                char valeur = '3';
                int valeurAttendu = 243;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur4_AlorsRetourner244()
            {
                // Arranger
                char valeur = '4';
                int valeurAttendu = 244;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur5_AlorsRetourner245()
            {
                // Arranger
                char valeur = '5';
                int valeurAttendu = 245;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur6_AlorsRetourner246()
            {
                // Arranger
                char valeur = '6';
                int valeurAttendu = 246;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur7_AlorsRetourner247()
            {
                // Arranger
                char valeur = '7';
                int valeurAttendu = 247;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur8_AlorsRetourner248()
            {
                // Arranger
                char valeur = '8';
                int valeurAttendu = 248;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }

            [Test]
            public void SiValeur_AlorsRetourner()
            {
                // Arranger
                char valeur = '9';
                int valeurAttendu = 249;

                // Agir
                var resultat = _evaluateur.Evaluer(valeur);

                // Assurer
                resultat.Should().Be(valeurAttendu);
            }


            [Test]
            public void SiValeurInexistante_AlorsLancerKeyNotFoundException()
            {
                // Arranger
                char valeurInexistante = '!';

                // Agir
                Action action = () => _evaluateur.Evaluer(valeurInexistante);

                // Assurer
                action.Should().Throw<KeyNotFoundException>();
            }
        }
    }
}
