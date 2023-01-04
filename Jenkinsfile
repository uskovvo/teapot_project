pipeline {
    agent {
        sshagent(['ssh-key-github']) {
                            sh 'ssh -o StrictHostKeyChecking=no -l cloudbees 35.227.146.153 valerii -a'
                        }
    }

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

//                deploy adapters: [tomcat9(credentialsId: 'run_app-deploy', path: '', url: 'http://192.168.0.200:8080')], contextPath: null, war: '**/*.war'
            }
        }
    }
}