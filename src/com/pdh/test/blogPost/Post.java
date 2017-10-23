/**
 * @Project Name:test
 * @File Name:Post.java
 * @Package Name:com.pengdh.test
 * @Date:2017年4月23日上午1:20:12
 */

package com.pdh.test.blogPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ClassName:Post
 * @Function:
 * @version
 *
 * @author pengdh
 * @date: 2017年4月23日 上午1:20:12
 */
public class Post {

  public static void main(String[] args) {

    String url = "http://data.zz.baidu.com/urls?site=alexpdh.com&token=GV8mCU1ZwOCPL9uB";// 网站的服务器连接
    String[] param = {
        // 需要推送的网址
//        		"http://alexpdh.com/",
//        		"http://alexpdh.com/categories/",
//        		"http://alexpdh.com/archives/",
//        		"http://alexpdh.com/tags/",
//        		"http://alexpdh.com/about/",
//        		"http://alexpdh.com/2017/04/21/decoretor-pattern/",
//        		"http://alexpdh.com/2017/04/10/design-pattern-six-principle/",
//        		"http://alexpdh.com/2017/04/07/java-generic-type/",
//        		"http://alexpdh.com/2017/04/06/git-operator/",
//        		"http://alexpdh.com/2017/03/30/strategy-pattern/",
//        		"http://alexpdh.com/2017/03/27/simple-factory-pattern/",
//        		"http://alexpdh.com/2017/03/25/uml-exmaple/",
//        		"http://alexpdh.com/2017/03/18/effective-java/",
//        		"http://alexpdh.com/2017/03/04/maven-repository/",
//        		"http://alexpdh.com/2017/02/25/Intellij-IDEA-course01/",
//        		"http://alexpdh.com/2017/02/18/2017-reading-plan/",
//        		"http://alexpdh.com/2017/02/11/jdkbug/",
//        		"http://alexpdh.com/2017/01/15/hexo-blog-building02/",
//        		"http://alexpdh.com/2017/02/05/http-Garbled/",
//        		"http://alexpdh.com/2017/04/23/proxy-pattern/",
//              "http://alexpdh.com/2017/05/03/log4j-properties-config/",
//              "http://alexpdh.com/2017/05/09/factory-method-pattern/",
//              "http://alexpdh.com/2017/05/14/prototype-pattern/",
//              "http://alexpdh.com/2017/05/21/template-method-pattern/",
//              "http://alexpdh.com/2017/05/28/facade-pattern/",
//              "http://alexpdh.com/2017/05/29/builder-pattern/",
//                "http://alexpdh.com/2017/05/30/observer-pattern/",
//        "http://alexpdh.com/2017/06/11/abstract-factory-pattern/",
//        "http://alexpdh.com/2017/06/17/state-pattern/",
//        "http://alexpdh.com/2017/06/24/adapter-pattern/",
//        "http://alexpdh.com/2017/06/26/springMVC-restful-api/",
        "http://alexpdh.com/2017/07/20/work-in-redis-use/",
        "http://alexpdh.com/2017/07/22/memento-pattern/",
        "http://alexpdh.com/2017/07/23/composite-pattern/",
        "http://alexpdh.com/2017/07/24/iterator-pattern/",
        "http://alexpdh.com/2017/07/30/singleton-pattern/",
        "http://alexpdh.com/2017/07/30/birdge-pattern/",
        "http://alexpdh.com/2017/08/01/command-pattern/",
        "http://alexpdh.com/2017/08/12/chain-of-responsibility-pattern/",
        "http://alexpdh.com/2017/08/13/mediator-pattern/",
        "http://alexpdh.com/2017/08/13/flyweight-pattern/",
        "http://alexpdh.com/2017/08/14/interpreter-pattern/",
        "http://alexpdh.com/2017/08/19/visitor-pattern/",
        "http://alexpdh.com/2017/09/02/springboot-websocket/",
        "http://alexpdh.com/2017/09/03/springboot-socketio/",
        "http://alexpdh.com/2017/09/16/jvm-memory/",
        "http://alexpdh.com/2017/09/17/java-memory-model-01/",
        "http://alexpdh.com/2017/10/11/vagrant-install-configure/"

    };
    String json = Post(url, param);// 执行推送方法
    System.out.println("结果是" + json); // 打印推送结果
  }

  /** * 百度链接实时推送 * * @param PostUrl * @param Parameters * @return */
  public static String Post(String PostUrl, String[] Parameters) {
    if (null == PostUrl || null == Parameters || Parameters.length == 0) {
      return null;
    }
    String result = "";
    PrintWriter out = null;
    BufferedReader in = null;
    try {
      // 建立URL之间的连接
      URLConnection conn = new URL(PostUrl).openConnection();
      // 设置通用的请求属性
      conn.setRequestProperty("Host", "data.zz.baidu.com");
      conn.setRequestProperty("User-Agent", "curl/7.12.1");
      conn.setRequestProperty("Content-Length", "83");
      conn.setRequestProperty("Content-Type", "text/plain");

      // 发送POST请求必须设置如下两行
      conn.setDoInput(true);
      conn.setDoOutput(true);

      // 获取conn对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      String param = "";
      for (String s : Parameters) {
        param += s + "\n";
      }
      out.print(param.trim());
      // 进行输出流的缓冲
      out.flush();
      // 通过BufferedReader输入流来读取Url的响应
      in = new BufferedReader(
          new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }

    } catch (Exception e) {
      System.out.println("发送post请求出现异常！" + e);
      e.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }

      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return result;
  }
}

