#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
    jdk 'JDK1.8.0_65'
    maven 'M3'
    }
    parameters {
        string( name: 'branche', description: 'Branche sur la quelle on effectue le test SonarQube')
    }
        stages { 
    	     stage('Checkout') { 
	         steps
	         {
	            git branch: "${branche}",
                credentialsId: '1bc4f949-d629-4c86-98d9-758b1d536f40',
                url: 'https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git';
	         }
	       }
             stage ('Construire utilitaire-nam') {
                 environment {
                      unPom = readMavenPom file: 'utilitaire-NAM-Service/pom.xml'
			          IMAGE = unPom.getArtifactId()
		    	      VERSION = readMavenPom().getVersion()
                 }
                 steps {
                      sh "mvn clean install"
                      sh "mvn deploy -Dmaven.install.skip=true"
       				  sh "docker build --build-arg APP_VERSION=${VERSION} --tag nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION} --file utilitaire-NAM-Service/Dockerfile ."
                      sh "docker push nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION}"
				      sh "docker build --build-arg APP_VERSION=${VERSION} --tag nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:SNAPSHOT --file utilitaire-NAM-Service/Dockerfile ."
                      sh "docker push nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:SNAPSHOT"
                  }
                }       
             stage ('Tests SonarQube') {
                steps {
                   script { 
                   sh 'cd /var/lib/jenkins/jobs/utilitaire-nam/jobs/utilitaire-nam-construction/workspace/'
                   def mvnHome= tool name: 'M3', type: 'maven'
                   withSonarQubeEnv('SonarQube') { 
                   sh "${mvnHome}/bin/mvn sonar:sonar"
                   }
                 }
               } 
       }
    }
}
