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
		post {  
        	always {
            	mail (to: 'philippe.gauthier@inspq.qc.ca',
                      subject: "Déploiement de Utilitaire-NAM en PP", 
                      body: "Une nouvelle version de utilitaire NAM est maintenant disponible. Déployer en pré-production? ${env.BUILD_URL}")
             }
        }
        stage ('Déploiement en PP') {
            steps {
				milestone(ordinal: 1)
				script {
					def DEPLOY_PP
					try {
		            	timeout (time: 24, unit: "HOURS" ){
							DEPLOY_PP = input(
		                		id: 'tag_choice',
		                		message: 'Voulez-vous déployer la version ${TAG} de utilitaire NAM en PP ?',
		                		parameters: [ 
		                			[$class: 'ChoiceParameterDefinition', 
		                			choices: [ 'oui','non' ].join('\n'), 
		                			name: 'tag'] 
		                		]
		                	)
		                } 
		            } catch (err) {
                	    DEPLOY_PP = "non"      
                	}
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
		post {  
        	always {
            	mail (to: 'philippe.gauthier@inspq.qc.ca',
                      subject: "Déploiement de Utilitaire-NAM en PROD", 
                      body: "La nouvelle version de utilitaire NAM a été déployé en pré-production avec succès. Déployer en production? ${env.BUILD_URL}")
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
		                			choices: [ 'oui','non' ].join('\n'), 
		                			name: 'tag'] 
		                		]
		                	)
		                } 
		            } catch (err) {
                	    DEPLOY_PROD = "non"      
                	}
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