#language: fr
Fonctionnalité: obtenir les informations contenues dans un numéro d'assurance maladie du Québec

		Je veux pouvoir obtenir le sexe et la date de naissance de la personne à qui le numéro d'assurance maladie appartient
		
		On peut seulement utiliser cette fonctionnalité sur un numéro d'assurance maladie du Québec

	Scénario: obtenir les informations contenues dans un numéro d'assurance maladie
	
		Quand je demande d'obtenir les informations contenues dans le numéro d'assurance maladie "TREM04121925"
		Alors je reçois l'information que la date de naissance est "2004-12-19"
		Et je reçois l'information que le sexe est "masculin"
	
	Scénario: avertir l'utilisateur lorsqu'il y a un problème avec le numéro d'assurance maladie qu'il a fourni
		
		Quand je demande d'obtenir les informations contenues dans un numéro d'assurance maladie non valide
		Alors je reçois un avertissement que mon numéro d'assurance maladie n'est pas valide
