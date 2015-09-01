package org.openvmc.io.input;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.opencv.core.Core;
import org.openvmc.io.obj.HBMat;

public class VideoInputFormat extends FileInputFormat<Text, HBMat> {
	
	@Override
	public RecordReader<Text, HBMat> createRecordReader(InputSplit split,TaskAttemptContext ctx) throws IOException, InterruptedException {
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		return (RecordReader) new VideoRecordReader(split,ctx);
	}
	
}
