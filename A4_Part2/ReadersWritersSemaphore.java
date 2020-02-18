import java.util.Random;
import java.util.concurrent.Semaphore;

// Readers-Writers with Writer Priority
//    (using wait-notify)

public class ReadersWritersSemaphore { 
	
	public static void main(String[] args) {

		Database d = new Database();

		Writer w1 = new Writer(d,1);
		Writer w2 = new Writer(d,10);
		Writer w3 = new Writer(d,100);
		Writer w4 = new Writer(d,1000);
		Writer w5 = new Writer(d,10000);
		Reader r1 = new Reader(d);
		Reader r2 = new Reader(d);
		Reader r3 = new Reader(d);
		Reader r4 = new Reader(d);
		Reader r5 = new Reader(d);
		Reader r6 = new Reader(d);
		Reader r7 = new Reader(d);
		Reader r8 = new Reader(d);
		Reader r9 = new Reader(d);
		Reader r10 = new Reader(d);

		w1.start();
		w2.start();
		w3.start();
		r1.start();
		r2.start();
		r3.start();
		r4.start();
		r5.start();
		w4.start();
		w5.start();
		r6.start();
		r7.start();
		r8.start();
		r9.start();
		r10.start();
	}
}

 
 class Reader extends Thread {
		Database d;
		public Reader(Database d) {
			this.d = d;
		}

		public void run() {
			for (int i = 0; i < 5; i++){
				d.request_read();
				System.out.println(d.read());
				d.done_read();
				
			}
		}
	}

 class Writer extends Thread {
		Database d;
		int x;

		public Writer(Database d, int x) {
			this.d = d;
			this.x = x;
		}

		public void run() {
			for (int i = 0; i < 5; i++) {
				try {Thread.sleep(new Random().nextInt(200));}
				catch (InterruptedException e) {}
				d.request_write();
				d.write(x);
				d.done_write();
			}
		}
	}

class Database {

	Semaphore s1 = new Semaphore(1);
	Semaphore s2_r = new Semaphore(0);
	Semaphore s2_w = new Semaphore(0);

	int data = 0;
	int r = 0;   // # active readers
	int w = 0;   // # active writers (0 or 1)
	int ww = 0;  // # waiting writers
	int wr = 0;  // # waiting readers
	

	public void request_read() {
		try {
		    s1.acquire();
		    while (w == 1 || ww > 0)
			try { 
			     	wr++;  
			      	s1.release();
			      	s2_r.acquire();
			      	s1.acquire(); 
			      	wr--; 
               	}   
		    catch (Exception e) {
			}
			r++;
			s1.release();
		    }
	        catch (Exception e) {
	        }
	}

	public void done_read() {
		try
		{
			s1.acquire();
			r--;
			s1.release();
		}
		catch ( Exception e) {			
		}
		
		int wwlen = s2_w.getQueueLength();
		if(wwlen>0) 
		{
			s2_w.release();
		}
		else
		{
			int wrlen = s2_r.getQueueLength();
			if(wrlen>0)
			{					
				s2_r.release(wrlen);
			}
		}
	}

	public void request_write() {
 		try
		{
			s1.acquire();
			while (r > 0 || w == 1) {
				try {
					ww++;  
					s1.release();
					s2_w.acquire();
					s1.acquire();
					ww--;
				}
				catch (Exception e) {}
				}
			w = 1;
			s1.release();
		}
		catch(Exception e) {
		}
	}

	public void done_write() {
		try
		{
			s1.acquire();
			w = 0;
			s1.release();
		}
		catch(Exception e) {
		}
		
		int wwlen = s2_w.getQueueLength();
		if(wwlen>0) 
		{
			s2_w.release();
		}
		else
		{
			int wrlen = s2_r.getQueueLength();
			if(wrlen>0)
			{					
				s2_r.release(wrlen);
			}
		}
	} 

	int read() {
		return data;
	}

	void write(int x) {
		data = data + x;
	}
}

