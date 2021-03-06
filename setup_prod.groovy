import groovy.transform.Field

@Field final String REPERTOIRE_RACINE = 'utilitaire-nam'

@Field final String PIPELINE_DEPLOIEMENT = "${REPERTOIRE_RACINE}/utilitaire-nam-deploiement"
@Field final String PIPELINE_LIVRAISON_PROD = "${REPERTOIRE_RACINE}/utilitaire-nam-livraison-prod"
@Field final String PIPELINE_TESTS_INTEGRATION = "${REPERTOIRE_RACINE}/utilitaire-nam-tests-integration"


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

pipelineJob("${PIPELINE_LIVRAISON_PROD}") {
    description ("Livraison de Utilitaire-NAM production")
    triggers { scm('*/10 * * * *') }
    definition {
        cpsScm {
            scm {
                git{
                	remote{
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
	                	refspec ('+refs/tags/*:refs/remotes/origin/tags/*')
          			}
                	branch ('**/tags/**')
                }
            }
            scriptPath('prod.delivery.Jenkinsfile')
        }
    }
}
pipelineJob("${PIPELINE_TESTS_INTEGRATION}") {
    description ("Tests d'intégration de Utilitaire-NAM")
    parameters {
    	gitParam('TAG'){
    	    description('Version des tests utilitaire-nam')
    	    type('BRANCH_TAG')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
    	}
        stringParam('ENV', '', 'Environnement sur lequel on test Utilitaire-NAM')
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
            
            scriptPath('int.tests.Jenkinsfile')
        }
    }
}
