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

#  Plan du scénario: Generer un NAM a partir des informations de l'usager
#    Etant donné le prénom "<prenom>" et le nom "<nom>"
#    Et la date de naissance "<dateNaissance>" et le sexe "<sexe>"
#    Quand je clique sur Generer
#    Alors je dois afficher une liste <NAM> valide pour cet usager

#    Exemples: 
#      | prenom  | nom      | dateNaissance | sexe | NAM                                                                                                                          |
#      | Marting | Tremblay | 2000-01-18    | M    | TREM00011811, TREM00011822, TREM00011833, TREM00011844, TREM00011855, TREM00011866, TREM00011877, TREM00011888, TREM00011899 |

#  Scénario: Onglet Generer un NAM Valider que le bon message d'erreur est renvoyé
#    Étant donné le champ prenom est vide et le champ nom est vide
#    Et le champ dateNaissance est vide et le champ sexe est vide
#    Quand je clique sur Generer NAM
#    Alors je dois afficher un message pour l'utilisateur
