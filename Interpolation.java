// Darren Cattle 
// Interpolation.java
// Adds interpolated points between set points based on time, useful for hardware loggers
// Student Version 
import java.io.*;

public class Interpolation
{
	public static void main (String args[]) throws IOException
	{
		String inString, hourStr, minStr, varStr;
		File f1 = new File("Solarjava.txt");
		File f2 = new File("Interpole.txt");
		f2.createNewFile();
		//Initialize Variables
		int part=0;
		int lines=200;
		int[] var=new int[lines];
		int[] hour=new int[lines];
		int[] min=new int[lines];
		int minformat=0;
		int line=0;
		int n=12;
		double time=0;
		boolean hourpass=false;
		//if input exists
		if (f1.exists())
		{
			//streams
			FileReader inFile=new FileReader(f1);
			BufferedReader inStream=new BufferedReader(inFile);	
			BufferedWriter outStream=new BufferedWriter(new FileWriter(f2));

		    while((inString=inStream.readLine())!=null)
		    {
		    	part=0;hourStr="";minStr="";varStr="";
		    	for(int i=0;i<inString.length();i++)
		    	{
		    		if(part==0){
						if(inString.charAt(i)!=':'){
							hourStr+=inString.charAt(i);}
		    			else{
		    				part++;
		    				i++;
		    				hour[line]=Integer.parseInt(hourStr);
		    			}
		    		}
		    		if(part==1){
		    			if(inString.charAt(i)!='\t'){
		    				minStr+=inString.charAt(i);}
		    			else{
		    				part++;
		    				i++;
		    				min[line]=Integer.parseInt(minStr);
		    			}
		    		}
		    		if(part==2){
		    			varStr=inString.substring(i,inString.length());
		    			var[line]=Integer.parseInt(varStr);
		    			i=inString.length();
		    		}
		    	}
		    	//console
		    	System.out.println(hour[line]+" "+min[line]+" "+var[line]+" "+line);
		    	line++;
		    }
		    inStream.close();

		    // print OUTPUT
		    for (int i = 0; i < line - 1; i++) { // for each entry
				int h1 = hour[i], h2 = hour[i+1];
				int m1 = min[i], m2 = min[i+1];
				int v1 = var[i], v2 = var[i+1];

				int time1 = h1 * 60 + m1;
				int time2 = h2 * 60 + m2;

				int timeDiff = time2 - time1;
				double valDiff = v2 - v1;

				double curVal = (double) v1;
				for (int j = 0; j < timeDiff; j++) { // for each minute

					int curTime = time1 + j;
					int thisHour = curTime / 60;
					int thisMin = curTime % 60;

					for (int k = 0; k < n; k++) { // for each 5 seconds
						curVal += valDiff / (n * timeDiff);
						int terror = (int) Math.round(curVal);
						String m = (thisMin < 10) ? "0" : "";
						m = m + thisMin;

						outStream.write(thisHour + ":" + m + "\t" + terror);
						outStream.newLine();
					}
				}




				// divide by this

				// for (int j = 0; j < timeDiff; j++) {

				// 	int curTime = time1 + j;
				// 	int thisHour = curTime / 60;
				// 	int thisMin = curTime % 60;

				// 	for (int k = 0; k < n; k++) {
				// 		// time is the same here
				// 		double val = v1 + valDiff*(k/n + j)*(1/timeDiff);
				// 		int intVal= (int) val;

				// 		String m = (thisMin < 10) ? "0" : "";

				// 		//todo: convert thisMin to string
				// 		m = m + thisMin;

				// 		outStream.write(thisHour + ":" + m + "\t" + intVal);
				// 		outStream.newLine();
				// 	}
				// }
			}


		    outStream.close();
		}
	}
}
