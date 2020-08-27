pipeline {
  agent {
    docker {
      image 'docker:19.03.12'
    }

  }
  stages {
    stage('Initialize') {
      steps {
        sh '''echo PATH=${PATH}
echo M2_HOME = ${M2_HOME}
docker --version
docker-compose up
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