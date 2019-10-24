#language: fr
Fonctionnalité: valider un numéro d'assurance maladie
	
		Je veux valider un numéro d'assurance maladie
		En spécifiant le numéro ainsi que la province qui l'a émis
		
		Afin de valider, il faut fournir le numéro d'assurance maladie ainsi que la province

  Plan du scénario: valider un numéro d'assurance maladie qui est valide selon la province <province>
    Quand je valide le numéro d'assurance maladie "<nam>" émis par la province "<province>"
    Alors je reçois la confirmation que le numéro d'assurance maladie est valide

    Exemples: 
      | province                  | nam          |
      | Québec                    | TREM04121925 |
      | Alberta                   |    940114192 |
      | Colombie-Britannique      |   9759528158 |
      | Île-du-Prince-Édouard     |    940114195 |
      | Manitoba                  |    940114192 |
      | Nouveau-Brunswick         |    940114192 |
      | Nouvelle-Écosse           |   9401141923 |
      | Nunavut                   |    112345672 |
      | Ontario                   |   9401141925 |
      | Saskatchewan              |    940114195 |
      | Terre-Neuve-et-Labrador   | 940114192698 |
      | Territoires du Nord-Ouest |      9401141 |
      | Yukon                     |    940114195 |

  Plan du scénario: valider un numéro d'assurance maladie qui n'est pas valide selon la province <province>
    Quand je valide le numéro d'assurance maladie "<nam>" émis par la province "<province>"
    Alors je reçois un avertissement que le numéro d'assurance maladie n'est pas valide

    Exemples: 
      | province                  | nam             |
      | Québec                    | TREM04121935    |
      | Alberta                   |         9401141 |
      | Colombie-Britannique      |      9759528153 |
      | Île-du-Prince-Édouard     |         9401141 |
      | Manitoba                  |         9401141 |
      | Nouveau-Brunswick         |         9401141 |
      | Nouvelle-Écosse           |   9401141926545 |
      | Nunavut                   |        94011419 |
      | Ontario                   |   9401141925565 |
      | Saskatchewan              |   9401141958545 |
      | Terre-Neuve-et-Labrador   | 940114192698012 |
      | Territoires du Nord-Ouest |   9401141023365 |
      | Yukon                     |  94011419503256 |

	Scénario: s'assurer qu'on est averti lorsqu'on ne fourni pas les informations nécessaires à la validation du numéro d'assurance maladie
	
		Quand j'effectue une validation en ne fournissant pas les informations nécessaires
		Alors je reçois un avertissement qu'il me manque de l'information pour pouvoir procéder à la validation