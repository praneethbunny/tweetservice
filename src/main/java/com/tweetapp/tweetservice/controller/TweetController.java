package com.tweetapp.tweetservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.tweetservice.exception.TweetAppException;
import com.tweetapp.tweetservice.model.TweetReply;
import com.tweetapp.tweetservice.model.Tweets;
import com.tweetapp.tweetservice.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/tweets")
@Slf4j
public class TweetController {

	@Autowired
	private TweetService tweetService;
	
	@RequestMapping(value="/all" , method=RequestMethod.GET)
	public ResponseEntity<?> getAllTweets() throws TweetAppException{
		log.info("Inside get all tweets controller");
		List<Tweets> response = tweetService.getAllTweets();
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{username}" , method=RequestMethod.GET)
	public ResponseEntity<?> getTweetsByUser(@PathVariable String username) throws TweetAppException{
		log.info("Inside get all tweets by username controller");
		List<Tweets> response = tweetService.getTweetsByUser(username);
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{username}/add" , method=RequestMethod.POST)
	public ResponseEntity<?> postNewTweet(@PathVariable String username, @RequestBody Tweets tweet) throws TweetAppException{
		log.info("Inside add tweet controller");
		List<Tweets> response = tweetService.postNewTweet(username, tweet);
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{username}/update/{id}" , method=RequestMethod.PUT)
	public ResponseEntity<?> updateTweet(@PathVariable String username, @PathVariable String id, @RequestBody Tweets tweet) throws TweetAppException {
		List<Tweets>  response = tweetService.updateTweet(username, tweet);
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{username}/delete/{id}" , method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteTweet(@PathVariable String username , @PathVariable String id) throws TweetAppException {
		log.info("Inside delete tweet controller");
		List<Tweets> response = tweetService.deleteTweet(username, id);
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{username}/like/{id}" , method=RequestMethod.PUT)
	public ResponseEntity<?> likeTweet(@PathVariable String username,@PathVariable String id) throws TweetAppException {
		log.info("Inside like tweet controller");
		List<Tweets> response = tweetService.likeTweet(username, id);
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{username}/reply/{id}" , method=RequestMethod.PUT)
	public ResponseEntity<?> replyTweet(@PathVariable String username,@PathVariable String id ,@RequestBody TweetReply reply) throws TweetAppException{
		log.info("Inside reply tweet controller");
		List<Tweets> response = tweetService.replyTweet(username, id, reply);
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value="/trending" , method = RequestMethod.GET)
	public ResponseEntity<?> trending() throws TweetAppException{
		log.info("Inside trending tweets controller");
		List<Tweets> response = tweetService.trending();
		return new ResponseEntity<>(response  , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/health-check", method=RequestMethod.GET)
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("auth-Ok", HttpStatus.OK);
	}
}
