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

public class Serve extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see com.google.appengine.api.datastore.Key
	 * @see com.google.appengine.api.datastore.Entity
	 * @see com.google.appengine.api.datastore.DatastoreService
	 * 
	 * Downloads an image
	 * 
	 * @param req HttpServletRequest object
	 * @param res HttpServletResponse object
	 * 
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException
	{
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		String blobKeyString = req.getParameter("blobkey");
		
		Key key = KeyFactory.createKey("blobImage", blobKeyString);	// Create a Key from the blobkey of the image that was clicked
		
		try
		{
			Entity entity = dataStoreService.get(key);	//Get the entity from DataStore
			BlobKey blobKey = (BlobKey) entity.getProperty("blobkey");	// Get the Blobkey object
			blobstoreService.serve(blobKey, res);	// Serve the blobkey
		}
		catch (EntityNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
