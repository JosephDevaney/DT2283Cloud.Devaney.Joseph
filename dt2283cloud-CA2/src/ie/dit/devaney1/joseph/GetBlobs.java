package ie.dit.devaney1.joseph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.images.Composite;
import com.google.appengine.api.images.IImagesServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.InputSettings;
import com.google.appengine.api.images.OutputSettings;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.images.ImagesService.OutputEncoding;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GetBlobs extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException
	{
		Iterator<BlobInfo> blobInfos = new BlobInfoFactory().queryBlobInfos();
		
		ImagesService imageService = ImagesServiceFactory.getImagesService();
		
		List<String> blobNames = new ArrayList<>();
		List<String> images = new ArrayList<>();
		
		BlobInfo b = null;
		while (blobInfos.hasNext())
		{
			b = blobInfos.next();
			// images.add(ImagesServiceFactory.makeImageFromBlob(b.getBlobKey()));
			if (!(b.getFilename().equals("usGLU.jpg")))
			{
				images.add(imageService.getServingUrl(ServingUrlOptions.Builder
						.withBlobKey(b.getBlobKey())));
				blobNames.add(b.getFilename());
			}
		}
		HttpSession sess = req.getSession();
		sess.setAttribute("names", blobNames);
		sess.setAttribute("images", images);
		res.sendRedirect("showPictures.jsp");
	}
	// delete usGLU.jpg

}
