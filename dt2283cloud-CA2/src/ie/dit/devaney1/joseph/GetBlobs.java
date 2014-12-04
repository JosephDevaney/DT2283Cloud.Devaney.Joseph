package ie.dit.devaney1.joseph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;

public class GetBlobs extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws IOException
	{
		Iterator<BlobInfo> blobInfos = new BlobInfoFactory().queryBlobInfos();
		
		List<String> blobNames = new ArrayList<>();
		
		BlobInfo b = null;
		while (blobInfos.hasNext())
		{
			b = blobInfos.next();
			blobNames.add(b.getFilename());
		}
		HttpSession sess = req.getSession();
		sess.setAttribute("names", blobNames);
		res.sendRedirect("showPictures.jsp");
	}

}
