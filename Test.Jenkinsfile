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
		svcPom = readMavenPom file: 'dev/utilitaire-nam/utilitaire-nam-service/pom.xml'
		SVC_ARTIFACT_ID = svcPom.getArtifactId()
		POMVERSION = projectPom.getVersion()
    	REPOSITORY_PREFIX = "inspq"
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
                    try{
                        REMOTE = sh(
            			script: 'git remote',
	                	returnStdout: true
	                	).trim()
					    sh "git checkout ${BRANCH_NAME} && git pull ${REMOTE} ${BRANCH_NAME}"
                    }catch(error){
                        timeout(time:120, unit:"SECONDS"){
                            retry(1){
                                REMOTE = sh(
            			        script: 'git remote',
	                	        returnStdout: true
	                	        ).trim()
					            sh "git checkout ${BRANCH_NAME} && git pull ${REMOTE} ${BRANCH_NAME}"                            }
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
				   sh 'mvn versions:use-latest-versions -Dexcludes=com.vaadin:* -DprocessAllModules=true -f dev/utilitaire-nam/pom.xml'
            }
        }
        
        stage ('Construire utilitaire-nam') {
			environment {
		    	projectPom = readMavenPom file: 'dev/utilitaire-nam/pom.xml'
		    	svcPom = readMavenPom file: 'dev/utilitaire-nam/utilitaire-nam-service/pom.xml'
			    SVC_ARTIFACT_ID = svcPom.getArtifactId()
		    	POMVERSION = projectPom.getVersion()
		    }        
            steps{
                script {
                    try{
                        VERSION = sh(
	                    script: 'if [ "$(git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD)" == "master" ]; then mvn -f dev/utilitaire-nam/pom.xml -q -Dexec.executable="echo" -Dexec.args=\'${project.version}\' --non-recursive exec:exec 2>/dev/null; else git describe --exact-match HEAD 2>>/dev/null || git rev-parse --abbrev-ref HEAD; fi',
	                    returnStdout: true
	                    ).trim()
                         
	                    // Configurer le numéro de version pour utiliser le nom de la branche si on est pas sur master
                        sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION} -f dev/utilitaire-nam/pom.xml"
                        sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY} -f dev/utilitaire-nam/pom.xml"
                        sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY} -Ddockerfile.skip=false -f dev/utilitaire-nam/pom.xml"
                        // Annuler les modifications faites au fichier pom par la première étape
                        sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${POMVERSION} -f dev/utilitaire-nam/pom.xml"
                    
                    }catch(error) {
                        timeout(time:120, unit:'SECONDS'){
                            retry(2) {
                                // Annuler les modifications faites au fichier pom par la mise à jour des librairies
                                sh "git checkout -- **/pom.xml" 
                                // Configurer le numéro de version pour utiliser le nom de la branche si on est pas sur master
                                sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION} -f dev/utilitaire-nam/pom.xml"
                                sh "mvn clean install -Dprivate-repository=${MVN_REPOSITORY} -f dev/utilitaire-nam/pom.xml"
                                sh "mvn deploy -Dmaven.install.skip=true -DskipTests -Dprivate-repository=${MVN_REPOSITORY} -Ddockerfile.skip=false -f dev/utilitaire-nam/pom.xml"
		                        // Annuler les modifications faites au fichier pom par la première étape
		                        sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${POMVERSION} -f dev/utilitaire-nam/pom.xml"
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
        stage ("Publier le résultats des tests et la documentation Cucumber API") {
        	steps {
	            publishHTML target: [
	            	allowMissing: false,
	            	alwaysLinkToLastBuild: false,
	            	keepAll: true,
	            	reportDir: 'dev/utilitaire-nam/utilitaire-nam-api/target/cukedoctor',
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
		    environment {
		    	projectPom = readMavenPom file: 'dev/utilitaire-nam/pom.xml'
		    	svcPom = readMavenPom file: 'dev/utilitaire-nam/utilitaire-nam-service/pom.xml'
			    SVC_ARTIFACT_ID = svcPom.getArtifactId()
		    	POMVERSION = projectPom.getVersion()
		        SVC_IMAGE = "${REPOSITORY}/${REPOSITORY_PREFIX}/${SVC_ARTIFACT_ID}:${POMVERSION}"
		        SVC_RAPPORT = "analysis-${REPOSITORY}-${REPOSITORY_PREFIX}-${SVC_ARTIFACT_ID}-${POMVERSION}.html"    
		    }
       		steps {
	       	   	script {
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
        			try {
	        			sh "cd ops && ./clairctl analyze ${SVC_IMAGE} --filters High,Critical,Defcon1"     		    
        			} catch (err) {
        			      unstable("Vulnérabilités identifées dans l'image")
        			      //currentBuild.result = 'FAILURE'
        			}
	        		sh "cd ops && mkdir -p reports && ./clairctl report ${SVC_IMAGE} && mv reports/html/${SVC_RAPPORT} reports/html/analyse-image.html"
	        		sh "docker stop utilitairenamclair utilitairenamclairdb && rm ops/clairctl"		    
        		}
       		}
      	}
        stage ("Construire et publier la version étiquetée de Utilitaire-NAM") {
            when {
                buildingTag()
            }
            steps {
                script {
                    sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${VERSION_TAG} -f dev/utilitaire-nam/pom.xml"
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
        stage ("Balayage de securite image"){
            when {
                buildingTag()
            }
            steps {
                script {
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
        stage ('Valider (commit) le fichier pom avec les mises à jour des dépendances Maven') {
            steps {
                script {
                    try {
                        // Annuler les modifications faites au fichier pom par la première étape
                        sh "mvn versions:set -DprocessAllModules=true -DnewVersion=${POMVERSION} -f dev/utilitaire-nam/pom.xml"
				        sh 'git add -- **/pom.xml'
				        sh 'git commit -m "Mise à jour dépendances maven" && git push || echo "Aucune dependances mise a jour"'
				    } catch (error) {
			            unstable("[ERROR]: ${STAGE_NAME} failed!")
			            stageResult."{STAGE_NAME}" = "UNSTABLE"
			            emailext body: ' ${JOB_NAME} ${BUILD_NUMBER} a échoué! Vous devez faire quelque chose à ce sujet. https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/${BUILD_NUMBER}/console', subject: 'FAILURE', to: "${NOTIFICATION_TEAM}"
		            }
			    }
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
                        subject: "Construction de utilitaire-nam et etiquetage réalisée avec succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}", 
                        body: "${env.BUILD_URL}")
                }
            }
        }
        failure {
            mail(to: "${equipe}",
                subject: "Échec de la construction et etiquetage de utilitaire-nam : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
        unstable {
            mail(to : "${equipe}",
                subject: "Construction de utilitaire-nam et etiquetage instable : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
    }
}