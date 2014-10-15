package ie.dit.devaney1.joseph;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Multiplication extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
		String aString = req.getParameter("A");
		String bString = req.getParameter("B");
		
		if(checkParameters(aString, bString) == false)
		{
			aString = getServletConfig().getInitParameter("A");
			bString = getServletConfig().getInitParameter("B");
		}
		
		int a, b;
		
		resp.setContentType("text/plain");
		
		try
		{
			a = Integer.parseInt(aString);
			b = Integer.parseInt(bString);
			
			long l = (long) a * (long) b;
			if (l > Integer.MAX_VALUE)
			{
				throw new NumberFormatException("Result is too big.");
			}
			else
			{
				resp.getWriter().println("Hello, I'm the addition servlet");
				resp.getWriter().println("A: " + a + ", B: " + b + ", A * B: " + (a*b));
			}
		}
		catch (NumberFormatException e)
		{
			resp.getWriter().println("Error: one/both of the arguments is not a number within the accepted 32 bit range.");
		}
	}
	
	private boolean checkParameters(String a, String b)
	{
		if (a == null || a == "")
		{
			return false;
		}
		if (b == null || b == "")
		{
			return false;
		}
		try
		{
			int num = Integer.parseInt(a);
			num= Integer.parseInt(b);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		
		return true;
	}

}
