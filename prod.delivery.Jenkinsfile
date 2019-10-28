#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    stages {
    	stage ('Checkout') {
			steps {
              
			    script {
					sh "git fetch --all && git pull origin master"
					TAG = sh(returnStdout: true, script: 'git describe --abbrev=0').trim()
				}
			}      
    	}

        stage ('Déploiement en PP') {
            steps {
				milestone(ordinal: 1)
				script {
					DEPLOY_PP = input(
                		id: 'tag_choice',
                		message: 'Voulez-vous déployer la version ${TAG} de utilitaire NAM en PP ?',
                		parameters: [ 
                			[$class: 'ChoiceParameterDefinition', 
                			choices: [ 'oui','non' ].join('\n'), 
                			name: 'tag'] 
                		]
                	)
                	if ( "${DEPLOY_PP}" == "oui" ) {
					
		        		build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'PP'), string(name: 'TAG', value: "${TAG}")]
		        	}
		        	else {
		        	    currentBuild.getRawBuild().getExecutor().interrupt(Result.SUCCESS)
		        	}

		        }
				milestone(ordinal: 2)
			}
        }
        stage ('Déploiement en PROD') {
            steps {
				milestone(ordinal: 3)
				script {
					DEPLOY_PROD = input(
                		id: 'tag_choice',
                		message: 'Voulez-vous déployer la version ${TAG} de utilitaire NAM en PROD ?',
                		parameters: [ 
                			[$class: 'ChoiceParameterDefinition', 
                			choices: [ 'oui','non' ].join('\n'), 
                			name: 'tag'] 
                		]
                	)
                	if ( "${DEPLOY_PROD}" == "oui" ) {
					
		        		build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'PROD'), string(name: 'TAG', value: "${TAG}")]
		        	}
		        	else {
		        	    currentBuild.getRawBuild().getExecutor().interrupt(Result.SUCCESS)
		        	}

		        }
				milestone(ordinal: 4)
			}
        }
	}
}