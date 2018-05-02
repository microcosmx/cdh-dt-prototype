
rest url:
http://rest-service-analysis:16602/greeting?cal=50
return json:
{"id":2,"result":false}


run:
java -jar target/rest-service-analysis-0.1.0.jar

dtrace run:
su root
/Users/admin/work/workspace_jvm/java-source-samples/sample/dtrace/hotspot/method_invocation_tree.d -c "java -jar /Users/admin/work/workspace_microservices/1-services/rest-service-analysis/target/rest-service-analysis-0.1.0.jar"
/Users/admin/work/workspace_jvm/java-source-samples/sample/dtrace/hotspot/method_invocation_tree.d -p 20985