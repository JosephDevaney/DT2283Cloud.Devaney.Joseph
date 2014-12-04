package ie.dit.devaney1.joseph;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CloudLabTest1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String aString = req.getParameter("A");
		String bString = req.getParameter("B");
		String cString = req.getParameter("C");
		String dString = req.getParameter("D");
		
		int a, b, c, d;
		
		if(checkParameters(aString, bString, cString, dString) == false)
		{
			aString = getServletConfig().getInitParameter("A");
			bString = getServletConfig().getInitParameter("B");
			cString = getServletConfig().getInitParameter("C");
			dString = getServletConfig().getInitParameter("D");
		}
		
		
		resp.setContentType("text/plain");

		try
		{
			a = Integer.parseInt(aString);
			b = Integer.parseInt(bString);
			c = Integer.parseInt(cString);
			d = Integer.parseInt(dString);
			
			long lAB, lCD, labcd;
			
			lAB = a - b;
			lCD = c - d;
			labcd = lAB * lCD;
			
			if ((lAB > Integer.MAX_VALUE || lAB < Integer.MIN_VALUE) ||
					(lCD > Integer.MAX_VALUE || lCD < Integer.MIN_VALUE) ||
					(labcd > Integer.MAX_VALUE || labcd < Integer.MIN_VALUE)
				)
			{
				throw new NumberFormatException("Result is too big.");
			}
			else
			{
				resp.getWriter().println("Hello, I'm the Maths servlet");
				resp.getWriter().println("(" + a + " - " + b + ") * (" + c + " - " + d + ") = (" + lAB + " * " + lCD + ") = " + labcd);
			}
		}
		catch (NumberFormatException e)
		{
			resp.getWriter().println("Error: Arguments/results of the arguments is not a number within the accepted 32 bit range.");
		}
	}

	private boolean checkParameters(String a, String b, String c, String d) 
	{
		int num;
		if (a == null || a == "")
		{
			return false;
		}
		if (b == null || b == "")
		{
			return false;
		}
		if (c == null || c == "")
		{
			return false;
		}
		if (d == null || d == "")
		{
			return false;
		}
		
		try {
			num = Integer.parseInt(a);
			num = Integer.parseInt(b);
			num = Integer.parseInt(c);
			num = Integer.parseInt(d);
			
			
		}
		catch (NumberFormatException e) 
		{
			return false;
		}
		
		return true;
	}
}
