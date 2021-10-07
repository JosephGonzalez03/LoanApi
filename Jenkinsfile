pipeline {
    agent any
    environment {
        GIT_COMMIT_SUBJECT = sh(
            script: "git log --pretty=format:'%s' -n 1 ${GIT_COMMIT}"
            returnStdout: true
        )
    }
    stages {
        stage('Lint API Specification') {
            steps {
                sh 'docker run --rm \
                    -v $WORKSPACE/src/main/resources/spec/:/spec \
                    redocly/openapi-cli lint openapi.yaml'
            }
        }

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

        stage('Tag Docker Image & Publish to Docker Hub') {
            environment {
                DOCKER_CREDENTIALS = credentials('docker_credentials')
            }
            steps {
                sh '''
                    ${GIT_COMMIT_SUBJECT}
                    api_image=${JOB_NAME%/*}
                    tagged_api_image=josephgonzalez03/$api_image:${BRANCH_NAME}

                    docker-compose build
                    docker tag $api_image $tagged_api_image
                    echo ${DOCKER_CREDENTIALS_PSW} | docker login -u ${DOCKER_CREDENTIALS_USR} --password-stdin
                    docker push $tagged_api_image
                    docker logout
                    docker rmi $api_image $tagged_api_image
                '''
            }
        }
    }
}
