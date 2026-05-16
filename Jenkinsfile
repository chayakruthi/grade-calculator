pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout') {
            steps {
                git ' https://github.com/chayakruthi/grade-calculator.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {

        success {
            emailext(
                subject: 'Jenkins Build SUCCESS',
                body: 'Maven project build completed successfully.',
                to: 'chayamanjappa@gmail.com'
            )
        }

        failure {
            emailext(
                subject: 'Jenkins Build FAILED',
                body: 'Maven project build failed. Check Jenkins console output.',
                to: 'chayamanjappa@gmail.com'
            )
        }
    }
}
