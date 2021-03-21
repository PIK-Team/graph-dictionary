pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        sh './api/gradlew clean build'
      }
    }

    stage('Test') {
      steps {
        echo 'Testing..'
      }
    }

    stage('Deploy') {
      steps {
        echo 'Deploying....'
      }
    }

  }
}