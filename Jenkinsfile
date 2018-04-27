#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
    triggers { pollSCM('*/30 * * * *') }
    tools {
        jdk 'JDK1.8.0_65'
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
					junit '**/target/failsafe-reports/TEST-*.xml'
                }
            }
        }
        // stage ('Packager image Docker de utilitaire-nam') {
		//     environment {
		// 	    IMAGE = readMavenPom().getArtifactId()
		//     	VERSION = readMavenPom().getVersion()
		// 	}
        //     steps {	
		// 		sh "docker build --build-arg APP_VERSION=${VERSION} --tag nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION} --file docker/Dockerfile ."
        //         sh "docker push nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:${VERSION}"
		// 		sh "docker build --build-arg APP_VERSION=${VERSION} --tag nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:SNAPSHOT --file docker/Dockerfile ."
        //         sh "docker push nexus3.inspq.qc.ca:5000/inspq/${IMAGE}:SNAPSHOT"
        //     }
		// 	post {
        //         success {
        //             junit '**/TEST-*.xml'
        //         }
        //     }
        // }
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