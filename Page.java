public class Page{
	private int mId;
	private String mName;
	private String mParent;
	private String mUrl;
	private String mParentUrl;

	public Page(String parent, String name){
		this.mUrl = name;
		this.mParentUrl = parent;
		this.mName = formatUrl(name);
		this.mParent = formatUrl(parent);
	}

	public Page(int id, String parent, String name){
		this.mId = id;
		this.mUrl = name;
		this.mParentUrl = parent;
		this.mName = formatUrl(name);
		this.mParent = formatUrl(parent);
	}

	public void setId(int id){
		this.mId = id;
	}

	public int getId(){
		return this.mId;
	}

	public String getParentUrl(){
		return this.mParentUrl;
	}

	public String getUrl(){
		return this.mUrl;
	}

	public boolean isRoot(){
		return this.mParentUrl.equals(this.mUrl);
	}

	public String getName(){
		return this.mName;
	}

	public String getParent(){
		return this.mParent;
	}

	public String toString(){
		return "ID: " + this.mId + "\nPARENT: " + this.mParentUrl + "\nCURRENT: " + this.mUrl + "\n";
	}

	public int hashCode(){
		return this.mUrl.hashCode();
	}

	public boolean equals(Object obj){
		if(!(obj instanceof Page))
			return false;

		Page p = (Page) obj;
		return this.mUrl.equals(p.mUrl) && this.mParentUrl.equals(p.mParentUrl);
	}

	private String formatUrl(String url){
		return url.replace("https://en.wikipedia.org/wiki/", "");
	}
}