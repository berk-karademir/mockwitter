package com.BekoInc.mockwitter.repository;
import com.BekoInc.mockwitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;





public interface TweetRepository extends JpaRepository<Tweet ,Long> {

    @Query("SELECT t FROM Tweet t WHERE t.user.id=:userId")
    List<Tweet> findAllByUserId(Long userId);


}
