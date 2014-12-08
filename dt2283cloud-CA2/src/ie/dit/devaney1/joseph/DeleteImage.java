package ie.dit.devaney1.joseph;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DeleteImage extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws IOException
	{
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
		
		String blobKeyString = (String) req.getParameter("blobkey");

		Key key = KeyFactory.createKey("blobImage", blobKeyString);
		String nextJsp;
		
		try
		{
			Entity entity = dataStoreService.get(key);
			blobService.delete((BlobKey) entity.getProperty("blobkey"));
			dataStoreService.delete(key);
			nextJsp = "/getblobs";
		}
		catch (EntityNotFoundException e)
		{
			nextJsp = "upload.jsp";
			e.printStackTrace();
		}
		
		res.sendRedirect(nextJsp);
	}
	

}
