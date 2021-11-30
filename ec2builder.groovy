

node {
    stage("Clone A Repository") {
        git branch: 'main', url: 'https://github.com/emirsway/terraform-august-cloud.git'
    }
    stage("Initialize"){
        timestamps {
            sh 'terraform init'
        }
    }
    stage("Validate"){
        sh 'terraform validate'
        }
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
        sh 'terraform refresh'
    }
    stage("Wait"){
        sleep 5
    }
    stage("Email Notification to Team"){
        mail bcc: 'EC2', body: 'EC2 is created in AWS', cc: 'EC2', from: '', replyTo: '', subject: 'EC2 Build', to: 'emirmails@gmail.com'
    }
    stage("Send message to a Contractor"){
        mail bcc: '', body: '''Hi, VPC has been built Thanks''', cc: '', from: '', replyTo: '', subject: 'VPC being built', to: 'contractor@company.com'    }
}