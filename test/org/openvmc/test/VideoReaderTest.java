package org.openvmc.test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class VideoReaderTest {

    public static void main(String[] args) {
    	
    	System.out.println(System.getProperty("java.class.path"));
	    System.out.println(System.getProperty("java.library.path"));
	    
	    
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	VideoCapture camera = new VideoCapture("data/bike.avi");
    	MatOfByte frame = new MatOfByte();
    	int i = 0;
    	
	    
    	while(true){
            if (camera.read(frame)){
                System.out.println("Frame Obtained");
                System.out.println("Captured Frame Width " +
                frame.width() + " Height " + frame.height());
                System.out.println(frame.dump());
                Highgui.imwrite("tmp\\image\\camera"+(i++)+".jpg", frame);
                //Highgui.imencode(ext, img, buf)
            }else{
            	break;
            }
        }
    	camera.release();
    	
    }

}
