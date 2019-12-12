#!/usr/bin/env groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
    }
    parameters {
        string (name: 'ENV', description: 'Environnement à anaylser')
    }
    environment {
        MVN_REPOSITORY = "${env.MVN_REPOSITORY_INSPQ}"
    	REPOSITORY = "${env.REPOSITORY_INSPQ}"
    	NOTIFICATION_TEAM = "${env.NOTIFICATION_SX5_TEAM}"
        unamIvfile = readYaml file: "ops/${env.ENV}/group_vars/unam"
        APPURL = "${unamIvfile.unamservice_protocol}://${unamIvfile.unamservice_base_url}"
    }
    stages {
        stage ('Configurer Ansible') {
            steps {
	            sh "rm -rf ops/roles"
	            sh "cd ops && ansible-galaxy install -f -r requirements.yml"        	    
            }
        }
        stage ('Tests de sécurité applicative utilitaire-nam') {
            steps {
                sh "cd ops && ansible-playbook startAppScan.yml -i ./${env.ENV}/${env.ENV}.hosts -e app_url=${APPURL} -e build_number=${env.BUILD_NUMBER} && unzip reports/${env.BUILD_NUMBER}/app_report.zip -d reports/${env.BUILD_NUMBER}/unam" 
                publishHTML target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: "ops/reports/${env.BUILD_NUMBER}/unam",
                    reportFiles: 'index.html',
                    reportName: 'unam non-Auth Report'
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
                mail(to: "${equipe}", 
                    subject: "Scan de l'utilitaire-nam en ${env.ENV} réalisé avec succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}", 
                    body: "${env.BUILD_URL}")
            }
        }
        failure {
            mail(to: "${equipe}",
                subject: "Échec du Scan de l'utilitaire-nam en ${env.ENV}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
        unstable {
            mail(to: "${equipe}",
                subject: "Scan de l'utilitaire-nam en ${env.ENV} instable: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "${env.BUILD_URL}")
        }
    }
}