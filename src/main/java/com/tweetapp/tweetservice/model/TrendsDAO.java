package com.tweetapp.tweetservice.model;

public class TrendsDAO {

	public String tId;
	
	public int repliesCount;
	
	public int likesCount;
	
	public TrendsDAO(String tId, int repliesCount, int likesCount) {
		this.tId=tId;
		this.repliesCount=repliesCount;
		this.likesCount=likesCount;
	}
	
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public int getRepliesCount() {
		return repliesCount;
	}
	public void setRepliesCount(int repliesCount) {
		this.repliesCount = repliesCount;
	}
	public int getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}
	
	
}
