# HadoopCV
##OpenVMC-HadoopCV说明
 OpenVMC-HadoopCV 项目是OpenVMC项目的子集,希望能通过Hadoop,Spark高效读取视频内容,OpenVMC-HadoopCV包含:视频读取Hadoop接口封装，图像对象封装,图像算法处理封装等。
##整体架构
   视频文件->OpenCV->OpenCV JNI接口->->VideoRecordReader->VideoInputFormat->[Text,HBMat]->[Spark RDD/Hadoop MapReduce]->视频算法

##测试方法
1.配置 opencv-249.jar的 native library: data\opencv\

2.测试 org.openvmc.test.VideoReaderTest 测试 使用Java 是否能读取视频内容。

3.测试org.openvmc.spark.InputFormatTest 测试使用Spark 是否能读取视频内容。


##软件许可
 OpenVMC-HadoopCV 由中国大数据公司红象云腾（RedHadoop）发起并贡献于开源社区,项目遵循Apache 2.0 协议。
