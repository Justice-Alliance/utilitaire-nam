#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
		disableConcurrentBuilds()
    }
    environment {
        MVN_REPOSITORY = "${env.MVN_REPOSITORY_INSPQ}"
    	REPOSITORY = "${env.REPOSITORY_INSPQ}"
    	NOTIFICATION_TEAM = "${env.NOTIFICATION_SX5_TEAM}"
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
            	mail (to: "${NOTIFICATION_TEAM}",
                      subject: "Déploiement de Utilitaire-NAM en PP", 
                      body: "Une nouvelle version de utilitaire NAM est maintenant disponible. Déployer en pré-production? ${env.JOB_URL}")
				script {
					def DEPLOY_PP
					try {
		            	timeout (time: 24, unit: "HOURS" ){
							DEPLOY_PP = input(
		                		id: 'tag_choice',
		                		message: 'Voulez-vous déployer la version ${TAG} de utilitaire NAM en PP ?',
		                		parameters: [ 
		                			[$class: 'ChoiceParameterDefinition', 
		                			choices: [ 'oui','non','annuler' ].join('\n'), 
		                			name: 'tag'] 
		                		]
		                	)
		                } 
		            } catch (err) {
                	    DEPLOY_PP = "annuler"      
                	}
                	if ( "${DEPLOY_PP}" == "oui" ) {
					
		        		build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'PP'), string(name: 'TAG', value: "${TAG}")]
		            	mail (to: "${NOTIFICATION_TEAM}",
		                      subject: "Déploiement de Utilitaire-NAM en PROD", 
		                      body: "La nouvelle version de utilitaire NAM a été déployée en pré-production avec succès. Déployer en production? ${env.JOB_URL}")
				        	}
		        	else if ( "${DEPLOY_PP}" == "annuler") {
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
					def DEPLOY_PROD
					try {
		            	timeout (time: 24, unit: "HOURS" ){
							DEPLOY_PROD = input(
		                		id: 'tag_choice',
		                		message: 'Voulez-vous déployer la version ${TAG} de utilitaire NAM en PROD ?',
		                		parameters: [ 
		                			[$class: 'ChoiceParameterDefinition', 
		                			choices: [ 'oui','non','annuler' ].join('\n'), 
		                			name: 'tag'] 
		                		]
		                	)
		                } 
		            } catch (err) {
                	    DEPLOY_PROD = "annuler"      
                	}
                	if ( "${DEPLOY_PROD}" == "oui" ) {
					
		        		build job: "utilitaire-nam-deploiement", parameters:[string(name: 'ENV', value: 'PROD'), string(name: 'TAG', value: "${TAG}")]
		            	mail (to: "${NOTIFICATION_TEAM}",
		                      subject: "Déploiement de Utilitaire-NAM en PROD", 
		                      body: "La nouvelle version de utilitaire NAM a été déployée en production avec succès: ${env.JOB_URL}")
		        	}
		        	else if ( "${DEPLOY_PROD}" == "annuler") {
		        	    currentBuild.getRawBuild().getExecutor().interrupt(Result.SUCCESS)
		        	}

		        }
				milestone(ordinal: 4)
			}
        }
	}
}