pipeline {
    agent any

    tools{
    maven 'maven_deploy'
    }

    stages {

        stage("Build") {
            steps {
                sh "mvn -DskipTests clean install"
            }
        }

        stage("Deploy app") {
            steps{
                 deploy adapters: [tomcat9(credentialsId: 'remote-tomcat10', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
            }
        }

        stage('SonarQube Analysis') {

            withSonarQubeEnv() {
              sh "maven_deploy/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=test_project"
            }
        }
    }
}