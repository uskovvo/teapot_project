pipeline {
    agent any

    tools{
    maven 'maven_deploy'
    }

    stages {

        stage("build") {
            steps {
                sh "mvn -DskipTests clean install"
                step([$class: 'GitHubCommitStatusSetter'])
            }
        }

        stage("SSHsteps-deploy app") {
            steps{
                 deploy adapters: [tomcat9(credentialsId: 'remote-tomcat10', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
                 step([$class: 'GitHubCommitStatusSetter'])
            }
        }

        stage("git co"){
            steps{
                step([$class: 'GitHubCommitStatusSetter', contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: 'Job is done']])
//                 step([$class: 'GitHubCommitStatusSetter'])
//                 step([$class: 'GitHubSetCommitStatusBuilder', contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: 'Fuck'], statusMessage: [content: 'We here!']])
            }
        }
    }
}