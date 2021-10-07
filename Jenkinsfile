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
            }
        }

        stage('Report') {
            steps {
                junit 'target/surefire-reports/*.xml '
                archiveArtifacts 'target/*.jar'
            }
        }

        stage('Lint API Specification') {
            steps {
                sh 'docker run --rm \
                    -v $WORKSPACE/src/main/resources/spec/:/spec \
                    redocly/openapi-cli lint openapi.yaml'
            }
        }

        stage('Tag Docker Image & Publish to Docker Hub') {
            environment {
                DOCKER_CREDENTIALS = credentials('docker_credentials')
            }
            steps {
                sh 'api_name=${JOB_NAME%/JOB_BASE_NAME}'
                sh 'echo $api_name'
            }
        }
    }
}
