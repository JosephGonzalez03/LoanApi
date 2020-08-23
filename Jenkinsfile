pipeline {
  agent {
    docker {
      image 'maven:3.6.3-jdk-11'
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

    stage('Run Unit Tests') {
      steps {
        sh 'mvn -Dmaven.test.failure.ignore=true test'
      }
    }

    stage('Build Image') {
      steps {
        sh 'mvn -DskipTests spring-boot:build-image'
      }
    }

    stage('Report') {
      steps {
        junit 'target/surefire-reports/*.xml '
        archiveArtifacts 'target/*.jar'
      }
    }

  }
}