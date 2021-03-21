pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        withGradle()
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