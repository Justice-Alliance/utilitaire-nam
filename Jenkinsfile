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
        maven 'M3'
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
				sh "git checkout ${BRANCH_NAME} && git pull"
            }
        } 
        stage ('Construire utilitaire-nam') {
            steps {
                sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY}"
                sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY}"
            }
            post {
                success {
                    archive '**/target/*.jar'
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage ("Publier le résultats des tests et la documentation Cucumber API") {
        	steps {
	            publishHTML target: [
	            	allowMissing: false,
	            	alwaysLinkToLastBuild: false,
	            	keepAll: true,
	            	reportDir: 'utilitaire-NAM-API/target/cukedoctor',
	            reportFiles: 'documentation.html',
	            reportName: 'Documentation et résultats des tests BDD'
	          	]        	    
        	}
        }        
        stage ('Packager image Docker de utilitaire-nam') {
		    environment {
                unPom = readMavenPom file: 'utilitaire-NAM-Service/pom.xml'
			    IMAGE = unPom.getArtifactId()
			    APP_VERSION = unPom.getVersion()
			}
            steps {
                script {
	                VERSION = sh(
	                	script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -q -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                	returnStdout: true
	                	).trim()
                }                        	
                sh "docker build --build-arg APP_VERSION=${APP_VERSION} --tag ${REPOSITORY}/inspq/${IMAGE}:${VERSION} --file utilitaire-NAM-Service/Dockerfile ."
                sh "docker push ${REPOSITORY}/inspq/${IMAGE}:${VERSION}"
            }
        }
    }
    post {
        always {
            script {
                equipe = 'mathieu.couture@inspq.qc.ca,philippe.gauthier@inspq.qc.ca,pierre-olivier.chiasson@inspq.qc.ca'
				//equipe = 'bilel.hamdi@inspq.qc.ca'  // Ajout de mon adresse pour Test
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
