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
    stages {
        stage ('Construire utilitaire-nam') {
            steps {
                sh "mvn clean install"
                sh "mvn deploy -Dmaven.install.skip=true"
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
        stage ('Exécuter les tests de sécurité') {
            steps {
                sh "mvn validate -Psecurity"
            }
        }
        stage ("Publier le résultats des tests de sécurité") {
        	steps {
	            publishHTML target: [
	            	allowMissing: false,
	            	alwaysLinkToLastBuild: false,
	            	keepAll: true,
	            	reportDir: 'target',
	            reportFiles: 'dependency-check-report.html',
	            reportName: 'résultats des sécurités des librairies'
	          	]        	    
        	}
        }
        stage ('Packager image Docker de utilitaire-nam') {
		    environment {
                unPom = readMavenPom file: 'utilitaire-NAM-Service/pom.xml'
			    IMAGE = unPom.getArtifactId()
		    	VERSION = readMavenPom().getVersion()
			}
            steps {	
				sh "docker build --build-arg APP_VERSION=${VERSION} --tag nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION} --file utilitaire-NAM-Service/Dockerfile ."
                sh "docker push nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION}"
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
