import groovy.transform.Field

@Field final String REPERTOIRE_RACINE = 'utilitaire-nam'

@Field final String PIPELINE_CONSTRUCTION = "${REPERTOIRE_RACINE}/utilitaire-nam-construction"
@Field final String PIPELINE_DEPLOIEMENT = "${REPERTOIRE_RACINE}/utilitaire-nam-deploiement"
@Field final String PIPELINE_LIVRAISON = "${REPERTOIRE_RACINE}/utilitaire-nam-livraison"
@Field final String PIPELINE_TAG = "${REPERTOIRE_RACINE}/utilitaire-nam-etiquetage"

folder("${REPERTOIRE_RACINE}") {
    description ("Utilitaire NAM")
}

pipelineJob("${PIPELINE_CONSTRUCTION}") {
    description ('Construction de utilitaire-nam')
    definition {
        cpsScm {
            scm {
                git{
                	remote{
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
          			}
                	branch ('origin/master')
                }
            }
            scriptPath('Jenkinsfile')
        }
    }
}
// pipelineJob("${PIPELINE_TAG}") {
//     description ('Étiquetage (tag) de SADU Panorama')
//     parameters {
//         stringParam('VERSION_TAG', '', 'Numéro de version a assigner au Tag de SADU')
//         stringParam('VERSION_NEXT','', 'Numéro a assigner à la prochaine version de SADU (sans SNAPSHOT)')
//         stringParam ('MESSAGE', 'Nouveau tag ${VERSION_TAG} par Jenkins', 'Numéro a assigner à la prochaine version de SADU (sans SNAPSHOT)')
//     }
//     definition {
//         cpsScm {
//             scm {
//                 git{
//                 	remote{
//               			url('https://gitlab.forge.gouv.qc.ca/sipmi/sadu-panorama.git')
//           			}
//                 	branch ('origin/master')
//                 }
//             }
//             scriptPath('tag.Jenkinsfile')
//         }
//     }
// }

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
pipelineJob("${PIPELINE_LIVRAISON}") {
    description ('Livraison de Utilitaire-NAM')
    definition {
        cpsScm {
            scm {
                git{
                	remote{
              			url('https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git')
          			}
                	branch ('origin/master')
                }
            }
            scriptPath('delivery.Jenkinsfile')
        }
    }
}
