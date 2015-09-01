package org.openvmc.io.obj;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;
import org.opencv.core.MatOfByte;

public class HBMat implements Writable, Serializable{

	private static final long serialVersionUID = 1L;
	private MatOfByte bmat;
	
	public HBMat(){ }
	
	 public HBMat(MatOfByte bmat)
	  {
	    this.bmat = bmat;
	  }

	@Override
	public void readFields(DataInput in) throws IOException {
		int size = WritableUtils.readVInt(in);
		byte[] b = new byte[size];
		in.readFully(b);
		bmat  = new MatOfByte(b);
	}

	@Override
	public void write(DataOutput out) throws IOException {
			byte[] mbyte = bmat.toArray();
			WritableUtils.writeVInt(out, mbyte.length);
		    out.write(mbyte);
	}

	public void init(MatOfByte frame) {
		this.bmat = frame;
	}

	public MatOfByte getBmat() {
		return bmat;
	}

	public void setBmat(MatOfByte bmat) {
		this.bmat = bmat;
	}
}
