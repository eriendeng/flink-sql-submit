package com.github.eriendeng.sqlsubmit

import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import com.github.eriendeng.udf._

object UdfRegister {
  def register(tableEnv: StreamTableEnvironment): Unit = {
    tableEnv.createTemporarySystemFunction("UDF_WINDOW_TIMEZONE", new ParseTimestampTimezone)
    tableEnv.createTemporarySystemFunction("UDF_PARSE_UA", new ParseUserAgent)
  }
}
