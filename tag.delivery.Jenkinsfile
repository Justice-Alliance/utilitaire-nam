#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    stages {
        stage ('Déploiement en DEV') {
            steps {
				milestone(ordinal: 1)
				script {
					sh "git fetch --all && git pull origin master"
					TAG = sh(returnStdout: true, script: 'git describe --abbrev=0').trim()
		        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV'), string(name: 'TAG', value: "${TAG}")]
		        }
				milestone(ordinal: 2)
			}
        }
	}
}