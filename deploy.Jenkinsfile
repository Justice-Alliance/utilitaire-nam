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
        unServicePom = readMavenPom file: 'utilitaire-NAM-Service/pom.xml'
        UN_SERVICE_IMAGE = unServicePom.getArtifactId()
	    unPom = readMavenPom file: 'pom.xml'
    	VERSION = unPom.getVersion()
	}
    stages {
        stage ('Configurer Ansible') {
            steps {
	            sh "rm -rf roles && mkdir -p roles"
	            sh "ansible-galaxy install -f -r requirements.yml"        	    
            }
        }
        stage ('Déployer Utilitaire-NAM-Service') {
            steps {
                sh "ansible-playbook -i ${env.ENV}/${env.ENV}.hosts utilitaire-NAM-Service/deploy-vm.yml"
                sh "ansible-playbook -i ${env.ENV}/${env.ENV}.hosts -e unamservice_artifact_id=${UN_SERVICE_IMAGE} -e unamservice_image_version=${VERSION} utilitaire-NAM-Service/deploy.yml"
            }
        }
    }
    post {
        always {
            script {
                equipe = 'mathieu.couture@inspq.qc.ca,etienne.sadio@inspq.qc.ca,soleman.merchan@inspq.qc.ca,philippe.gauthier@inspq.qc.ca,pierre-olivier.chiasson@inspq.qc.ca,eric.parent@inspq.qc.ca'
            }
        }
        success {
            script {
                mail(to: "${equipe}", 
                    subject: "Déploiement de Utilitaire-NAM (${env.TAG}) en ${env.ENV} réalisé avec succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}", 
                    body: "${env.BUILD_URL}")
            }
        }
        failure {
            mail(to: "${equipe}",
                subject: "Échec du déploiement de Utilitaire-NAM (${env.TAG}) en ${env.ENV}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
        unstable {
            mail(to: "${equipe}",
                subject: "Déploiement de Utilitaire-NAM (${env.TAG}) en ${env.ENV} instable: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
    }
}
