package com.cx.demo.secure.commons;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CxDemoPost implements Serializable {

	private static final long serialVersionUID = 7890L;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static final int MAX_POSTS_PER_PAGE = 10; 

	private long   postTime;
	private String text;
	private String publisher;

	public CxDemoPost(long postTime, String text, String publisher) {
		setPostTime(postTime);
		setText(text);
		setPublisher(publisher);
	}

	public CxDemoPost(String text, String publisher) {
		setPostTime(System.currentTimeMillis());
		setText(text);
		setPublisher(publisher);
	}

	public static String encodeHtml(String post) {
		if (post != null) {
			return post.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
		return "";
	}

	public long getPostTime() {
		return postTime;
	}

	public void setPostTime(long postTime) {
		this.postTime = postTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = encodeHtml(text);
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPostTimeString() {
		return dateFormat.format(new Date(this.postTime));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CxDemoPost other = (CxDemoPost) obj;
		boolean publisherComp	= publisher == null && other.publisher == null && publisher.equals(other.publisher);
		boolean textComp 		= text == null && other.publisher == null && publisher.equals(other.publisher);
		boolean timeComp 		= postTime == other.postTime;
		return publisherComp && textComp && timeComp;
	}

	@Override
	public int hashCode() {
		final int prime = CxDemoProperties.HASH_SEED;
		int result = 1;
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + (int)postTime;
		return result;
	}

	@Override
	public String toString() {
		return publisher + " [" + getPostTimeString() + "] - " + getText();
	}

}
