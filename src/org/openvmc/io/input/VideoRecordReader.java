package org.openvmc.io.input;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.VideoCapture;
import org.openvmc.io.obj.HBMat;

public class VideoRecordReader extends RecordReader<Text, HBMat> {

	private long start;	
	private long pos;
	private String filename;
	private long end;
    private  VideoCapture camera;
    protected Configuration conf;
    public static final Log LOG = LogFactory.getLog(VideoRecordReader.class);
    private MatOfByte frame = new MatOfByte();
    
    
	public VideoRecordReader(InputSplit split,TaskAttemptContext context) {
		try {
			
			initialize(split, context);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void close() throws IOException {
		camera.release();
	}
	
	
	public Class<?> getKeyClass() { return Text.class; }
	public Class<?> getValueClass() { return MatOfByte.class;}

	@Override
	public float getProgress() throws IOException {
		if (start == end) {
		    return 0.0f;
		} 
		else {
		    return Math.min(1.0f, (pos - start) / (float)(end - start));
		}
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		 return key;
	}

	@Override
	public HBMat getCurrentValue() throws IOException, InterruptedException {
		return value;
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		FileSplit sp = (FileSplit)split;
	    Path file = sp.getPath(); 
	    Configuration job = context.getConfiguration();
	    String fpath = System.getProperty("java.io.tmpdir")+new Date().getTime()+"-"+sp.getStart()+"-"+sp.getLength()+".avi";
	    FSDataInputStream fs = null;
	    FileOutputStream osw = null;
	    LOG.info("<><><>"+sp.toString()+"-"+sp.getStart()+"-"+sp.getLength());
	    try {
		    FileSystem fileSystem = file.getFileSystem(job);
		    fs = fileSystem.open(file);
		    fs.seek(sp.getStart());
	    	osw = new FileOutputStream(fpath);  
	    	IOUtils.copyBytes(fs,osw,sp.getLength(),false);
	    	osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeStream(fs);
			IOUtils.closeStream(osw);
		}
	    camera = new VideoCapture(fpath);
		
	}

	private Text key = new Text();
	private HBMat value = new HBMat();
	
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (camera.read(frame)){
        	pos++;
        	key = new Text(String.valueOf(pos));
            value.init(frame);
            return true;
        }else{
			return false;
		}
	}
}
