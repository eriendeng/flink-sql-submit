package com.github.eriendeng.sqlsubmit

import java.io.BufferedInputStream
import java.util.Properties

import scala.io.Source

object Configuration {
  def loadProps(): Properties = {
    val props = new Properties
    props.load(new BufferedInputStream(getClass.getResourceAsStream("/app.properties")))
    props
  }

}
