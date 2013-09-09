package com.hang.smalltools.execommander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExeCommandInputStreamTask implements Runnable
{
	InputStream is;
	StringBuilder output = new StringBuilder();

	boolean debug = false;

	public ExeCommandInputStreamTask(InputStream is) {
		this.is = is;
	}
	public void run()
	{
		try 
		{
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) 
			{
				output.append(line);
				output.append("\n");
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public String getOutput() 
	{
		return output.toString();
	}

}
