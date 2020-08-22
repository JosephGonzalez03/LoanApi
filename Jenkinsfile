pipeline {
  agent {
    docker {
      image 'maven:3.6.3-jdk-11'
      args '-v "$HOME"/.m2:/root/.m2 -v "$PWD":/usr/src/mymaven -v "$PWD"/target:/usr/src/mymaven/target -w /usr/src/mymaven maven mvn clean package '
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
        junit 'target/surefire-reports/*.xml '
        archiveArtifacts 'target/*.jar,target/*.hpi'
      }
    }

  }
}