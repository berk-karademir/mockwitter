import React, { useEffect, useState } from "react";
import axios from "axios";

const HomeFeed = () => {
  const [feedData, setFeedData] = useState({ tweets: [], retweets: [] });

  useEffect(() => {
    axios
      .get("http://localhost:3000/home")
      .then((response) => {
        setFeedData(response.data);
      })
      .catch((error) => {
        console.error("Hata:", error);
      });
  }, []);

  return (
    <div>
      <h2>Tweetler</h2>
      {feedData.tweets.map((tweet) => (
        <div key={`tweet-${tweet.id}`} style={{ border: "1px solid #ccc", margin: "10px", padding: "10px" }}>
          <p><strong>{tweet.user.username}</strong>: {tweet.content}</p>
          <small>{new Date(tweet.postDate).toLocaleString()}</small>
          <div>Likes: {tweet.likesCount} | Dislikes: {tweet.dislikesCount} | Comments: {tweet.commentsCount} | Retweets: {tweet.retweetsCount}</div>
        </div>
      ))}

      <h2>Retweetler</h2>
      {feedData.retweets.map((retweet) => (
        <div key={`retweet-${retweet.id}`} style={{ border: "1px dashed #999", margin: "10px", padding: "10px" }}>
          <p><strong>{retweet.user.username}</strong> retweetledi:</p>
          <p><em>{retweet.content}</em></p>
          <small>{new Date(retweet.postDate).toLocaleString()}</small>
          <div>Likes: {retweet.likesCount} | Dislikes: {retweet.dislikeCount} | Comments: {retweet.commentsCount}</div>

          <div style={{ marginTop: "10px", padding: "10px", backgroundColor: "#f9f9f9" }}>
            <p><strong>{retweet.tweet.user.username}</strong>: {retweet.tweet.content}</p>
            <small>{new Date(retweet.tweet.postDate).toLocaleString()}</small>
          </div>
        </div>
      ))}
    </div>
  );
};

export default HomeFeed;
