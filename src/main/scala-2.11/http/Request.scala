package http

/**
 * Created by yoone on 10/07/15.
 */
class Request {
  var method: String = null
  var POST: Map[String, String] = Map()
  var FILES: Map[String, Array[Byte]] = Map()
  var vars: Map[String, AnyRef] = Map()
}
