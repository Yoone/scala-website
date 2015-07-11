package http

import java.io.File
import java.util.UUID

/**
 * Created by mecavity on 10/07/15.
 */
class PostFile {
  var tmpPath: String = null
  var fileName: String = null
  var contentType: String = null

  def generateTempPath (): Unit = {
    assert(fileName != null, "Filename should not be null at this point")

    tmpPath = sys.props.get("java.io.tmpdir").get + "/scala-website-uploads/"
    val dir = new File(tmpPath)
    if (!dir.exists()) {
      assert(dir.mkdir(), "Could not create tmp directory")
    }

    tmpPath += UUID.randomUUID() + ".tmp"
  }
}
