#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
    tools {
        jdk 'JDK1.8.0_65'
        maven 'M3'
    }
    parameters {
        string (name: 'VERSION_TAG', description: 'Numéro de version à assigner au tag de Utilitaire-NAM')
        string (name: 'VERSION_NEXT', description: 'Numéro à assigner à la prochaine version de Utilitaire-NAM (sans SNAPSHOT)')
        string (name: 'MESSAGE', defaultValue: 'Nouveau tag ${VERSION_TAG} par Jenkins', description: 'Message à mettre dans le commit sur Git')
    }
    
    stages {
        stage ('Preparer le release de Utilitaire-NAM') {
            steps {
                sh "git checkout master"
                sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_TAG}"
                sh "mvn clean install"
                sh "mvn deploy -Dmaven.install.skip=true"
                sh "git add -- pom.xml **/pom.xml"
                sh "git pull && git commit -m '${MESSAGE}' -- pom.xml **/pom.xml && git push"
                sh "git pull && git tag -a ${VERSION_TAG} -m '${MESSAGE}' && git push origin ${VERSION_TAG}"
            }
        }        
        stage ('Packager les composants de Utilitaire-NAM dans des images Docker') {
		    environment {
			    unPom = readMavenPom file: 'utilitaire-NAM-Service/pom.xml'
			    IMAGE = unPom.getArtifactId()
		    	VERSION = readMavenPom().getVersion()
			}
            steps {
                echo "Construction de l'image Docker ${IMAGE} version ${VERSION}"
                sh "docker build --build-arg APP_VERSION=${VERSION} -t nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION} --file utilitaire-NAM-Service/Dockerfile ."
                sh "docker push nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION}"
                sh "docker tag nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION} nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:latest"
                sh "docker push nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:latest"
            }
        }
        
        stage ('Pousser les mises à jour des fichiers pom.xml pour le nouveau SNAPSHOT'){
            steps {
            	sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_NEXT}-SNAPSHOT"
        	   	sh "git add -- pom.xml **/pom.xml"
    	    	sh "git pull && git commit -m 'Nouvelle version des fichiers pom.xml par Jenkins' -- pom.xml **/pom.xml && git push"
	    	}  
    	}  
	}
	post {
        always {
            script {
                // equipe = 'mathieu.couture@inspq.qc.ca,etienne.sadio@inspq.qc.ca,soleman.merchan@inspq.qc.ca,philippe.gauthier@inspq.qc.ca,pierre-olivier.chiasson@inspq.qc.ca'
		equipe = 'bilel.hamdi@inspq.qc.ca'   // Ajout de mon adresse pour test
            }
        }
        success {
            script {
                if (currentBuild.getPreviousBuild() == null || (currentBuild.getPreviousBuild() != null && currentBuild.getPreviousBuild().getResult().toString() != "SUCCESS")) {
                    mail(to: "${equipe}", 
                        subject: "Étiquetage de Utilitaire-NAM réalisé avec succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}", 
                        body: "${env.BUILD_URL}")
                }
            }
        }
        failure {
            mail(to: "${equipe}",
                subject: "Échec de l'étiquetage de Utilitaire-NAM : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
            script{
                sh "git reset --hard HEAD"
                sh "git clean -f"
            }
        }
        unstable {
            mail(to : "${equipe}",
                subject: "Étiquetage de Utilitaire-NAM dans Nexus instable : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
    }	
}
