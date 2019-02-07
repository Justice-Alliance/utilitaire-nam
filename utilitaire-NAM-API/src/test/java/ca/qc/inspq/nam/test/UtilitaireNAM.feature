Feature: Creation de cas de testes Cucumber pour utilitaire-nam

 Scenario Outline: Valider un NAM pour une province donnee
	Given Etant donne une "<province>" 
	And Un "<nam>" fournie qui est valide 
	When Quand je clique sur le bouton valider  
	Then Je dois voir afficher "<reponse>"
	
	Examples:
	| province | nam 					| reponse |
	| QC	| TREM04121925	|	Le NAM est valide! |
	| QC	| TREM04121935	|	Le NAM est invalide! |
	| AB	| 940114192		| Le NAM est valide! |
	| AB	| 9401141		| Le NAM est invalide! |
	| BC	| 9759528158 | Le NAM est valide! |
	| BC	| 9759528153 | Le NAM est invalide! |
	| PE	| 940114195 | Le NAM est valide! |
	| PE	| 9401141 | Le NAM est invalide! |
	| MB	| 940114192 | Le NAM est valide! |
	| MB	| 9401141 | Le NAM est invalide! |
	| NB	| 940114192 | Le NAM est valide! |
	| NB	| 9401141 | Le NAM est invalide! |
	| NS	| 9401141923 | Le NAM est valide! |
	| NS	| 9401141926545 | Le NAM est invalide! |
	| NU	| 94011419 | Le NAM est invalide! |
	| ON	| 9401141925 | Le NAM est valide! |
	| ON	| 9401141925565 | Le NAM est invalide! |
	| SK	| 940114195 | Le NAM est valide! |
	| SK	| 9401141958545 | Le NAM est invalide! |
	| NL	| 940114192698 | Le NAM est valide! |
	| NL	| 940114192698012 | Le NAM est invalide! |
	| NT	| 9401141 | Le NAM est valide! |
	| NT	| 9401141023365 | Le NAM est invalide! |
	| YT	| 940114195 | Le NAM est valide! |
	| YT	| 94011419503256 | Le NAM est invalide! |
	

	Scenario: Valider que le bon message d'erreur est renvoyé
		Given Le champ "province" est vide
		And Le champ "nam" est vide
		When Quand je clique sur valider NAM
		Then Je dois voir un message "Vous devez saisir un NAM et sélectionner une province!"

	
	Scenario Outline: Generer un NAM a partir des informations de l'usager
		Given Etant donne un "<prenom>" and Un "<nom>"
		And Une "<dateNaissance>" and Un "<sexe>"
		When quand je clique sur Generer 
		Then Je dois afficher une liste <NAM> valide pour cet usager
	
		Examples:
		| prenom 	| nom 					| dateNaissance | sexe | NAM 					|
		| Marting	| Tremblay			|	 2000-01-18		| M		 |TREM00011811, TREM00011822, TREM00011833, TREM00011844, TREM00011855, TREM00011866, TREM00011877, TREM00011888, TREM00011899	|


	Scenario: Onglet Generer un NAM Valider que le bon message d'erreur est renvoyé
		Given Le champ "prenom" est vide and le champ "nom" est vide
		And Le champ "dateNaissance" est vide and le champ "sexe" est vide
		When Quand je clique sur Generer NAM
	  Then Je dois afficher un message pour l'utilisateur

	Scenario Outline: Onglet Obtenir information sur le NAM 
		Given Le "<nam>" d'un usager
		When Quand je clique sur le bouton Obtenir l'information
		Then Je dois afficher la "<dateNaissance>" and "<sexe>" de l'usager
		
		Examples:
		| nam 					| dateNaissance | sexe 		 |
		| TREM04121925	| 2004-12-19		|	MASCULIN |
	

	Scenario: Onglet Obtenir l'information Valider que le bon message  est affiché
		Given Le champ "nam" de l'usager est vide
		When Quand je clique sur obtenir information sur le NAM
		Then Un message doit etre afficher pour l'usager
	
	  