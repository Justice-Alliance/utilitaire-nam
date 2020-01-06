#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    environment {
    	NOTIFICATION_TEAM = "${env.NOTIFICATION_SX5_TEAM}"
	}
    stages {
		stage ('Construction en LABO de utilitaire-nam') {
			steps {
				milestone(ordinal: 1)
				build job: "utilitaire-nam-construction-nuit", parameters:[string(name: 'BRANCH', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 2)
			}
		}
        stage ("Lancer les tests d'intégrations en LOCAL") {
            steps {
				milestone(ordinal: 3)
	        	build job: "utilitaire-nam-tests-integration", parameters:[string(name: 'ENV', value: 'LOCAL'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 4)
			}
        }
        stage ('Déploiement de la branche en LABO') {
            steps {
				milestone(ordinal: 5)
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'LABO'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 6)
			}
        }
        stage ("Lancer les tests d'intégrations en LABO") {
            steps {
				milestone(ordinal: 7)
	        	build job: "utilitaire-nam-tests-integration", parameters:[string(name: 'ENV', value: 'LABO'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 8)
			}
        }
        stage ('Lancer le balayage de sécurité applicative en LABO') {
            steps {
				milestone(ordinal: 9)
	        	build job: "utilitaire-nam-scan-securite-app", parameters:[string(name: 'ENV', value: 'LABO'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 10)
			}
        }
    }
}