#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
    jdk 'JDK1.8.0_65'
    maven 'M3'
    }
        stages { 
	       stage ('Tests unitaires') { 
	            steps {
	            script { 
                   def mvnHome= tool name: 'M3', type: 'maven'
                   sh "${mvnHome}/bin/mvn clean test  "
                   }
                }
           }
       }
}
