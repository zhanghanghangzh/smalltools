package com.hang.smalltools.execommander;

import java.io.BufferedReader;
import java.io.File;

public class ExeCommand 
{
	private Runtime rn;
	private Process process;
	private BufferedReader outputBufferedReader;
	private BufferedReader errorBufferedReader;
	ExeCommandInputStreamTask outputStreamTask;
	ExeCommandInputStreamTask errorStreamTask;
	
	public static void main(String[] args) 
	{
		ExeCommand e=new ExeCommand("E:/eclipseNew/aa/WebRoot/gcc/MinGW/bin/gcc.exe -S -save-temps -w D:/haha/00000/test1c.c",new String []{"include = E:/eclipseNew/aa/WebRoot/gcc/MinGW/include", "lib = E:/eclipseNew/aa/WebRoot/gcc/MinGW/lib", "SystemRoot = c:/Windows"},"D:/haha/00000");
		e.startWatchTask();
		e.waitFor();
		System.out.println(e.getAllOutPutInfo());
		System.out.println(e.getAllErrorOutPut());
		e.closeCommand();
	}
	
	public ExeCommand(String command,String [] env,String filePath) 
	{
		File file = new File(filePath);
		rn = Runtime.getRuntime();
		try 
		{
			setProcess(rn.exec(command,env,file));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public int waitFor()
	{
		try 
		{
			return getProcess().waitFor();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		return 0;
	}
	public void startWatchTask()
	{
		outputStreamTask=new ExeCommandInputStreamTask(getProcess().getInputStream());
		errorStreamTask=new ExeCommandInputStreamTask(getProcess().getErrorStream());
		new Thread(outputStreamTask).start();
		new Thread(errorStreamTask).start();
	}
	
	private void closeErrorOutputInfo()
	{
		try 
		{
			if(errorBufferedReader!=null)
			{
				errorBufferedReader.close();
				errorBufferedReader=null;
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getAllErrorOutPut()
	{
		return errorStreamTask.getOutput();
	}
	
	public String getAllOutPutInfo()
	{
		return outputStreamTask.getOutput();
	}
	
	private void closeOutputInfo()
	{
		try 
		{
			if(outputBufferedReader!=null)
			{
				outputBufferedReader.close();
				outputBufferedReader=null;
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void closeCommand()
	{
		closeOutputInfo();
		closeErrorOutputInfo();
	}
	
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
}
