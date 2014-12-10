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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class GetBlobs extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see com.google.appengine.api.blobstore.BlobInfoFactory#queryBlobInfos()
	 * @see com.google.appengine.api.datastore.Key
	 * @see com.google.appengine.api.datastore.Entity
	 * @see com.google.appengine.api.datastore.DatastoreService
	 * 
	 * Gets a List of Images to be displayed on showPictures.jsp
	 * <p>
	 * The method uses a BlobInfoFactory to get an iterator of all the BlobInfo objects stored on the server
	 * We iterate through all of these objects and extract the information we need:
	 * We create a Key for each object, the value of which is the BlobKeys' Blobkey as a string
	 * This Key will be used to retrieve data about the image in DataStore. The data is stored in an Entity </p>
	 * 
	 * <p>
	 * If the image is not marked as private, or if the user is logged in and able to view it, the picture is added to a list.
	 * The ImagesService object is used to create a URL that can be used as the source in an img tag to display the image
	 * This is done with the getServingURL method which takes a BlobKey as a parameter. Use a ServingURLOptions Builder
	 * @see com.google.appengine.api.images.ImagesService#getServingUrl(com.google.appengine.api.images.ServingUrlOptions options)
	 * When the loop is finished, the List of Image object is added to the current session.
	 * The servlet then forwards to the showPictures.jsp page which will display the images
	 * </p>
	 * 
	 * @param req HttpServletRequest object
	 * @param res HttpServletResponse object
	 * 
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException
	{
		Iterator<BlobInfo> blobInfos = new BlobInfoFactory().queryBlobInfos();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ImagesService imageService = ImagesServiceFactory.getImagesService();
		
		List<Image> images = new ArrayList<>();
		
		BlobInfo b = null;
		while (blobInfos.hasNext())
		{
			Image i;
			b = blobInfos.next();
			Key blob = KeyFactory.createKey("blobImage", b.getBlobKey().getKeyString());
			Boolean isPrivate = false;
			String userID = "";
			String nickname = "";
			try
			{
				Entity entity = datastore.get(blob);
				userID = (String) entity.getProperty("ownerid");
				nickname = (String) entity.getProperty("nickname");
				isPrivate = (Boolean) entity.getProperty("isPrivate");
			}
			catch (EntityNotFoundException e)
			{
				e.printStackTrace();
			}
			
			if (!isPrivate || req.getSession().getAttribute("session") != null)
			{
				String imageUrl = imageService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(b.getBlobKey()));
				i = new Image(imageUrl, userID, nickname, b.getFilename(), isPrivate, b.getBlobKey().getKeyString());
				images.add(i);
			}
				
		}
		
		HttpSession sess = req.getSession();
		sess.setAttribute("images", images);
		res.sendRedirect("showPictures.jsp");
	}

}
