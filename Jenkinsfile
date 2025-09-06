pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Browser for test execution')
        choice(name: 'TEST_SUITE', choices: ['smoke', 'positive', 'negative', 'regression'], description: 'Test suite to execute')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Code checked out"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
                echo "Build completed"
            }
        }

        stage('Run Tests') {
            steps {
                sh "mvn test -Dtest=${params.TEST_SUITE}TestRunner -Dbrowser=${params.BROWSER}"
            }
            post {
                always {
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'reports',
                        reportFiles: '*.html',
                        reportName: 'Test Results'
                    ])
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: 'reports/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: 'screenshots/**/*', allowEmptyArchive: true
        }
    }
}