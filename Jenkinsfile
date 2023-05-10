pipeline {
    environment{
        DOCKERHUB_CREDENTIALS = credentials('dockerhubcredentials')
        DOCKERHUB_USER = 'lax98'
    }
    agent any
    stages {
        stage('Clone repository') {
            steps {
                git branch: 'master', url: 'https://github.com/ShashidharNagaral/SplitwiseApplication'
            }
        }
        stage('Maven Build SplitwiseRegistryService'){
            steps{
                echo 'Building Job'
                sh 'cd SplitwiseRegistryService; mvn clean install';
                sh 'mv -f SplitwiseRegistryService/target/SplitwiseRegistryService-0.0.1-SNAPSHOT.jar JarFiles/';
            }
        }
        stage('Maven Build SplitwiseAPIGateway'){
            steps{
                echo 'Building Job'
                sh 'cd SplitwiseAPIGateway; mvn clean install';
                sh 'mv -f SplitwiseAPIGateway/target/SplitwiseAPIGateway-0.0.1-SNAPSHOT.jar JarFiles/'
            }
        }
        stage('Maven Build SplitwiseUserService'){
            steps{
                echo 'Building Job'
                sh 'cd SplitwiseUserService; mvn clean install';
                sh 'mv -f SplitwiseUserService/target/SplitwiseUserService-0.0.1-SNAPSHOT.jar JarFiles/';
            }
        }
        stage('Maven Build SplitwiseExpenseService'){
            steps{
                echo 'Building Job'
                sh 'cd SplitwiseExpenseService; mvn clean install';
                sh 'mv -f SplitwiseExpenseService/target/SplitwiseExpenseService-0.0.1-SNAPSHOT.jar JarFiles/';
            }
        }
        stage('Build Image for Microservices'){
            steps{
                echo 'Building docker Image'
                sh "docker build -t $DOCKERHUB_USER/splitwise:eurekaregistry -f DockerFiles/RegistryDockerfile .";
                sh "docker build -t $DOCKERHUB_USER/splitwise:apigateway -f DockerFiles/APIGatewayDockerfile .";
                sh "docker build -t $DOCKERHUB_USER/splitwise:userservice -f DockerFiles/UserServiceDockerfile .";
                sh "docker build -t $DOCKERHUB_USER/splitwise:expenseservice -f DockerFiles/ExpenseServiceDockerfile .";
                sh "cd splitwisefrontend; docker build -t ${DOCKERHUB_USER}/splitwise:splitwisefrontend .";   
            }
        }
    
        stage('Login into docker hub'){
            steps{
                echo 'Login into docker hub'
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin';
            }
        }
        stage('Push Image to DockerHub'){
            steps{
                echo 'Pushing Images into DockerHub'
                sh 'docker push $DOCKERHUB_USER/splitwise:eurekaregistry';
                sh 'docker push $DOCKERHUB_USER/splitwise:apigateway';
                sh 'docker push $DOCKERHUB_USER/splitwise:userservice';
    	        sh 'docker push $DOCKERHUB_USER/splitwise:expenseservice';
    	        sh 'docker push $DOCKERHUB_USER/splitwise:splitwisefrontend';
            }
        }
        stage('Delete Image from localsystem'){
            steps{
                echo 'Deleting Docker Image in localsystem'
                sh 'docker rmi $DOCKERHUB_USER/splitwise:eurekaregistry';
                sh 'docker rmi $DOCKERHUB_USER/splitwise:apigateway';
                sh 'docker rmi $DOCKERHUB_USER/splitwise:userservice';
                sh 'docker rmi $DOCKERHUB_USER/splitwise:expenseservice';
                sh 'docker rmi $DOCKERHUB_USER/splitwise:splitwisefrontend';
            }
        }
        stage('Run ansible playbook'){
            steps{
                echo 'Running the ansible playbook yml file'
                sh 'export LC_ALL=en_IN.UTF-8;export LANG=en_US.UTF-8;ansible-playbook -i inventory playbook.yml'
            }
        }
    }
}