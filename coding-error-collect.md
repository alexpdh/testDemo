#### coding日常错误整理

* error

> Information:java: javacTask: 源发行版 1.8 需要目标发行版 1.8
Information:java: Errors occurred while compiling module 'project name'
Information:javac 1.8.0_92 was used to compile java sources
Information:2017/8/2 23:06 - Compilation completed with 1 error and 0 warnings in 2s 136ms
Error:java: Compilation failed: internal java compiler error

* resolvent

> 选中项目，右击选择Maven-->Reimport, 再次编译可以解决。或者 setting->Compiler->java Compiler 设置相应Module的target byte code version的合适版本。一劳永逸的办法，在 pom 文件中添加编译插件：

``` java 
  <build>
    <plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>2.3.2</version>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
      </plugin>
    </plugins>
  </build>

```