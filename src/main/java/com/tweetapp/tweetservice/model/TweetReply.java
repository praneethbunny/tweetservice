package com.tweetapp.tweetservice.model;

public class TweetReply {

	private String reply;
	private String userName;
	private String replyDate;
	
	public TweetReply(String reply, String userName, String replyDate) {
		this.reply = reply;
		this.userName = userName;
		this.replyDate = replyDate;
	}
	



	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	
	
}
