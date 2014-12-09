package ie.dit.devaney1.joseph;

import java.io.Serializable;

public class Image implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	private String url;
	
	private String owner;
	
	private String ownerNickName;
	
	private Boolean isPrivate;
	
	private String name;

	private String blobKey;
	
	public Image()
	{
		
	}
	
	public Image(String url, String owner, String nickname, String name, Boolean isPrivate, String blobkey)
	{
		setUrl(url);
		setOwner(owner);
		setName(name);
		setIsPrivate(isPrivate);
		setBlobKey(blobkey);
		setOwnerNickName(nickname);
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public String getOwnerNickName()
	{
		return ownerNickName;
	}

	public void setOwnerNickName(String ownerNickName)
	{
		this.ownerNickName = ownerNickName;
	}

	public Boolean getIsPrivate()
	{
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate)
	{
		this.isPrivate = isPrivate;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBlobKey()
	{
		return blobKey;
	}

	public void setBlobKey(String blobKey)
	{
		this.blobKey = blobKey;
	}

}
