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
        stage ('Étiqueter utilitaire-nam') {
            steps {
				milestone(ordinal: 7)
                script {
                	timeout (time: 30, unit: "SECONDS" ){
	                	def VERSION
	                	def VERSION_TAG
	                	def VERSION_NEXT
	                	def VERSION_MESSAGE
	                	def TAG_CHOICE = input(
	                		id: 'tag_choice',
	                		message: 'Les tests sont conclants, voulez vous dans ce cas taguer cette version ?',
	                		parameters: [ 
	                			[$class: 'ChoiceParameterDefinition', 
	                			choices: [ 'oui','non' ].join('\n'), 
	                			name: 'tag'] 
	                		]
	                	)
	                	if ( "${TAG_CHOICE}" == "oui" ) {
		                	VERSION = input(
		                		id: 'version',
		                		message: 'Numéros de version à assigner à Utilitaire-NAM',
		                		parameters: [
		                			string(
		                				name: 'VERSION_TAG', 
		                				description: 'Numéro de version à assigner au tag de Utilitaire-NAM'),
		                			string(
		                				name: 'VERSION_NEXT', 
		                				description: 'Numéro à assigner à la prochaine version de Utilitaire-NAM'),
		                			string(
		                				name: 'MESSAGE', 
		                				description: 'Message à mettre dans le commit sur Git'),
		                		]
		                	)
		                	VERSION_TAG = VERSION.VERSION_TAG?:''
		                	VERSION_NEXT = VERSION.VERSION_NEXT?:''
		                	VERSION_MESSAGE = VERSION.VERSION_MESSAGE?:'Nouveau tag ${VERSION_TAG} par Jenkins'
		                	if ( "${VERSION_TAG}" != '' && "${VERSION_NEXT}" != '' ) {
					        	build job: "utilitaire-nam-etiquetage", 
					        		parameters:[ 
				    	    			string(name: 'VERSION_TAG', value: "${VERSION_TAG}"), 
				        				string(name: 'VERSION_NEXT', value: "${VERSION_NEXT}"), 
				        				string(name: 'MESSAGE', value: "${VERSION_MESSAGE}"),
				        				string(name: 'BRANCH', value: "${env.BRANCH_OR_TAG}")
				        			]
				        	}
			        	}
			        }
	        	}
				milestone(ordinal: 8)
			}
        }
    }
	post {
		aborted {
      		echo "Aucun TAG n'a été créé."
      		currentBuild.result = 'SUCCESS'
      	} 
    }
}