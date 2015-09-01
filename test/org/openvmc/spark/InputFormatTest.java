package org.openvmc.spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.openvmc.io.input.VideoInputFormat;
import org.openvmc.io.obj.HBMat;

import scala.Tuple2;

public class InputFormatTest {
		public static void main(String[] args) {
			SparkConf conf = new SparkConf().setAppName("VideoInput").setMaster("local[2]");
			JavaSparkContext sc = new JavaSparkContext(conf);
			
			Configuration hc = new org.apache.hadoop.conf.Configuration();
			JavaPairRDD<Text, HBMat> video = sc.newAPIHadoopFile("data/bike.avi", VideoInputFormat.class, Text.class, HBMat.class,hc);
			
			video.foreach(new VoidFunction<Tuple2<Text,HBMat>>() {	
				@Override
				public void call(Tuple2<Text, HBMat> tuple) throws Exception {
					HBMat image = (HBMat)tuple._2;
					System.out.print(image.getBmat().dump());
				}
			});
			
			System.out.print(video.count());
		}
}
