package com.tweetapp.tweetservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.tweetservice.exception.TweetAppException;
import com.tweetapp.tweetservice.model.TrendsDAO;
import com.tweetapp.tweetservice.model.TweetReply;
import com.tweetapp.tweetservice.model.Tweets;
import com.tweetapp.tweetservice.repository.TweetsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TweetServiceImpl implements TweetService {

	@Autowired
	private TweetsRepository repo;
	
	@Override
	public List<Tweets> getAllTweets() throws TweetAppException{
		List<Tweets> result = new ArrayList<>();
		List<Tweets> response = new ArrayList<>();
		try {
			for(Tweets tweet:repo.findAll()) {
				result.add(tweet);
			}
			if(result.isEmpty()) {
				log.error("DB is empty");
				throw new TweetAppException("no tweets to display");
			}else {
				response = result.stream().sorted(Comparator.comparing(Tweets::getCreatedDate).reversed()).collect(Collectors.toList());
				log.info("inside get All Service");
			}
		}catch(Exception ex) {
			log.error("Couldn't connect with DB");
			throw new TweetAppException("couldn't connect with DB");
		}
		
		return response;
	}
	
	@Override	
	public List<Tweets> getTweetsByUser(String userName) throws TweetAppException{
		List<Tweets> result= new ArrayList<>();
		try {
			log.info("inside get Tweets By User");
		 result = repo.findByUserName(userName);
		if(result.isEmpty()) {
			log.error("no tweets to display");
			throw new TweetAppException("no tweets to display");
		}
		}catch(Exception ex) {
			log.error("Couldn't connect with DB");
			throw new TweetAppException("Couldn't connect with DB");
			
		}
		return result;
	}
	
	@Override	
	public List<Tweets> postNewTweet(String userName, Tweets tweet) throws TweetAppException {
		Tweets post = new Tweets();
		post.settId(tweet.gettId());
		post.setTweet(tweet.getTweet());
		post.setUserName(userName);
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		post.setCreatedDate(current.format(formatter));
		List<String> likes = new ArrayList<>();
		List<TweetReply> reply = new ArrayList<>();
		post.setLikes(likes);
		post.setReply(reply);
		try {
			log.info("inside post new Tweet");
			repo.save(post);
		}catch(Exception ex) {
			log.error("failed to save tweet");
			throw new TweetAppException("failed to save tweet");
		}
		return getAllTweets();
	}
	
	@Override	
	public List<Tweets> updateTweet(String userName,Tweets tweet) throws TweetAppException {
		Optional<Tweets> option = repo.findById(tweet.gettId());
		if(option.isPresent()) {
			Tweets temp = option.get();
			if(temp.getUserName().equals(userName)) {
				temp.setTweet(tweet.getTweet());
				try {
					log.info("inside update Tweet");
				repo.save(temp);
				}catch(Exception ex){
					log.error("Failed to save Tweet");
					throw new TweetAppException("failed to save tweet");
				}
			}else {
				log.error("You cannot edit this Tweet");
				throw new TweetAppException("you cannot edit the Tweet");
			}
		}else {
			log.error("Tweet dont Exist");
			throw new TweetAppException("Tweet dont Exist");
		}
		return getAllTweets();
	}
	
	@Override	
	public List<Tweets> deleteTweet(String userName , String id) throws TweetAppException {
		Optional<Tweets> option = repo.findById(id);
		Tweets temp;
		if(option.isPresent()) {
			temp = option.get();
			if(temp.getUserName().equals(userName)) {
				try {
					log.info("inside delete Tweet");
				repo.delete(temp);
				}catch(Exception ex){
					log.error("failed to delete tweet");
					throw new TweetAppException("failed to delete tweet");
				}
			}else {
				log.error("you cannot delete the Tweet");
				throw new TweetAppException("you cannot delete the Tweet");
			}
		}else {
			log.error("Tweet dont Exist");
			throw new TweetAppException("Tweet dont Exist");
		}
		return getAllTweets();
	}
	
	@Override	
	public List<Tweets> likeTweet(String userName, String id) throws TweetAppException {
		Optional<Tweets> option = repo.findById(id);
		if(option.isPresent()) {
			Tweets temp = option.get();
			if(!temp.getUserName().equals(userName)) {
				List<String> likes = temp.getLikes();
				likes.add(userName);
				temp.setLikes(likes);
				try {
					log.info("inside like Tweet");
				repo.save(temp);
				}catch(Exception ex){
					log.error("failed to like tweet");
					throw new TweetAppException("failed to like tweet");
				}
			}else {
				log.error("you cannot like your Tweet");
				throw new TweetAppException("you cannot like your Tweet");
			}
		}else {
			log.error("Tweet dont Exist");
			throw new TweetAppException("Tweet dont Exist");
		}
		return getAllTweets();
	}
	
	@Override	
	public List<Tweets> replyTweet(String userName, String id , TweetReply reply) throws TweetAppException {
		Optional<Tweets> option = repo.findById(id);
		if(option.isPresent()) {
			Tweets tweet = option.get();
			LocalDateTime current = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			String replyDate = current.format(formatter);
			TweetReply tr = new TweetReply(reply.getReply() , userName , replyDate);
			List<TweetReply> list = tweet.getReply();
			list.add(tr);
			tweet.setReply(list);
			try {
				log.info("inside reply Tweet");
				 repo.save(tweet);
			}catch(Exception ex) {
				log.error("couldn't reply check format");
				throw new TweetAppException("couldn't reply check format");
			}
		}else {
			log.error("Tweet dont Exist");
			throw new TweetAppException("tweet doesnot exist");
		}
		return getAllTweets();
		}
	
	@Override
	public List<Tweets> trending() throws TweetAppException{
		List<Tweets> result = new ArrayList<>();
		try {
			log.info("inside trending Tweets");
			int l=10;
			List<Tweets> temp = getAllTweets();
			List<TrendsDAO> trend = new ArrayList<>();
			for(Tweets t : temp) {
				trend.add(new TrendsDAO(t.gettId(),t.getReply().size(),t.getLikes().size()));
			}
			trend=trend.stream().sorted(Comparator.comparing(TrendsDAO::getLikesCount).reversed()).collect(Collectors.toList());
			trend=trend.stream().sorted(Comparator.comparing(TrendsDAO::getRepliesCount).reversed()).collect(Collectors.toList());
			if(trend.size()<10) {
				l=trend.size();
			}
			for(int i=0;i<l;i++) {
				result.add(repo.findById(trend.get(i).gettId()).get());
			}
		}catch(Exception ex) {
			log.error("couldnt complete rquest");
			throw new TweetAppException("couldnt complete rquest");
		}
		return result;
	}
}
