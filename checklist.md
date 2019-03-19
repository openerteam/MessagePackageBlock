1. 推送信息为抓取并上传
2. 诸多文件注释未完善

maven 打包命令：mvn package -Dmaven.test.skip=true  
maven 依赖一并打包：mvn assembly:assembly -DskipTests

mvn deploy:deploy-file -DgroupId=cn.team -DartifactId=message -Dversion=2.0.1 -Dpackaging=jar -Dfile=/Users/magicbeans/Develop/Explore/Server/Block/MessagePackageBlock/target/message-2.0.1-SNAPSHOT.jar -Durl=http://maven.magic-beans.cn/nexus/content/repositories/thirdparty -DpomFile=/Users/magicbeans/Develop/Explore/Server/Block/MessagePackageBlock/pom.xml -DrepositoryId=thirdparty