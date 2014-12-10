package ie.dit.devaney1.joseph;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Upload extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private UserService userService = UserServiceFactory.getUserService();
	private DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see com.google.appengine.api.blobstore.BlobstoreService#getUploads(javax.servlet.http.HttpServletRequest)
	 * @see com.google.appengine.api.datastore.Key
	 * @see com.google.appengine.api.datastore.Entity
	 * @see com.google.appengine.api.datastore.DatastoreService
	 * 
	 * Upload an image to Blobstore and store metadata in DataStore
	 * 
	 * <p>
	 * The servlet retrieves the uploaded file from the HttpRequest object as a List. This list should only have 1 file in it.
	 * Note if the file was uploaded as to be private or public. Then, for every object in the list, do the following:
	 * <ul><li>Create a DataStore Key with the Blobkey as the value in the Key</li>
	 * <li>Create a new entity from that Key</li>
	 * <li>Set the owner id, uploaders nickname, privacy status and blobkey of the upload as attributes on the Entity</li>
	 * <li>Put the Entity onto the DataStore</li></ul>
	 * 
	 * Servlet then redirects to /getblobs
	 * {@link ie.dit.devaney1.joseph.GetBlobs.java}
	 * 
	 * @param req HttpServletRequest object
	 * @param res HttpServletResponse object
	 * 
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);	// Get a Map with a List of the uploaded files as BlobKeys
		List<BlobKey> blobKeys = blobs.get("myFile");
		
		String isPrivate = req.getParameter("isPrivate");
		
		Boolean privacy = null;
		if (isPrivate != null)	// If file was uploaded with private set
		{
			privacy = true;
		}
		else
		{
			privacy = false;
		}

		if (blobKeys == null)
		{
			res.sendRedirect("/");
		}
		else
		{
			for (BlobKey blobKey : blobKeys)	// the List should only have one element
			{
				Key blob = KeyFactory.createKey("blobImage", blobKey.getKeyString());	//Create a Key with the Blobkey as the value
				Entity image = new Entity(blob);	// Create a new entity form the previous Key
				
				// Set Properties containing the information about the uploaded image
				image.setProperty("ownerid",  userService.getCurrentUser().getUserId());
				image.setProperty("nickname", userService.getCurrentUser().getNickname());
				image.setProperty("isPrivate", privacy);
				image.setProperty("blobkey", blobKey);
				
				// Store the Entity in the DataStore
				dataStoreService.put(image);
			}
			
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			res.sendRedirect("/getblobs");
		}

	}

}
