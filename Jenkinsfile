pipeline {
  agent any

  tools {
    jdk 'jdk-21'
    maven 'maven-3'
  }

  parameters {
    choice(name: 'ENV', choices: ['qa', 'uat', 'stage', 'prod'], description: 'Target environment')
    choice(name: 'BROWSER', choices: ['chromium', 'firefox', 'webkit'], description: 'Browser')
    booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run headless')
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Install Browsers') {
      steps {
        sh 'mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps"'
      }
    }
    stage('Test') {
      steps {
        sh "mvn -B test -Denv=${params.ENV} -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS}"
      }
    }
    stage('Security Scan') {
      steps {
        sh 'mvn -B org.owasp:dependency-check-maven:check'
      }
    }
    stage('Allure Report') {
      steps {
        sh 'mvn -B allure:report'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/artifacts/**/*,target/site/allure-maven-plugin/**/*,target/dependency-check-report.*', allowEmptyArchive: true
      junit 'target/surefire-reports/*.xml'
    }
  }
}
