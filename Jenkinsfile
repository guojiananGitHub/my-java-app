pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        SONAR_PROJECT_KEY = 'my-java-app'
        SONAR_PROJECT_NAME = 'My Java App'
    }

    stages {
        // stage('Checkout') {
        //     steps {
        //         git 'https://github.com/你的用户名/my-java-app.git'
        //     }
        // }

        stage('Build & Unit Test') {
            steps {
                sh 'mvn clean compile test jacoco:report'
            }
        }

        stage('SpotBugs - Static Analysis') {
            steps {
                sh 'mvn spotbugs:check -Dspotbugs.failOnError=false || true'
            }
        }

        stage('OWASP Dependency-Check') {
            steps {
                sh 'mvn dependency-check:check -Ddependency-check.failBuildOnCVSS=9 || true'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        mvn sonar:sonar \
                          -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                          -Dsonar.projectName="${SONAR_PROJECT_NAME}" \
                          -Dsonar.java.binaries=target/classes \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                          -Dsonar.dependencyCheck.jsonReportPath=target/dependency-check/dependency-check-report.json \
                          -Dsonar.dependencyCheck.htmlReportPath=target/dependency-check/dependency-check-report.html \
                          -Dsonar.qualitygate.wait=true
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'

            recordIssues(
                tools: [
                    spotBugs(pattern: 'target/spotbugsXml.xml'),
                    dependencyCheck(pattern: 'target/dependency-check/dependency-check-report.xml')
                ],
                qualityGates: [
                    [threshold: 50, type: 'TOTAL', criticality: 'UNSTABLE'],
                    [threshold: 100, type: 'TOTAL', criticality: 'FAILURE']
                ]
            )

            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/site/jacoco',
                reportFiles: 'index.html',
                reportName: 'Coverage Report'
            ])

            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/dependency-check',
                reportFiles: 'dependency-check-report.html',
                reportName: 'Dependency-Check Report'
            ])
        }
    }
}