package com.tweetapp.tweetservice.service;

import java.util.List;

import com.tweetapp.tweetservice.exception.TweetAppException;
import com.tweetapp.tweetservice.model.TweetReply;
import com.tweetapp.tweetservice.model.Tweets;

public interface TweetService {

	public List<Tweets> getAllTweets() throws TweetAppException;
	
	public List<Tweets> getTweetsByUser(String userName) throws TweetAppException;
	
	public List<Tweets> postNewTweet(String userName, Tweets tweet) throws TweetAppException;
	
	public List<Tweets> updateTweet(String userName,Tweets tweet) throws TweetAppException ;
	
	public List<Tweets> deleteTweet(String userName , String id) throws TweetAppException;
	
	public List<Tweets> likeTweet(String userName, String id) throws TweetAppException;
	
	public List<Tweets> replyTweet(String userName, String id , TweetReply reply) throws TweetAppException;

	public List<Tweets> trending() throws TweetAppException;
}
