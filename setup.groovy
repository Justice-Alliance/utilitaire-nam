import groovy.transform.Field

@Field final String REPERTOIRE_RACINE = 'utilitaire-nam'

@Field final String PIPELINE_CONSTRUCTION = "${REPERTOIRE_RACINE}/utilitaire-nam-construction"
@Field final String PIPELINE_CONSTRUCTION_NUIT = "${REPERTOIRE_RACINE}/utilitaire-nam-construction-nuit"
@Field final String PIPELINE_DEPLOIEMENT = "${REPERTOIRE_RACINE}/utilitaire-nam-deploiement"
@Field final String PIPELINE_LIVRAISON = "${REPERTOIRE_RACINE}/utilitaire-nam-livraison"
@Field final String PIPELINE_LIVRAISON_NUIT = "${REPERTOIRE_RACINE}/utilitaire-nam-livraison-nuit"
@Field final String PIPELINE_TAG = "${REPERTOIRE_RACINE}/utilitaire-nam-etiquetage"
@Field final String PIPELINE_LIVRAISON_TAG = "${REPERTOIRE_RACINE}/utilitaire-nam-livraison-tag"
@Field final String PIPELINE_SCAN_APP = "${REPERTOIRE_RACINE}/utilitaire-nam-scan-securite-app"
@Field final String PIPELINE_TESTS_INTEGRATION = "${REPERTOIRE_RACINE}/utilitaire-nam-tests-integration"
//@Field final String PIPELINE_MULTIBRANCH = "${REPERTOIRE_RACINE}/utilitaire-nam-construction-branches"

folder("${REPERTOIRE_RACINE}") {
    description ("Utilitaire NAM")
}

//multibranchPipelineJob("${PIPELINE_MULTIBRANCH}") {
//    description("Pipeline multi-branches pour utilitaire-nam")
//    branchSources {
//        git {
//            remote('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
//        }
//    }
//    triggers {
//        cron('@daily')
//    }
//    orphanedItemStrategy{
//        discardOldItems { numToKeep(5) }
//    }
//}

pipelineJob("${PIPELINE_CONSTRUCTION}") {
    description ('Construction de utilitaire-nam')
    parameters {
    	gitParam('BRANCH'){
    	    description('Branche de utilitaire-nam à construire')
    	    type('BRANCH')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
    	}
    }
    definition {
        cpsScm {
            scm {
                git {
                	remote {
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
                	}
                    branch ('${BRANCH}')
                }
            }
            scriptPath('Jenkinsfile')
        }
    }
}
pipelineJob("${PIPELINE_CONSTRUCTION_NUIT}") {
    description ('Construction de nuit de utilitaire-nam')
    parameters {
    	gitParam('BRANCH'){
    	    description('Branche de utilitaire-nam à construire')
    	    type('BRANCH')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
    	}
    }
    definition {
        cpsScm {
            scm {
                git {
                	remote {
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
                	}
                    branch ('${BRANCH}')
                }
            }
            scriptPath('nightly.Jenkinsfile')
        }
    }

}
pipelineJob("${PIPELINE_TAG}") {
    description ('Étiquetage (tag) de Utilitaire-NAM')
    parameters {
        stringParam('VERSION_TAG', '', 'Numéro de version à assigner au tag de Utilitaire-NAM')
        stringParam('VERSION_NEXT','', 'Numéro à assigner à la prochaine version de Utilitaire-NAM (sans SNAPSHOT)')
        stringParam ('MESSAGE', 'Nouveau tag ${VERSION_TAG} par Jenkins', 'Numéro à assigner à la prochaine version de Utilitaire-NAM (sans SNAPSHOT)')
    	gitParam('BRANCH'){
    	    description('Branche de utilitaire-nam à construire')
    	    type('BRANCH')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
        }
    }
    definition {
        cpsScm {
            scm {
                git{
                	remote{
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
          			}
                	branch ('${BRANCH}')
                }
            }
            scriptPath('tag.Jenkinsfile')
        }
    }
}
pipelineJob("${PIPELINE_DEPLOIEMENT}") {
    description ('Déploiement de Utilitaire-NAM dans un environnement')
    parameters {
    	gitParam('TAG'){
    	    description('Version de utilitaire-nam à déployer')
    	    type('BRANCH_TAG')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
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
pipelineJob("${PIPELINE_LIVRAISON}") {
    description ('Livraison de Utilitaire-NAM')
    triggers { scm('*/30 * * * *') }
    parameters {
    	gitParam('BRANCH_OR_TAG'){
    	    description('Branche ou étiquette de utilitaire-nam à livrer')
    	    type('BRANCH_TAG')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
        }
    }
    definition {
        cpsScm {
            scm {
                git{
                	remote{
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
          			}
                	branch ('${BRANCH_OR_TAG}')
                }
            }
            scriptPath('delivery.Jenkinsfile')
        }
    }
}
pipelineJob("${PIPELINE_LIVRAISON_NUIT}") {
    description ('Livraison de nuit ou étiquetage de Utilitaire-NAM')
    triggers { cron('00 8 * * *') }
    parameters {
    	gitParam('BRANCH_OR_TAG'){
    	    description('Branche ou étiquette de utilitaire-nam à livrer')
    	    type('BRANCH_TAG')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
        }
    }
    definition {
        cpsScm {
            scm {
                git{
                	remote{
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
          			}
                	branch ('${BRANCH_OR_TAG}')
                }
            }
            scriptPath('nightly.delivery.Jenkinsfile')
        }
    }
}

pipelineJob("${PIPELINE_LIVRAISON_TAG}") {
    description ("Livraison d'un tag de Utilitaire-NAM")
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
            scriptPath('tag.delivery.Jenkinsfile')
        }
    }
}
pipelineJob("${PIPELINE_SCAN_APP}") {
    description ("Lancement des tests de sécurité sur l'utilitaire-nam dans un environnement")
    parameters {
    	gitParam('TAG'){
    	    description('Version de utilitaire-nam à déployer')
    	    type('BRANCH_TAG')
    	    tagFilter('*')
    	    sortMode('DESCENDING_SMART')
    	    defaultValue('origin/master')
    	}
        stringParam('ENV', '', 'Environnement à anaylser')
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
