pipeline {
    agent any

    stages {
        stage('Checkout') {
    steps {
        git credentialsId: 'github-opt1', url: 'https://github.com/naddri/OTP1IndividualTasks.git', branch: 'main'
    }
}

        stage('Build') {
            steps {
                dir('Jenkins') {
                    bat 'mvn clean install' // sh for linux and mac
                }
            }
        }

        stage('Test') {
            steps {
                dir('Jenkins') {
                    bat 'mvn test'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                dir('Jenkins') {
                    bat 'mvn jacoco:report'
                }
            }
        }

        stage('Publish Test Results') {
            steps {
                junit 'Jenkins/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                jacoco execPattern: 'Jenkins/target/jacoco.exec',
                       classPattern: 'Jenkins/target/classes',
                       sourcePattern: 'Jenkins/src/main'
            }
        }

        stage('Docker Build') {
            steps {
                dir('Jenkins') {
                    bat "docker build -t %IMAGE_NAME%:%BUILD_NUMBER% ."
                }
            }
        }

        stage('Docker Login & Push') {
            steps {
                bat 'echo %DOCKERHUB_CREDENTIALS_PSW% | docker login -u %DOCKERHUB_CREDENTIALS_USR% --password-stdin'
                bat "docker push %IMAGE_NAME%:%BUILD_NUMBER%"
            }
        }
    }
}
