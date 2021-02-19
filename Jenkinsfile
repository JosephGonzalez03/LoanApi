pipeline {
  agent any
  stages {
    stage('Run Unit Tests') {
      steps {
        sh 'docker run --rm \
            -v "$HOME/.m2":/root/.m2 \
            -v $WORKSPACE:/usr/maven/src/mymaven \
            -w /usr/maven/src/mymaven \
            maven mvn -Dmaven.test.failure.ignore=true package'
        //sh 'mvn -Dmaven.test.failure.ignore=true test'
      }
    }

    stage('Docker-Compose Setup') {
      steps {
        sh 'docker container prune --force'
        sh 'docker-compose build'
        sh 'docker-compose up -d'
        sh 'sleep 10s'
      }
    }
//     stage('Report') {
//       steps {
//         junit 'target/surefire-reports/*.xml '
//         archiveArtifacts 'target/*.jar'
//       }
//     }
    stage('Run Contract Tests') {
      environment {
        JENKINS_API_KEY = credentials('jenkins_api_key')
      }
      steps {
        sh "curl -L -X GET 'https://api.getpostman.com/environments/85a071d0-0382-499d-a27d-e8c701c9d0fe' \
            -H 'X-Api-Key: ${env.JENKINS_API_KEY}' > env.json"
        sh "curl -L -X GET 'https://api.getpostman.com/collections/58ad80b5-0e3d-4018-a0f4-f1bb12ae546b' \
            -H 'X-Api-Key: ${env.JENKINS_API_KEY}' > contract_tests_collection.json"
        sh 'docker run --rm --expose 8090 --network host -v $WORKSPACE:/etc/newman -t postman/newman_alpine33:5.2.0 \
            run "contract_tests_collection.json" \
            --environment="env.json" \
            --reporters="cli" \
            --color="off" \
            --disable-unicode'
      }
    }

    stage('Docker-Compose Cleanup') {
      steps {
        sh 'docker-compose down'
        sh 'docker container prune --force'
      }
    }
  }
}