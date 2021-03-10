package com.github.eriendeng.sqlsubmit

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, SqlParserException, TableEnvironment}

import scala.collection.JavaConversions._

//entry: com.github.eriendeng.sqlsubmit.SqlSubmit
object SqlSubmit {

  def main(args: Array[String]) {

    if (args.length == 0) {
      sys.error("PLEASE INPUT SQL FILE NAME.")
      System.exit(-1)
    }
    val sqlFileName = args(0)
    val sqls = Configuration.getSQL(sqlFileName)

    val sqlCalls = SqlCommandParser.parse(sqls)

    val bsEnv = StreamExecutionEnvironment.getExecutionEnvironment
    val bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tableEnv = StreamTableEnvironment.create(bsEnv, bsSettings)

    for (call <- sqlCalls) {
      println("[SQL] " + call.toString)
      val callType = SqlCommandParser.callCommand(call)
      callType match {
        case "set" =>
          callSet(tableEnv, call)
        case "create_table" =>
          callCreateTable(tableEnv, call)
        case "insert_into" =>
          callInsertInto(tableEnv, call)
        case _ =>
          throw new RuntimeException("Unsupported command: " + call.command)
      }
    }
    bsEnv.execute("SQL Job")
    tableEnv.execute("SQL Job")
  }

  private def callSet(tEnv: TableEnvironment, cmdCall: SqlCommandParser.SqlCommandCall): Unit = {
    val key = cmdCall.operands(0)
    val value = cmdCall.operands(1)
    tEnv.getConfig.getConfiguration.setString(key, value)
  }

  private def callCreateTable(tEnv: TableEnvironment, cmdCall: SqlCommandParser.SqlCommandCall): Unit = {
    val ddl = cmdCall.operands(0)
    try tEnv.executeSql(ddl)
    catch {
      case e: SqlParserException =>
        throw new RuntimeException("SQL parse failed:\n" + ddl + "\n", e)
    }
  }

  private def callInsertInto(tEnv: TableEnvironment, cmdCall: SqlCommandParser.SqlCommandCall): Unit = {
    val dml = cmdCall.operands(0)
    try tEnv.executeSql(dml)
    catch {
      case e: SqlParserException =>
        throw new RuntimeException("SQL parse failed:\n" + dml + "\n", e)
    }
  }

}
