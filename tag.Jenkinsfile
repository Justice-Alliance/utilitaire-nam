#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
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
        stage ('Faire le checkout de la branche unitilitaire nam a étiqueter et mettre à jour la version') {
            steps {
				sh "git checkout ${BRANCH_NAME} && git pull"
                sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_TAG}"
            }
        } 
        stage ('Preparer le release de Utilitaire-NAM') {
            steps {
                sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY}"
                sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY}"
                sh "git add -- pom.xml **/pom.xml"
                sh "git commit -m '${MESSAGE}'"
                sh "git pull"
                sh "git push"
                sh "git tag -a ${VERSION_TAG} -m '${MESSAGE}'"
                sh "git push origin ${VERSION_TAG}"
            }
        	post {
                success {
                    archive '**/target/*.jar'
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage ("Publier le résultats des tests et la documentation Cucumber") {
        	steps {
	            publishHTML target: [
	            	allowMissing: false,
	            	alwaysLinkToLastBuild: false,
	            	keepAll: true,
	            	reportDir: 'target/cukedoctor',
	            reportFiles: 'documentation.html',
	            reportName: 'Documentation et résultats des tests BDD'
	          	]        	    
        	}
        } 
		stage ("Tests de securité") {
            steps {
                sh "mvn validate -Psecurity"
            }
        }
        stage ("Publier le résultats des tests ") {
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
        stage ('Packager les composants de Utilitaire-NAM dans des images Docker') {
		    environment {
			    unPom = readMavenPom file: 'utilitaire-NAM-Service/pom.xml'
			    IMAGE = unPom.getArtifactId()
		    	VERSION = readMavenPom().getVersion()
			}
            steps {
                echo "Construction de l'image Docker ${IMAGE} version ${VERSION}"
                
                sh "docker build --build-arg APP_VERSION=${VERSION} --tag ${REPOSITORY}/inspq/${IMAGE}:${VERSION} --tag ${REPOSITORY}/inspq/${IMAGE}:latest --file utilitaire-NAM-Service/Dockerfile ."
                sh "docker push ${REPOSITORY}/inspq/${IMAGE}:${VERSION}"
                sh "docker push ${REPOSITORY}/inspq/${IMAGE}:latest"
            }
        }
        
        stage ('Pousser les mises à jour des fichiers pom.xml pour le nouveau SNAPSHOT'){
            steps {
            	sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_NEXT}-SNAPSHOT"
        	   	sh "git add -- pom.xml"
    	    	sh "git commit -m 'Nouvelle version des fichiers pom.xml par Jenkins'"
    	    	sh "git push"
	    	}  
    	}  
	}
	post {
        always {
            script {
                equipe = 'mathieu.couture@inspq.qc.ca,etienne.sadio@inspq.qc.ca,soleman.merchan@inspq.qc.ca,philippe.gauthier@inspq.qc.ca,pierre-olivier.chiasson@inspq.qc.ca'
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
