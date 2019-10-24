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
				build job: "utilitaire-nam-construction", parameters:[]
				milestone(ordinal: 2)
			}
		}
//        stage ('Déploiement en DEV3') {
//            steps {
//				milestone(ordinal: 3)
//	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV3'), string(name: 'TAG', value: 'origin/master')]
//				milestone(ordinal: 4)
//			}
//        }
//        stage ('Déploiement en DEV2') {
//            steps {
//				milestone(ordinal: 5)
//	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV2'), string(name: 'TAG', value: 'origin/master')]
//				milestone(ordinal: 6)
//			}
//        }
        stage ('Déploiement en DEV') {
            steps {
				milestone(ordinal: 7)
	        	build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'DEV'), string(name: 'TAG', value: 'origin/master')]
				milestone(ordinal: 8)
			}
        }
    }
}