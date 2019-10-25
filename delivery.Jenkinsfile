#!/usr/bin/env groovy
pipeline {
    agent any
    triggers { pollSCM('*/30 * * * *') }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    stages {
		stage ('Construction de utilitaire-nam') {
			steps {
				milestone(ordinal: 1)
				build job: "utilitaire-nam-construction", parameters:[string(name: 'BRANCH', value: '${BRANCH_OR_TAG}')]
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
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV3'), string(name: 'TAG', value: 'origin/master')]
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
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV2'), string(name: 'TAG', value: 'origin/master')]
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
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV'), string(name: 'TAG', value: '${BRANCH_OR_TAG}')]
				milestone(ordinal: 8)
			}
        }
    }
}