package com.github.eriendeng.udf

import org.apache.flink.table.functions.ScalarFunction
import eu.bitwalker.useragentutils.UserAgent

class ParseUserAgent extends ScalarFunction{
  //解析ua来源
  def eval(userAgent: String): String = {
    if (userAgent == null || userAgent == "") {
      return ""
    }
    if (userAgent.contains("miniProgram") && userAgent.contains("MicroMessenger")) {
      return "微信小程序"
    }
    if (userAgent.contains("IPH_SQ") || userAgent.contains("AND_SQ")) {
      return "手机QQ"
    }
    if (userAgent.contains("wxwork")) {
      return "企业微信"
    }
    if (userAgent.contains("MicroMessenger")) {
      return "微信"
    }
    val agent = UserAgent.parseUserAgentString(userAgent)
    val browser = agent.getBrowser
    browser.getGroup.toString
  }
}
