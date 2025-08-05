pipeline {
    agent { label 'docker' }

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/your-username/todo-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'docker build -t todo-app .'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            emailext to: 'team@example.com',
                     subject: '✅ Build Successful',
                     body: "Jenkins Build #${env.BUILD_NUMBER} succeeded!"
        }
        failure {
            emailext to: 'team@example.com',
                     subject: '❌ Build Failed',
                     body: "Jenkins Build #${env.BUILD_NUMBER} failed!"
        }
    }
}
