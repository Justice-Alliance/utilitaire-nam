#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    stages {
		stage ('Construction de nuit de utilitaire-nam') {
        	// Ne construire que si la branche courante n'est pas un TAG
             when {
            	expression{
                	IS_BRANCH_OR_TAG = sh(returnStdout: true, script: 'git describe --exact-match HEAD >/dev/null 2>/dev/null && echo tag || echo branch').trim()
                    return IS_BRANCH_OR_TAG == 'branch'
                }
            }
			steps {
				milestone(ordinal: 1)
				build job: "utilitaire-nam-construction-nuit", parameters:[string(name: 'BRANCH', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 2)
			}
		}
        stage ('Déploiement en DEV3') {
        	// Ne déployer que si la branche courante est un origin/master
            when {
                environment name: 'BRANCH_OR_TAG', value: 'origin/master'
            }
            steps {
				milestone(ordinal: 3)
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV3'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 4)
			}
        }
        stage ('Déploiement en DEV2') {
        	// Ne déployer que si la branche courante est un origin/master
            when {
                environment name: 'BRANCH_OR_TAG', value: 'origin/master'
            }
            steps {
				milestone(ordinal: 5)
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV2'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 6)
			}
        }
        stage ('Déploiement en DEV') {
        	// Ne déployer que si la branche courante est un TAG
             when {
            	expression{
                	IS_BRANCH_OR_TAG = sh(returnStdout: true, script: 'git describe --exact-match HEAD >/dev/null 2>/dev/null && echo tag || echo branch').trim()
                    return IS_BRANCH_OR_TAG == 'tag'
                }
            }
            steps {
				milestone(ordinal: 7)
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 8)
			}
        }
        stage ('Étiqueter utilitaire-nam si des numéros de versions ont été fournis') {
        	// Lancer l'étiquetage si les paramètres sont spécifiés
            when {
            	not {
            	    anyOf {
		                environment name: 'VERSION_TAG', value: ''
		                environment name: 'VERSION_NEXT', value: ''            	        
            	    }
            	}
            }
            steps {
				milestone(ordinal: 9)
	        	build job: "utilitaire-nam-etiquetage", 
	        		parameters:[string(name: 'VERSION_TAG', value: "${env.VERSION_TAG}"), 
	        			string(name: 'VERSION_TAG', value: "${env.VERSION_TAG}"), 
	        			string(name: 'VERSION_NEXT', value: "${env.VERSION_NEXT}"), 
	        			string(name: 'MESSAGE', value: "${env.MESSAGE}"),
	        			string(name: 'BRANCH', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 10)
			}
        }
    }
}