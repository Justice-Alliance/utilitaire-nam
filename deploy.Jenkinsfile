#!/usr/bin/env groovy

pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
    tools {
        jdk 'JDK1.8.0_161'
        maven 'M3'
    }
    parameters {
        string (name: 'ENV', description: 'Environnement sur lequel on déploie Utilitaire-NAM')
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
                checkout([$class: 'SubversionSCM',
                        additionalCredentials: [],
                        excludedCommitMessages: '',
                        excludedRegions: '',
                        excludedRevprop: '',
                        excludedUsers: '',
                        filterChangelog: false,
                        ignoreDirPropChanges: false,
                        includedRegions: '',
                        locations: [[credentialsId: '30020735-8a8a-4209-bcb1-35b991e3b7ba',
                                    depthOption: 'infinity',
                                    ignoreExternalsOption: true,
                                    local: 'utilitaire-NAM-Service/certificats',
                                    remote: "http://svn.inspq.qc.ca/svn/inspq/infrastructure/Certificats/RTSS/INSPQ"]],
                        workspaceUpdater: [$class: 'UpdateUpdater']])
                sh "ansible-playbook -i /INSPQ/utilitaire-nam/properties/${env.ENV}/${env.ENV}.hosts utilitaire-NAM-Service/deploy-vm.yml"
                sh "ansible-playbook -i /INSPQ/utilitaire-nam/properties/${env.ENV}/${env.ENV}.hosts -e unamservice_artifact_id=${UN_SERVICE_IMAGE} -e unamservice_image_version=${VERSION} utilitaire-NAM-Service/deploy.yml"
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