pipeline {
  agent {
    docker {
      image 'maven:3.6.3-jdk-11'
      args '-v /home/joe/.m2:/root/.m2'
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

    stage('Build') {
      steps {
        sh 'mvn -Dmaven.test.failure.ignore=true install'
      }
    }

    stage('Report') {
      steps {
        junit 'target/sunfire-reports/**/*.xml '
        archiveArtifacts 'target/*.jar,target/*.hpi'
      }
    }

  }
}