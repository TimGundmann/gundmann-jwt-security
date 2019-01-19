node {

    def server
    def buildInfo
    def rtMaven
    
    
   def mvnHome
   stage('Preparation') { 
      git 'https://github.com/TimGundmann/gundmann-jwt-security.git'
      env.JAVA_HOME="${tool 'jdk 9'}"
      env.PATH="/var/lib/jenkins/.local/bin:${env.PATH}"
      mvnHome = tool 'maven 3.3.9'
   }
   stage("version update") {
       pom = readMavenPom file: 'pom.xml'
       newVersion = pom.version + "." + env.BUILD_NUMBER
       sh "'${mvnHome}/bin/mvn' versions:set -DnewVersion=${newVersion}"
       currentBuild.displayName = newVersion
       
        server = Artifactory.server 'gundmannArtifactory'

        rtMaven = Artifactory.newMavenBuild()
        rtMaven.tool = 'maven 3.3.9'
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        rtMaven.deployer.deployArtifacts = false // Disable artifacts deployment during Maven run

        buildInfo = Artifactory.newBuildInfo()
   }
   stage('Build') {
   	 rtMaven.run pom: 'pom.xml', goals: 'clean package', buildInfo: buildInfo
   }
   stage('Results') {
//      junit '**/target/surefire-reports/TEST-*.xml'
//      archive 'target/*.jar'
   }
   
   stage ('Publish build info') {
        server.publishBuildInfo buildInfo
   }
}