#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    stages {
        stage ('Déploiement en DEV') {
        	// Ne déployer que si la branche courante est un TAG
             when {
            	expression{
                	IS_BRANCH_OR_TAG = sh(returnStdout: true, script: 'git describe --exact-match HEAD >/dev/null 2>/dev/null && echo tag || echo branch').trim()
                    return IS_BRANCH_OR_TAG == 'tag'
                }
            }
            steps {
				milestone(ordinal: 1)
				TAG = sh(returnStdout: true, script: 'git describe --abbrev=0').trim()
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV'), string(name: 'TAG', value: "${TAG}")]
				milestone(ordinal: 2)
			}
        }
	}
}