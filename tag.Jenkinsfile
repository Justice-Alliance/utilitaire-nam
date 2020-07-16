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
    }
    stage (' Recherche des mise a jour de plugins Maven ') {
        steps {
           
                    sh 'mvn versions:display-dependency-updates -DprocessAllModules=true -f dev/utilitaire-nam/pom.xml'
                    sh 'mvn versions:display-plugin-updates -DprocessAllModules=true -f dev/utilitaire-nam/pom.xml'
                    sh 'mvn versions:update-parent -DprocessAllModules=true -f  dev/utilitaire-nam/pom.xml'
                    sh 'mvn -N versions:update-child-modules -DprocessAllModules=true -f  dev/utilitaire-nam/pom.xml '
                    sh 'mvn versions:use-latest-versions -Dexcludes=com.vaadin:* -DprocessAllModules=true -f dev/utilitaire-nam/pom.xml'
                    sh 'git add -- **/pom.xml'
                    sh 'git commit -m "Mise a jour dependances maven" || echo "Aucune dependances mise a jour"'
                    sh 'git pull'
                    sh 'git push'
        }
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
        
        stage ('Faire le checkout de la branche utilitaire nam a étiqueter et mettre à jour la version') {
            steps {
                script{
                    try{
                        sh "git checkout ${BRANCH_NAME} && git pull"
                        sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_TAG} -f dev/utilitaire-nam/pom.xml"
                    }catch(error){
                        timeout(time:120, unit:'SECONDS'){
                            retry(1){
                                sh "git checkout ${BRANCH_NAME} && git pull"
                                sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_TAG} -f dev/utilitaire-nam/pom.xml"
                            }
                        }    
                    }
                }
				
            }
        }
        stage ('Mise à jour des dépendances Maven ') {
            steps {
                    	sh 'mvn versions:display-dependency-updates -DprocessAllModules=true -f dev/utilitaire-nam/pom.xml'
				        sh 'mvn versions:display-plugin-updates -DprocessAllModules=true -f dev/utilitaire-nam/pom.xml'
				        sh 'mvn versions:update-parent -DprocessAllModules=true -f  dev/utilitaire-nam/pom.xml'
				        sh 'mvn -N versions:update-child-modules -DprocessAllModules=true -f  dev/utilitaire-nam/pom.xml '
			    }
			}
        }
        stage ('Construire et publier la version étiquetée de Utilitaire-NAM') {
            steps {
                script{
                    try{
                        sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY} -f dev/utilitaire-nam/pom.xml"
                        sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY} -Ddockerfile.skip=false -f dev/utilitaire-nam/pom.xml"
                        sh "git add -- **/pom.xml"
                        sh "git commit -m '${MESSAGE}'"
                        sh "git pull"
                        sh "git push"
                        sh "git tag -a ${VERSION_TAG} -m '${MESSAGE}'"
                        sh "git push origin ${VERSION_TAG}"
                    }catch(error){
                        timeout(time:120, unit:'SECONDS'){
                            retry(1){
                                sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY} -f dev/utilitaire-nam/pom.xml"
                                sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY} -Ddockerfile.skip=false -f dev/utilitaire-nam/pom.xml"
                                sh "git add -- **/pom.xml"
                                sh "git commit -m '${MESSAGE}'"
                                sh "git pull"
                                sh "git push"
                                sh "git tag -a ${VERSION_TAG} -m '${MESSAGE}'"
                                sh "git push origin ${VERSION_TAG}"
                            }
                        }
                    }
                        
            
                }
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
	            	reportDir: 'dev/utilitaire-nam/utilitaire-NAM-API/target/cukedoctor',
	            reportFiles: 'documentation.html',
	            reportName: 'Documentation et résultats des tests BDD'
	          	]        	    
        	}
        }
        stage ('Valider (commit) le fichier pom avec les mises à jour des dépendances Maven') {
            steps {
                script {
                    try {
	    			    sh 'git add -- **/pom.xml'
		    	    	sh 'git commit -m "Mise à jour dépendances maven" && git push || echo "Aucune dependances mise a jour"'
		    	    } catch(error) {
		        	    unstable("[ERROR]: ${STAGE_NAME} failed!")
			            stageResult."{STAGE_NAME}" = "UNSTABLE"
			            emailext body: ' ${JOB_NAME} ${BUILD_NUMBER} a échoué! Vous devez faire quelque chose à ce sujet. https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-etiquetage/${BUILD_NUMBER}/console', subject: 'FAILURE', to: "${NOTIFICATION_TEAM}"
			        }

			    }
            }
        }
		stage ("Tests de securité") {
            steps {
                script{
                    try{
                        sh "cd dev/utilitaire-nam && mvn validate -Psecurity"
                    }catch(err){
                        timeout(time:120, unit:'SECONDS'){
                            retry(1){
                                sh "cd dev/utilitaire-nam && mvn validate -Psecurity"
                            }
                        }
                    }
                }
                
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
                    try{
                        withSonarQubeEnv('SonarQube') { 
                            sh "cd dev/utilitaire-nam && mvn sonar:sonar"
                        }
                    }catch(error){
                        timeout(time:120, unit:'SECONDS'){
                            retry(1){
                                withSonarQubeEnv('SonarQube') { 
                   		            sh "cd dev/utilitaire-nam && mvn sonar:sonar"
                                }
                            }    
                        }
                	
                	}
                }
            }
        }
       	stage("Balayage sécurité image"){
       		steps {
	       	   	script{
                    try{
                        VERSION = sh(
	                	script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -q -f dev/utilitaire-nam/pom.xml -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                	returnStdout: true
	                	).trim()
	       	   		    sh "docker pull arminc/clair-db && docker pull arminc/clair-local-scan"
    	    		    sh '''
	                    until $(curl --output /dev/null --silent --fail http://localhost:16060/v1/namespaces)
	                    do 
	      				docker inspect utilitairenamclairdb 2>/dev/null >/dev/null && echo utilitairenamclairdb est demarre || docker run -d --rm --name utilitairenamclairdb arminc/clair-db
	                	printf '.'
	                	sleep 5
	    	    		docker inspect utilitairenamclair 2>/dev/null >/dev/null && echo utilitairenamclair est demarre || docker run -p 16060:6060 --link utilitairenamclairdb:postgres -d --rm --name utilitairenamclair arminc/clair-local-scan
	    	    		sleep 5
	                    done
	                    '''      
        			    sh "cd ops && wget -qO clairctl https://github.com/jgsqware/clairctl/releases/download/v1.2.8/clairctl-linux-amd64 && chmod u+x clairctl"
        			    sh "cd ops && ./clairctl analyze ${DOCKER_REPOSITORY}/${DOCKER_REPOSITORY_PREFIX}/${SVC_ARTIFACT_ID}:${VERSION} --filters High,Critical,Defcon1"     		    
        			}catch (err) {
                        timeout(time:120, unit:'SECONDS'){
                            retry(1){
                                VERSION = sh(
	                	        script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -q -f dev/utilitaire-nam/pom.xml -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                	        returnStdout: true
	                	        ).trim()
	       	   		            sh "docker pull arminc/clair-db && docker pull arminc/clair-local-scan"
    	    		            sh '''
	                            until $(curl --output /dev/null --silent --fail http://localhost:16060/v1/namespaces)
	                            do 
	      				        docker inspect utilitairenamclairdb 2>/dev/null >/dev/null && echo utilitairenamclairdb est demarre || docker run -d --rm --name utilitairenamclairdb arminc/clair-db
	                	        printf '.'
	                	        sleep 5
	    	    		        docker inspect utilitairenamclair 2>/dev/null >/dev/null && echo utilitairenamclair est demarre || docker run -p 16060:6060 --link utilitairenamclairdb:postgres -d --rm --name utilitairenamclair arminc/clair-local-scan
	    	    		        sleep 5
	                            done
	                            '''      
        			            sh "cd ops && wget -qO clairctl https://github.com/jgsqware/clairctl/releases/download/v1.2.8/clairctl-linux-amd64 && chmod u+x clairctl"
        			            sh "cd ops && ./clairctl analyze ${DOCKER_REPOSITORY}/${DOCKER_REPOSITORY_PREFIX}/${SVC_ARTIFACT_ID}:${VERSION} --filters High,Critical,Defcon1"     		    
                            }
                        }

                            if (currentBuild.result() == "FAILURE"){
                                unstable("Vulnérabilités identifées dans l'image")
        			        //currentBuild.result = 'FAILURE'
                            }           
                    }
                }    
	        	sh "cd ops && mkdir -p reports && ./clairctl report ${DOCKER_REPOSITORY}/${DOCKER_REPOSITORY_PREFIX}/${SVC_ARTIFACT_ID}:${VERSION} && mv reports/html/analysis-${DOCKER_REPOSITORY}-${DOCKER_REPOSITORY_PREFIX}-${SVC_ARTIFACT_ID}-${VERSION}.html reports/html/analyse-image.html"
	        	sh "docker stop utilitairenamclair utilitairenamclairdb && rm ops/clairctl"		    
        		
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
        stage ('Pousser les mises à jour des fichiers pom.xml pour le nouveau SNAPSHOT'){
            steps {
            	sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_NEXT}-SNAPSHOT -f dev/utilitaire-nam/pom.xml"
        	   	sh "git add -- **/pom.xml"
    	    	sh "git commit -m 'Nouvelle version des fichiers pom.xml par Jenkins'"
    	    	sh "git push"
	    	}  
    	}  
	}
	post {
        always {
            script {
                equipe = "${NOTIFICATION_TEAM}"
                sh "docker stop utilitairenamclair utilitairenamclairdb 2>/dev/null || echo clair supprime"
                sh "rm -f ops/clairctl"	
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
