import sbt._

class Project(info: ProjectInfo) extends ParentProject(info) {
  val akkaRepo = "akka" at "http://akka.io/repository/"
  val guiceyFruitRepo = "GuiceyFruit Repo" at "http://guiceyfruit.googlecode.com/svn/repo/releases/"
  
  def akkaDep = "se.scalablesolutions.akka" % "akka-remote" % "1.3-RC1"
  
  lazy val commonDep = project("common", "Common", new CommonProject(_))
  lazy val serverDep = project("server", "Server", new ServerProject(_))
  lazy val clientDep = project("client", "Client", new ClientProject(_))
  
  class CommonProject(info: ProjectInfo) extends DefaultProject(info) {
    override def compileOptions = super.compileOptions ++ compileOptions("-unchecked") 
    
    val akka = akkaDep
  }
  
  class ServerProject(info: ProjectInfo) extends DefaultProject(info) with assembly.AssemblyBuilder {
    override def compileOptions = super.compileOptions ++ compileOptions("-unchecked") 
    
    val common = commonDep
    //val akka = akkaDep
  }
  
  class ClientProject(info: ProjectInfo) extends DefaultProject(info) with assembly.AssemblyBuilder {
    override def compileOptions = super.compileOptions ++ compileOptions("-unchecked") 
    
    val common = commonDep
    //val akka = akkaDep
  }
}
