#language: fr
Fonctionnalité: générer une liste de numéros d'assurance maladie valides à partir des informations fournies

		Je veux pouvoir générer une liste de numéros d'assurance maladie valides
		À partir du nom, du prénom, de la date de naissance et du sexe d'un usager
		
		Toutes les informations doivent être fournies pour pouvoir générer une liste de numéros d'assurance maladie
	
	Scénario: générer une liste de numéros d'assurance maladie valides
	
		Quand je demande de générer une liste de numéros d'assurance maladie pour "Martin" "Tremblay", né le "2000-01-18", de sexe "masculin"
		Alors je reçois la liste de numéros d'assurance maladie suivante:
			| TREM00011811 |
			| TREM00011822 |
			| TREM00011833 |
			| TREM00011844 |
			| TREM00011855 |
			| TREM00011866 | 
			| TREM00011877 |
			| TREM00011888 |
			| TREM00011899 |
			
	Scénario: avertir que les informations fournies pour la génération de numéros d'assurance maladie ne sont pas valides
	
		Quand je demande de générer une liste de numéros d'assurance maladie en fournissant des informations non valides
		Alors je reçois un avertissement que les informations que j'ai fourni ne sont pas valides
