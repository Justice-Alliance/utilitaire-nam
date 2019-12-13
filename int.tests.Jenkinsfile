#!/usr/bin/env groovy

pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
    tools {
        maven 'maven-3.6.1'
    }
    environment {
    	NOTIFICATION_TEAM = "${env.NOTIFICATION_SX5_TEAM}"
        unServicePom = readMavenPom file: 'dev/utilitaire-nam/utilitaire-NAM-Service/pom.xml'
        UN_SERVICE_IMAGE = unServicePom.getArtifactId()
	}
    stages {
    	stage ('Checkout') {
			steps {
             	checkout scm
			}      
    	}
        stage ('Préparer les variables') {
   			steps {
                script {
                	sh "echo ${TAG} > BRANCH"
	                BRANCH_ORIGIN = sh(
	                	script: "cut -d / -f 1 BRANCH",
	                	returnStdout: true
	                	).trim()   
	                BRANCH_NAME = sh(
	                	script: "cut -d / -f 2 BRANCH",
	                	returnStdout: true
	                	).trim()  
                	sh "rm BRANCH"
                }
            }
        }
        stage ('Faire le checkout de la branche utilitaire nam') {
            steps {
            	script {
            		REMOTE = sh(
            			script: 'git remote',
	                	returnStdout: true
	                	).trim()
					sh "git checkout ${BRANCH_NAME} && git pull ${REMOTE} ${BRANCH_NAME}"
            	}
            }
        } 
        stage ('Déployer Utilitaire-NAM-Service en LOCAL') {
            steps {
                script {
	                VERSION = sh(
	                	script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -f dev/utilitaire-nam/pom.xml -q -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                	returnStdout: true
	                	).trim()
                }            
                sh "cd ops && ansible-galaxy install -r requirements.yml"
                sh "cd ops && ansible-playbook -i LOCAL/LOCAL.hosts -e unamservice_artifact_id=${UN_SERVICE_IMAGE} -e unamservice_image_version=${VERSION} deploy.yml"
            }
        }
        stage ('Tester Utilitaire-NAM') {
            steps {
		    	sh "nosetests --with-xunit --xunit-file=nosetests-unam.xml ops/tests/integration/test*.py"
            }
            post {
                success {
                    junit '**/nosetests-unam.xml'
                }
                unstable{
                    junit '**/nosetests-unam.xml'
                }
            }
        }
        stage ('Supprimer Utilitaire-NAM-Service en LOCAL') {
            steps {
                sh "docker stop unamservicelocal && docker rm unamservicelocal"
            }
        }
    }
    post {
        always {
            script {
                equipe = "${NOTIFICATION_TEAM}"
            }
        }
        success {
            script {
                if (currentBuild.getPreviousBuild() == null || (currentBuild.getPreviousBuild() != null && currentBuild.getPreviousBuild().getResult().toString() != "SUCCESS")) {
                    mail(to: "${equipe}", 
                    subject: "Tests d'intégration de Utilitaire-NAM (${env.TAG}) en ${env.ENV} réalisé avec succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}", 
                        body: "${env.BUILD_URL}")
                }
            }
        }
        failure {
            mail(to: "${equipe}",
                subject: "Échec des tests d'intégration de Utilitaire-NAM (${env.TAG}) en ${env.ENV}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
        unstable {
            mail(to: "${equipe}",
                subject: "Tests d'intégration de Utilitaire-NAM (${env.TAG}) en ${env.ENV} instable: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
    }
}
