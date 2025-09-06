pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Browser for test execution')
        choice(name: 'TEST_SUITE', choices: ['Smoke', 'Positive', 'Negative', 'Regression'], description: 'Test suite to execute')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "✅ Code checked out"
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean install -DskipTests'
                    } else {
                        bat 'mvn clean install -DskipTests'
                    }
                }
                echo "✅ Build completed"
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def testCmd = "mvn test -Dtest=${params.TEST_SUITE}TestRunner -Dbrowser=${params.BROWSER}"
                    if (isUnix()) {
                        sh testCmd
                    } else {
                        bat testCmd
                    }
                }
            }
            post {
                always {
                    publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/surefire-reports',
                        reportFiles: '*.html',
                        reportName: 'Surefire Test Results'
                    ])
                    publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'reports/extent',
                        reportFiles: 'index.html',
                        reportName: 'Extent Report'
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "📦 Archiving reports and logs..."
            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: 'reports/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: 'screenshots/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: 'logs/**/*', allowEmptyArchive: true
        }
    }
}
