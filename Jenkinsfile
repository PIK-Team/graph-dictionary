pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        checkout([
                    $class: 'GitSCM',
                    branches: scm.branches,
                    doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                    extensions: scm.extensions + [[$class: 'CloneOption', noTags: false, reference: '', shallow: false]],
                    submoduleCfg: [],
                    userRemoteConfigs: scm.userRemoteConfigs
                  ])
          echo 'Building..'
          sh 'git tag'
          sh '''cd api
./gradlew clean build
cd ..'''
          sh '''cd api
./gradlew cV
git tag
./gradlew release -Prelease.disableChecks -Prelease.useHighestVersion  -Prelease.customKeyFile="/var/jenkins_home/.ssh/versioning_PEM_ssh"
git tag
./gradlew cV
git tag
cd ..'''
        }
      }

      stage('Test') {
        steps {
          echo 'Testing..'
        }
      }

      stage('Static') {
        steps {
          echo 'Static analysis...'
          sh '''cd api
./gradlew sonarqube -Dsonar.projectKey=graph-dictionary -Dsonar.host.url=http://172.18.0.4:9000 -Dsonar.login=9cc460911f0a7a659c937c9c295d066abc799dac
cd ..'''
        }
      }

      stage('Deploy') {
        steps {
          echo 'Deploying...'
          sh '''cd api
./gradlew publish
cd ..'''
        }
      }

    }
  }