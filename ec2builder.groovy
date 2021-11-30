properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
    pipelineTriggers([cron('*/15 * * * *'), 
    pollSCM('* * * * *')])
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
    stage("Plan"){
        sh 'terraform plan'
    }
    stage("Confirmation"){
        //input 'Should I apply?'
        echo "Hello"
    }
    stage("Apply"){
        //sh 'terraform apply -auto-approve'
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
        sleep 10
    }
     stage("Email Notification to Team"){
        mail bcc: 'EC2', body: 'EC2 is created in AWS', cc: 'EC2', from: '', replyTo: '', subject: 'EC2 Build', to: 'emirmails@gmail.com'
    }
    stage("Send message to a Contractor"){
        mail bcc: '', body: '''Hi, VPC has been built Thanks''', cc: '', from: '', replyTo: '', subject: 'VPC being built', to: 'contractor@company.com'    }
}