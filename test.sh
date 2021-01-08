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
