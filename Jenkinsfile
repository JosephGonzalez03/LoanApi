pipeline {
  agent {
    docker {
      image 'postman/newman_alpine33:5.2.0'
    }

  }
//   stages {
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
      steps {
        sh 'curl --location --request GET \'https://api.getpostman.com/environments/85a071d0-0382-499d-a27d-e8c701c9d0fe\' \
            --header \'X-Api-Key: PMAK-600b53d7f9fc0700308169cd-d3e42ef5fea34b2f33d3d93a91f607b2e7\' > env.json'
        sh 'curl --location --request GET \'https://api.getpostman.com/collections/58ad80b5-0e3d-4018-a0f4-f1bb12ae546b\' \
            --header \'X-Api-Key: PMAK-600b53d7f9fc0700308169cd-d3e42ef5fea34b2f33d3d93a91f607b2e7\' > contract_tests_collection.json'
        sh 'newman run contract_tests_collection.json -e env.json'
      }
    }
  }
}