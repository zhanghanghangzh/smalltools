package com.hang.smalltools;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator
{
	private String orignExp;
	private List<String> opList;
	private List<String> nibolanList;
	private Stack<String> opStack;
	private Stack<String> computeStack;
	public static void main(String[] args)
	{
		Calculator c=new Calculator();
		c.setExp("1+2*3-4*(1+2)");
		System.out.println("RES:"+c.computeRes());
		
	}
	public Calculator()
	{
		opList=new ArrayList<String>();
		nibolanList=new ArrayList<String>();
		opStack=new Stack<String>();
		computeStack=new Stack<String>();
	}
	
	public void setExp(String exp)
	{
		this.orignExp=exp;
		splitExp();
	}

	private void splitExp()
	{
		Pattern p=Pattern.compile("([0-9]+(\\.[0-9]+){0,1})|\\+|-|\\*|/|\\(|\\)");
		Matcher m=p.matcher(orignExp);
		while(m.find())
		{
			opList.add(m.group());
		}
	}
	
	public String computeRes()
	{
		getNibolan();
		return computeNibolan();
	}
	private String computeNibolan()
	{
		for(String op:nibolanList)
		{
			if(op.matches("([0-9]+(\\.[0-9]+){0,1})"))
			{
				computeStack.push(op);
			}
			else
			{
				computeStack.push(computeTwoOp(op,computeStack.pop(),computeStack.pop()));
			}
		}
		return computeStack.pop();
	}
	private String computeTwoOp(String op,String pop, String pop2)
	{
		double value1=Double.valueOf(pop2);
		double value2=Double.valueOf(pop);
		if(op.equals("+"))
		{
			return String.valueOf(value1+value2);
		}
		if(op.equals("-"))
		{
			return String.valueOf(value1-value2);
		}
		if(op.equals("*"))
		{
			return String.valueOf(value1*value2);
		}
		if(op.equals("/"))
		{
			System.out.println(String.valueOf(value1/value2));
			return String.valueOf(value1/value2);
		}
		return "";
	}
	private void getNibolan()
	{
		for(String op:opList)
		{
			if(op.matches("([0-9]+(\\.[0-9]+){0,1})"))
			{
				nibolanList.add(op);
			}
			else if(op.equals("("))
			{
				opStack.push(op);
			}
			else if(op.equals(")"))
			{
				if(opStack.isEmpty())
				{
					throw new RuntimeException("¿®∫≈≤ª∆•≈‰£°");
				}
				else
				{
					String s;
					while(!(s=opStack.pop()).equals("("))
					{
						nibolanList.add(s);
					}
				}
			}
			else if(op.matches("\\+|-|\\*|/"))
			{
				while(!opStack.isEmpty() && !opStack.peek().equals("(") && opStack.peek().matches("[\\*|/]"))
				{
					nibolanList.add(opStack.pop());
				}
				opStack.push(op);
			}
		}
		while(!opStack.isEmpty())
		{
			nibolanList.add(opStack.pop());
		}
	}
}
