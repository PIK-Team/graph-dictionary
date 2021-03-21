pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Building..'
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

    stage('Scan') {
      steps {
        withSonarQubeEnv(installationName: 'sonarqube', credentialsId: '5a8bcafacd44a848184dd03e7130a91cef5e8aa3') {
          waitForQualityGate(abortPipeline: true, credentialsId: '5a8bcafacd44a848184dd03e7130a91cef5e8aa3')
        }

      }
    }

  }
}