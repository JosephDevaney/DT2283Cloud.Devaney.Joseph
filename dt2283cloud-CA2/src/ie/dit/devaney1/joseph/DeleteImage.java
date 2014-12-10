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

	/**  
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see com.google.appengine.api.datastore.Key
	 * @see com.google.appengine.api.datastore.Entity
	 * @see com.google.appengine.api.datastore.DatastoreService
	 * @see com.google.appengine.api.blobstore.BlobstoreService
	 * 
	 * Deletes an image from Blobstore and DataStore
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
		BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
		
		String blobKeyString = (String) req.getParameter("blobkey");	//Get the blobkey to be deleted from the request object

		Key key = KeyFactory.createKey("blobImage", blobKeyString);	//Create a key for that blobkeys DataStore Entity
		
		try
		{
			Entity entity = dataStoreService.get(key);	// Get the entity from the DataStore
			blobService.delete((BlobKey) entity.getProperty("blobkey"));	//Delete the Blobkey from BlobStore
			dataStoreService.delete(key);	//Delete the entity from DataStore
		}
		catch (EntityNotFoundException e)
		{
			e.printStackTrace();
		}
		
		res.sendRedirect("/getblobs");
	}
	

}
