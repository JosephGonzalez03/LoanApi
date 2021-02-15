pipeline {
  agent any
  stages {
//     stage('Initialize') {
//       steps {
//         sh '''echo PATH=${PATH}
// echo M2_HOME = ${M2_HOME}
// docker --version
// mvn clean'''
//       }
//     }

//     stage('Run Unit Tests') {
//       steps {
//         sh 'mvn -Dmaven.test.failure.ignore=true test'
//       }
//     }
//
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
        sh 'docker run --network="host" --bind 0.0.0.0 -v $WORKSPACE:/etc/newman -t postman/newman_alpine33:5.2.0 \
            run "contract_tests_collection.json" \
            --environment="env.json" \
            --reporters="cli" \
            --color="off" \
            --disable-unicode'
      }
    }
  }
}