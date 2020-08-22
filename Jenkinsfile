pipeline {
  agent {
    docker {
      image 'maven:3.6.3-jdk-11'
      args '-v /Users/joe/.m2:/root/.m2'
    }

  }
  stages {
    stage('Initialize') {
      steps {
        sh '''echo PATH=${PATH}
echo M2_HOME = ${M2_HOME}
mvn clean'''
      }
    }

  }
}