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
./gradlew sonarqube
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