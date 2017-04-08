package edu.njust.four;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
/**
 * 
 * @author sampson
 * 管道通信 
 * PipedOutputStream PipedInputStream 面向字节 
 * PipedReader PipedWriter 面向字符
 */
public class Piped {
	public static void main(String[] args) throws IOException {
		PipedReader reader=new PipedReader();
		PipedWriter writer=new PipedWriter();
		reader.connect(writer);
		
		Thread thread=new Thread(new Print(reader));
		thread.start();
		int re=0;
		try {
			while((re=System.in.read())!=-1){
				writer.write(re);
			}
		} catch (Exception e) {
			// TODO: handle exception
			writer.close();
		}
	}
	static class Print implements Runnable{
		private PipedReader read;
		public  Print(PipedReader in) {
			this.read=in;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int receive=0;
			try {
				while((receive=read.read())!=-1){
					System.out.print((char)receive);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
