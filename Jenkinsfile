pipeline {
    agent any

    tools{
    maven 'maven_deploy'
    }

    stages {
//         stage("git") {
//             steps {
//                 git credentialsId: 'ssh-key-github', url: 'git@github.com:uskovvo/JenkinsTomcat.git'
//             }
//         }

        stage("build") {
            steps {
                sh "mvn clean install"
            }
        }

        stage("deploy") {

            steps{

               deploy adapters: [tomcat9(credentialsId: 'pass-val', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
            }
        }
    }
}