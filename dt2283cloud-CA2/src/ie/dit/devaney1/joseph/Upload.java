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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Upload extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private UserService userService = UserServiceFactory.getUserService();
	private DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("myFile");

		if (blobKeys == null)
		{
			res.sendRedirect("/");
		}
		else
		{
			for (BlobKey blobKey : blobKeys)
			{
				Key blob = KeyFactory.createKey("blobImage", userService.getCurrentUser().getUserId());
				Entity image = new Entity(blob);
				image.setProperty("ownerid", blobKey.getKeyString());
				
				dataStoreService.put(image);
			}
			
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res.sendRedirect("/getblobs");
			//res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
		}

	}

}
