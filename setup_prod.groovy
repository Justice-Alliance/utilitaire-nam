import groovy.transform.Field

@Field final String REPERTOIRE_RACINE = 'utilitaire-nam'

@Field final String PIPELINE_DEPLOIEMENT = "${REPERTOIRE_RACINE}/utilitaire-nam-deploiement"

folder("${REPERTOIRE_RACINE}") {
    description ("Utilitaire NAM")
}

pipelineJob("${PIPELINE_DEPLOIEMENT}") {
    description ('Déploiement de Utilitaire-NAM dans un environnement')
    parameters {
    	gitParam('TAG'){
    	    description('Version de utilitaire-nam à déployer')
    	    type('BRANCH_TAG')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	}
        stringParam('ENV', '', 'Environnement sur lequel on déploie Utilitaire-NAM')
    }
    definition {
        cpsScm {
            scm {
                git {
                	remote {
                	    url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
                	}
                    branch ('${TAG}')
                }
            }
            
            scriptPath('deploy.Jenkinsfile')
        }
    }
}