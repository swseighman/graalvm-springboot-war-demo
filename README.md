## GraalVM Springboot WAR Demo on Wildfly


A GraalVM benchmarking test using Spring Boot WAR deployment on any Application Server that already support Servlet Speciftion 4.0 and above such as Weblogic 14c, Tomcat 9, Wildfly 14 and above, etc.

This particular version is specific to Wildfly.

Credit goes to Marthen Luther, this is a fork of his original project.

-

Download and install Wildfly [(Link)](https://download.jboss.org/wildfly/21.0.2.Final/wildfly-21.0.2.Final.zip)

From the root Wildfly directory, start the Wildfly server:

```
$ bin/standalone.sh
```

```
$ cd graalvm-springboot-war-demo
```
Make certain you’re using Java 8 Hotspot VM:```
$ sdkman use java 8.0.271-oracle$ java -versionjava version "1.8.0_271"Java(TM) SE Runtime Environment (build 1.8.0_271-b09)Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)
```


If prefer to use `wrk` to perform the benchmark tests, download and install `wrk2`.

```
$ sudo dnf -y groupinstall 'Development Tools' <--(Optional)
$ sudo dnf -y install openssl-devel git
$ git clone https://github.com/giltene/wrk2.git
$ cd wrk2
$ make
# Move the executable to /usr/bin
$ sudo cp wrk /usr/bin
```
Depending on which benchmark you choose, you can edit the `test.sh` file and comment out `curl` or `wrk`.  Here I've removed the `wrk` benchmark and will only run the `curl` test:

```
#!/bin/bash

# Valid argumuments are wls, tomcat, websphere, wildfly, and jetty. 

# check for argument
if [ "$#" -eq 1 ] 
then
   if [ $1 == "tomcat" ] 
   then
      echo "Testing will be done from Tomcat application server. Make sure GraalVMJAXRSDemo application is already deployed."
      appURL="http://localhost:8080/graalvm-springboot-demo-1/graal/benchmark/20000000"
   elif [ $1 == "websphere" ]
   then
      echo "Testing will be done from WebSphere Liberty Profile  application server. Make sure GraalVMJAXRSDemo application is already deployed."
      appURL="http://localhost:9080/graalvm-springboot-demo-1/graal/benchmark/20000000"
   elif [ $1 == "wildfly" ]
   then
      echo "Testing will be done from Wildfly application server. Make sure GraalVMJAXRSDemo application is already deployed."
      appURL="http://localhost:8080/graalvm-springboot-demo-1/graal/benchmark/20000000"
   elif [ $1 == "jetty" ]
   then
      echo "Testing will be done from Jetty application server. Make sure GraalVMJAXRSDemo application is already deployed."
      appURL="http://localhost:8080/graalvm-springboot-demo-1/graal/benchmark/20000000"
   elif [ $1 == "wls" ]
   then
      echo "Testing will be done from WebLogic application server. Make sure GraalVMJAXRSDemo application is already deployed."
      appURL="http://localhost:7001/graalvm-springboot-demo-1/graal/benchmark/20000000"
   fi
else
   # None of the above conditions are met, then default app URL is wls (WebLogic)
   echo "Testing will be done from WebLogic application server. Make sure GraalVMJAXRSDemo application is already deployed."
   appURL="http://localhost:7001/graalvm-springboot-demo-1/graal/benchmark/20000000"
fi

echo "The URL will be tested is >> $appURL"
echo " "
# echo "This test script is by default will use wrk2, but if wrks is not installed it will then execute using curl"
# echo " "


# if type wrk 2>/dev/null; then 
#   echo "==> Using wrk2"
#   echo "wrk is present in the system. Testing will continue using wrk"
#   echo "launching wrk using this options: wrk -t2 -c10 -d300s -R200 --latency"
#   wrk -t2 -c10 -d300s -R200 --latency http://localhost:8080/graalvm-springboot-demo-1/graal/benchmark/20000000
# else
   echo "==> Using curl"
   echo "10 curl testings, each with 20,000,000 (20 millions) iterations and followed by 0.5 second sleep"
   for i in {1..10}
   do
echo "Test: $i"
        time curl $appURL
sleep 0.5
   done
# fi
```

Run the benchmark test(s):

```
$ ./test.sh wildfly
```

Stop the Wildfly server.

Switch to the GraaLVM Enterprise Edition 8 VM:```
$ sdkman use java 8.0.271-oracle$ java -versionjava version "1.8.0_271"Java(TM) SE Runtime Environment (build 1.8.0_271-b09)Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)
```
Start Wildfly again:

```
$ bin/standalone.sh
```
Run the benchmark test(s) again:

```
$ ./test.sh wildfly
```
