pipeline {
    agent any

    tools{
    maven 'maven_deploy'
    }

    stages {
        stage("build") {
            steps {
                sh "mvn clean install"
                sh "mvn test"
            }
        }

        stage("deploy") {
            steps{
                 deploy adapters: [tomcat9(credentialsId: 'remote-tomcat10', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
            }
        }
    }
}