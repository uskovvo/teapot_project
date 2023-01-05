pipeline {
    agent any

    tools{
    maven 'maven_deploy'
    }

    stages {
        stage("build") {
            steps {
                sh "mvn -DskipTests clean install"
            }
        }

        stage("test") {
            steps{
                sh 'mvn clean'
                sh "mvn test"
            }
        }

        stage("SSHsteps-deploy app") {
            steps{
                 deploy adapters: [tomcat9(credentialsId: 'remote-tomcat10', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
            }
        }

        stage("success from jenkins") {
            steps{
                publishChecks detailsURL: 'git@github.com:uskovvo/teapot_project.git', name: '1', summary: '2', text: '4', title: '3'
            }
        }
    }
}