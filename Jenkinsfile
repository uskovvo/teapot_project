pipeline {
    agent any

    tools{
    maven 'maven_deploy'
    }

    stages {
        stage("build") {
            steps {
                sh "mvn -e clean install"
            }
        }

        stage("SSHsteps-deploy app") {
            steps{
                script {
                    withCredentials ([sshUserPrivateKey(credentialsId: 'remote-key10', keyFileVariable: 'keyFile', passphraseVariable: 'pass', usernameVariable: 'userName')]) {
                        def remote = [name: 'tomcat', host: '35.227.146.153', user: userName, identityFile: keyFile, allowAnyHosts: true]
                        sshCommand remote: remote, command: "ls -lrt"
                        sshPut remote: remote, from: 'target/teapot_project.war', into: '/opt/tomcat/webapps/'
                    }
                }
//                  deploy adapters: [tomcat9(credentialsId: 'remote-tomcat10', path: '', url: 'http://35.227.146.153:8080/')], contextPath: null, war: '**/*.war'
            }
        }
    }
}