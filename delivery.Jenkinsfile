#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    stages {
		stage ('Construction de utilitaire-nam') {
			steps {
				milestone(ordinal: 1)
				build job: "sadu-panorama-construction", parameters:[string(name: 'BRANCH', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 2)
			}
		}
        stage ('Déploiement en DEV3') {
            steps {
				milestone(ordinal: 3)
	        	build job: "sadu-panorama-deploiement", parameters:[string(name: 'ENV', value: 'DEV3'), string(name: 'TAG', value: "${env.BRANCH_OR_TAG}")]
				milestone(ordinal: 4)
			}
        }
	}
}