pipeline {
    agent any

    environment {
        DB_URL = credentials('db-url')
        DB_USERNAME = credentials('db-username')
        DB_PASSWORD = credentials('db-password')
        APP_PASSWORD = credentials('app-password')
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Utiliza withSonarQubeEnv con el nombre de la configuración que has dado a SonarQube en Jenkins
                withSonarQubeEnv('SonarQube4AirePur') {
                    // Ejecuta el análisis de SonarQube
                    script {
                        def branchName = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
                        // Usa las variables de entorno SONAR_HOST_URL y SONAR_AUTH_TOKEN que son inyectadas por withSonarQubeEnv
                        sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=SonarQube4AirePur -Dsonar.projectName='SonarQube4AirePur' -Dsonar.host.url=http://sonarqube:9000 -Dsonar.token=${env.SONAR_AUTH_TOKEN} -Dsonar.branch.name=${branchName}'
                    }
                }
            }
        }
    }
}
