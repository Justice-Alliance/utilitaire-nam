#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
    triggers { pollSCM('*/30 * * * *') }
    tools {
        jdk 'openjdk-11'
        maven 'maven-3.6.1'
    }
	environment {
        MVN_REPOSITORY = "${env.MVN_REPOSITORY_INSPQ}"
    	REPOSITORY = "${env.REPOSITORY_INSPQ}"
    	NOTIFICATION_TEAM = "${env.NOTIFICATION_SX5_TEAM}"
    }
    stages {
        stage ('Préparer les variables') {
   			steps {
                script {
                	sh "echo ${BRANCH} > BRANCH"
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
        stage ('Construire utilitaire-nam') {
            steps {
            	script {
	                VERSION = sh(
	                	script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -f dev/utilitaire-nam/pom.xml -q -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                	returnStdout: true
	                	).trim()
	                	
	                 // 1. option de nouvelle tentative pour les étapes ayant échoué 
                    try {

                            build "${env.BUILD_NUMBER}"
                            // Configurer le numéro de version pour utiliser le nom de la branche si on est pas sur master
                            sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION} -f dev/utilitaire-nam/pom.xml"
                            sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY} -f dev/utilitaire-nam/pom.xml"
                            sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY} -Ddockerfile.skip=false -f dev/utilitaire-nam/pom.xml"
                            // Annuler les modifications faites au fichier pom par la première étape
                            sh "git checkout -- **/pom.xml"
                            
                    }  catch(error) {
                                        if (currentBuild.result == 'FAILURE') {
                                            retry(2) {
                                                    build "${env.BUILD_NUMBER}"
                                            }
                                        }
                                        else {
                                            echo "${env.BUILD_NUMBER}"
                                            post {
                                                   success {
                                                            archive '**/target/*.jar'
                                                            junit '**/target/surefire-reports/TEST-*.xml'
                                                    }
                                            }
                                        }

                        }
                }                        	
                      
            }
           

        //    post {
        //       success {
        //            archive '**/target/*.jar'
        //            junit '**/target/surefire-reports/TEST-*.xml'
        //        }
        //    }
        }
        stage ("Publier le résultats des tests et la documentation Cucumber API") {
        	steps {
	            publishHTML target: [
	            	allowMissing: false,
	            	alwaysLinkToLastBuild: false,
	            	keepAll: true,
	            	reportDir: 'dev/utilitaire-nam/utilitaire-NAM-API/target/cukedoctor',
	            reportFiles: 'documentation.html',
	            reportName: 'Documentation et résultats des tests BDD'
	          	]        	    
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
                        subject: "Construction de utilitaire-nam réalisée avec succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}", 
                        body: "${env.BUILD_URL}")
                }
            }
        }
        failure {
            mail(to: "${equipe}",
                subject: "Échec de la construction de utilitaire-nam : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
        unstable {
            mail(to : "${equipe}",
                subject: "Construction de utilitaire-nam instable : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
    }
}
