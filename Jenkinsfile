#!groovy

properties(
  [[$class: 'GithubProjectProperty', displayName: 'http-proxy-spring-boot-autoconfigure', projectUrlStr: 'http://git.reform.hmcts.net/reform/http-proxy-spring-boot-autoconfigure/'],
   pipelineTriggers([[$class: 'GitHubPushTrigger']])]
)

@Library('Reform') _

node {
  try {
    stage('Checkout') {
      checkout scm
    }

    stage('Build') {
      sh '''
        ./gradlew clean build -x test
      '''
    }

    stage('Test') {
      sh '''
        ./gradlew test
      '''
    }

    if ("master" == "${env.BRANCH_NAME}") {
      stage('Install') {
        sh '''
          ./gradlew install
        '''
      }
    }
  } catch (err) {
    notifyBuildFailure channel: '#development'
    throw err
  }
}
