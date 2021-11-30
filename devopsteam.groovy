properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
    pipelineTriggers([cron('*/15 * * * *'), 
    pollSCM('H * * * *')])
])

node {
    stage("Clone A Repository") {
        timestamps {
            checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/emirsway/jenkins-terraform-aws-resources.git']]])
        }
    }
    stage("Initialize"){
        timestamps {
            sh 'terraform init'
        }
    }
    stage("Run Script"){
        timestamps {
        sh label: '', script: 
		'''#!/bin/bash
			if [ ! -d /tmp/foo ]; 
			then
				echo "Folder not found!"
				echo "Creating a folder"
				mkdir -p "/tmp/foo"
			fi
		'''
        }
    }
    stage("Validate"){
        sh 'terraform validate'
    }
    stage("Plan"){
        sh 'terraform plan'
    }
    stage("Confirmation"){
        input 'Should I apply?'
    }
    stage("Apply"){
        sh 'terraform apply -auto-approve'
        echo 'running apply '
    }
    stage("Security Check"){
        sh label: '', script: 
		'''#!/bin/bash
			if [ ! -d /tmp/foo ]; 
			then
				echo "Folder not found!"
				echo "Creating a folder"
				mkdir -p "/tmp/foo"
			fi
		'''
    }
    stage("Wait"){
        sleep 3
    }
    stage("Email Notification to Team"){
        mail bcc: 'EC2-VPC Created', body: 'EC2-VPC Created is created in AWS', cc: 'EC2-VPC Created', from: 'emirmails@gmail.com', replyTo: 'emirmails@gmail.com', subject: 'EC2-VPC Created Build', to: 'emirmails@gmail.com'
    }
    stage("Send message to a Contractor"){
        mail bcc: '', body: '''Hi, VPC has been built Thanks''', cc: '', from: '', replyTo: '', subject: 'VPC being built', to: 'contractor@company.com'    }
    }
    stage("Confirmation"){
        input 'Should I Destroy?'
    }
    stage("Wait"){
        sleep 3
    }
    stage("Destroy"){
        sh 'terraform destroy -auto-approve'
        echo 'running destroy '
    }
