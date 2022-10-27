package com.tweetapp.tweetservice.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.tweetservice.model.Tweets;

@EnableScan
@Repository
public interface TweetsRepository extends CrudRepository<Tweets , String> {

	List<Tweets> findByUserName(String userName);
}
