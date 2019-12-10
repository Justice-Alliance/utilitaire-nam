#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
    tools {
        jdk 'openjdk-11'
        maven 'maven-3.6.1'
    }
	environment {
        MVN_REPOSITORY = "${env.MVN_REPOSITORY_INSPQ}"
    	REPOSITORY = "${env.REPOSITORY_INSPQ}"
    	NOTIFICATION_TEAM = "${env.NOTIFICATION_SX5_TEAM}"
    	projectPom = readMavenPom file: 'dev/utilitaire-nam/pom.xml'
    	svcPom = readMavenPom file: 'dev/utilitaire-nam/utilitaire-NAM-Service/pom.xml'
	    SVC_ARTIFACT_ID = svcPom.getArtifactId()
    	POMVERSION = projectPom.getVersion()
    	DOCKER_REPOSITORY = projectPom.getProperties().getProperty('docker.repository')
    	DOCKER_REPOSITORY_PREFIX = projectPom.getProperties().getProperty('docker.repository.prefix')
    	ANSIBLE_VAULT_ID = '/etc/ansible/passfile'
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
            	script {
	                VERSION = sh(
	                	script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -f dev/utilitaire-nam/pom.xml -q -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                	returnStdout: true
	                	).trim()
                }                        	
            	// Configurer le numéro de version pour utiliser le nom de la branche si on est pas sur master
            	sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION} -f dev/utilitaire-nam/pom.xml"
                sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY} -f dev/utilitaire-nam/pom.xml"
                sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY} -Ddockerfile.skip=false -f dev/utilitaire-nam/pom.xml"
                // Annuler les modifications faites au fichier pom par la première étape
                sh "git checkout -- **/pom.xml"
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
	            	reportDir: 'dev/utilitaire-nam/utilitaire-NAM-API/target/cukedoctor',
	            reportFiles: 'documentation.html',
	            reportName: 'Documentation et résultats des tests BDD'
	          	]        	    
        	}
        }        
        stage ('Exécuter les tests de sécurité') {
            steps {
                sh "cd dev/utilitaire-nam && mvn validate -Psecurity"
            }
        }
        stage ("Publier le résultats des tests de l'anaylse statique et des librairies") {
        	steps {
	            publishHTML target: [
	            	allowMissing: false,
	            	alwaysLinkToLastBuild: false,
	            	keepAll: true,
	            	reportDir: 'dev/utilitaire-nam/target',
	            reportFiles: 'dependency-check-report.html',
	            reportName: 'résultats des sécurités des librairies'
	          	]        	    
        	}
        }
        stage ('Tests SonarQube') {
        	steps {
            	script { 
                	withSonarQubeEnv('SonarQube') { 
                   		sh "cd dev/utilitaire-nam && mvn sonar:sonar"
                	}
                }
            }
        }
       	stage("Balayage sécurité image"){
       		steps {
	       	   	script {
	                VERSION = sh(
	                	script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -q -f dev/utilitaire-nam/pom.xml -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                	returnStdout: true
	                	).trim()
	      			sh "docker pull arminc/clair-db && docker run -d --rm --name utilitairenamclairdb arminc/clair-db && sleep 15"
    	    		sh "docker pull arminc/clair-local-scan && docker run -p 16060:6060 --link utilitairenamclairdb:postgres -d --rm --name utilitairenamclair arminc/clair-local-scan && sleep 5"
        			sh "cd ops && wget -qO clairctl https://github.com/jgsqware/clairctl/releases/download/v1.2.8/clairctl-linux-amd64 && chmod u+x clairctl"
        			try {
	        			sh "cd ops && ./clairctl analyze ${DOCKER_REPOSITORY}/${DOCKER_REPOSITORY_PREFIX}/${SVC_ARTIFACT_ID}:${VERSION}"     		    
        			} catch (err) {
        			      unstable("Vulnérabilités identifées dans l'image")
        			      //currentBuild.result = 'FAILURE'
        			}
	        		sh "cd ops && mkdir -p reports && ./clairctl report ${DOCKER_REPOSITORY}/${DOCKER_REPOSITORY_PREFIX}/${SVC_ARTIFACT_ID}:${VERSION} && mv reports/html/analysis-${DOCKER_REPOSITORY}-${DOCKER_REPOSITORY_PREFIX}-${SVC_ARTIFACT_ID}-${VERSION}.html reports/html/analyse-image.html"
	        		sh "docker stop utilitairenamclair utilitairenamclairdb && rm ops/clairctl"		    
        		}
       		}
      	}
        stage ("Publier le résultats des tests de balayage de l'image") {
        	steps {
	            publishHTML target: [
	            	allowMissing: false,
	            	alwaysLinkToLastBuild: false,
	            	keepAll: true,
	            	reportDir: "ops/reports/html",
	            reportFiles: "analyse-image.html",
	            reportName: "résultats du test de balayage de l'image"
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
