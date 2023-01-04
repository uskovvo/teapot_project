pipeline {
    agent any

    tools{
    maven 'maven_deploy'
    }

    stages {
        stage("test") {
            steps {
                sh "mvn clean install"
            }
        }

//         stage("deploy") {
//
//             steps{
//
//                  deploy adapters: [tomcat9(credentialsId: 'remote-tomcat10', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
//             }
//         }
    }
}