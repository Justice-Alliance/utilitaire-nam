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
                sh "if [ ! -d ansible ]; then git clone https://github.com/Inspq/ansible.git && cd ansible; else cd ansible && git pull; fi; git checkout inspq-2.4.2.0-1"
	            sh "rm -rf roles && mkdir -p roles"
	            sh "source ansible/hacking/env-setup && ansible-galaxy install -r requirements.yml"        	    
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
                                    local: 'certificats',
                                    remote: "http://svn.inspq.qc.ca/svn/inspq/infrastructure/Certificats/RTSS/SANTEPUBLIQUE"]],
                        workspaceUpdater: [$class: 'UpdateUpdater']])
                sh "source ansible/hacking/env-setup && ansible-playbook -i /SIPMI/FonctionsAllegees/properties/${env.ENV}/${env.ENV}.hosts -e unamservice_docker_image=${UN_SERVICE_IMAGE} -e unamservice_image_version=${VERSION} utilitaire-NAM-Service/deploy.yml"
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
                    subject: "Déploiement de Fonctions allégées en ${env.ENV} réalisé avec succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}", 
                    body: "${env.BUILD_URL}")
            }
        }
        failure {
            mail(to: "${equipe}",
                subject: "Échec du déploiement de Fonctions allégées en ${env.ENV}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
        unstable {
            mail(to: "${equipe}",
                subject: "Déploiement de Fonctions allégées en ${env.ENV} instable: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
    }
}