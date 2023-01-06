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

        stage("Test") {
            steps {
                sh "mvn clean"
                sh "mvn test"
            }
        }

        stage("Deploy app") {
            steps{
                 deploy adapters: [tomcat9(credentialsId: 'remote-tomcat10', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
            }
        }


        stage("Git commit"){
            steps{
                step([$class: 'GitHubCommitStatusSetter', contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: 'Job is done']])
            }
        }
    }
}