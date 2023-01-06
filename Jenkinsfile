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
                 deploy adapters: [tomcat9(credentialsId: 'mich-tomcat', path: '', url: 'http://34.82.239.239:8080/')], contextPath: null, war: '**/*.war'
            }
        }

        stage("git co"){
            steps{
                step([$class: 'GitHubCommitStatusSetter', contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: 'Job is done']])
            }
        }
    }
}