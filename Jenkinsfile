pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        sh '''cd api
./gradlew clean build
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
./gradlew sonarqube -Dsonar.projectKey=graph-dictionary -Dsonar.host.url=http://172.17.0.1:9000 -Dsonar.login=9cc460911f0a7a659c937c9c295d066abc799dac
cd ..'''
      }
    }

    stage('Deploy') {
      steps {
        echo 'Deploying...'
      }
    }

  }
}