package http

/**
 * Created by yoone on 10/07/15.
 */
class Request {
  var method: String = null
  var GET: Map[String, String] = Map()
  var POST: Map[String, String] = Map()
  var FILES: Map[String, PostFile] = Map()
  var vars: Map[String, AnyRef] = Map()
}
