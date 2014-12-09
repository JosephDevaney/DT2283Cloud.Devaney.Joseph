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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GetBlobs extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException
	{
		Iterator<BlobInfo> blobInfos = new BlobInfoFactory().queryBlobInfos();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		UserService userService = UserServiceFactory.getUserService();
		ImagesService imageService = ImagesServiceFactory.getImagesService();
		
		//List<String> blobNames = new ArrayList<>();
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
