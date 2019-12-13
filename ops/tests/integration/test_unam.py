#!/usr/bin/python
# -*- coding: utf-8 -*-import requests
import unittest
import requests
import os

class unamIntegrationTest(unittest.TestCase):
    usagersATester = [
        {
            "nom": "Test",
            "prenom": "Test",
            "dateNaissance": "1999-02-02",
            "nam": "asasdasdasdasdad",
            "sexe": "M",
            "description": "Usager avec NAM invalide"
            },
        {
            "nom": "Einstein",
            "prenom": "Albert",
            "dateNaissance": "1879-03-14",
            "nam": "EINA79031411",
            "sexe": "M",
            "description": "Usager avec NAM valide"
            },
        ]
    unam_base_url = "http://localhost:14101/nam"
    generer_uri = "/generer"
    valider_uri = "/valider"
    info_uri = "/info"
    province = "QC"
    description_sexe = {
        "M": "MASCULIN",
        "F": "FEMININ"
    }
    def setUp(self):
        unittest.TestCase.setUp(self)
        try:
            env_unam_base_url = os.environ['UNAM_BASE_URL']
            self.unam_base_url = env_unam_base_url + "/nam"
        except:
            print("URL de base non spécifié: Utilisation de l'URL par défaut")
              
    def test_generer_nam(self):
        usager = self.usagersATester[1]
        params = {
            "prenom": usager["prenom"],
            "nom": usager["nom"],
            "datenaissance": usager["dateNaissance"],
            "sexe": usager["sexe"]
        }
        rep = requests.get(url=self.unam_base_url+self.generer_uri, params=params)
        listeNam = rep.json()
        namTrouve = False
        for nam in listeNam:
            if nam == usager["nam"]:
                namTrouve = True
                break
        self.assertTrue(namTrouve, "Erreur lors de la génération: le nam " + usager["nam"] + " n'a pas ete retourne par le service")
        
    def test_valider_nam_valide(self):
        usager = self.usagersATester[1]
        params = {
            "nam": usager["nam"],
            "province": self.province
        }
        rep = requests.get(url=self.unam_base_url+self.valider_uri, params=params)
        resultat = rep.json()
        self.assertTrue(resultat, "Erreur lors de la validation: le nam " + usager["nam"] + " n'est pas valide")

    def test_valider_nam_invalide(self):
        usager = self.usagersATester[0]
        params = {
            "nam": usager["nam"],
            "province": self.province
        }
        rep = requests.get(url=self.unam_base_url+self.valider_uri, params=params)
        resultat = rep.json()
        self.assertFalse(resultat, "Erreur lors de la validation: le nam " + usager["nam"] + " est valide")

    def test_info_nam_valide(self):
        usager = self.usagersATester[1]
        params = {
            "nam": usager["nam"]
        }
        rep = requests.get(url=self.unam_base_url+self.info_uri, params=params)
        resultat = rep.json()
        self.assertEqual(resultat["dateNaissance"], usager["dateNaissance"], "Les dates de naissances " + resultat["dateNaissance"] + " et " + usager["dateNaissance"] + " ne correspondent pas")
        self.assertEqual(resultat["sexe"], self.description_sexe[usager["sexe"]], "Les sexes " + resultat["sexe"] + " et " +  self.description_sexe[usager["sexe"]] + " ne correspondent pas")

    def test_info_nam_invalide(self):
        usager = self.usagersATester[0]
        params = {
            "nam": usager["nam"]
        }
        rep = requests.get(url=self.unam_base_url+self.info_uri, params=params)
        resultat = rep.json()
        self.assertEqual(resultat["message"], "Le NAM est invalide", "Erreur obtenir les info: le nam " + usager["nam"] + " est valide")
