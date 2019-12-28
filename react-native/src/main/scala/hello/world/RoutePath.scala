package hello.world

object RoutePath extends Enumeration {
  type RoutePath = Value
  val HOME, ANTD, REACTROUTER = Value

  final implicit class IPath(p: RoutePath) {

    def path: String = p match {
      case HOME        => "/"
      case ANTD        => "/antd"
      case REACTROUTER => "/react_router"
      case _           => throw new Error
    }

    def title: String = p match {
      case HOME        => "Home"
      case ANTD        => "Antd"
      case REACTROUTER => "React Router"
      case _           => throw new Error
    }

  }

  val allOrdered: List[RoutePath] = List(HOME, ANTD, REACTROUTER)

}
