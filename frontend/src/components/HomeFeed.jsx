import { useEffect, useState } from "react";
import { api } from "../service/axios";

const HomeFeed = () => {
  const [tweets, setTweets] = useState([]); // Varsayılan olarak boş dizi
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTweets = async () => {
      try {
        const response = await api.get("/home");
        const data = Array.isArray(response.data) ? response.data : []; // Güvenlik önlemi
        setTweets(data);
      } catch (err) {
        setError("Failed to load tweets");
      } finally {
        setLoading(false);
      }
    };

    fetchTweets();
  }, []);

  if (loading) return <p>Loading tweets...</p>;
  if (error) return <p>{error}</p>;
  if (tweets.length === 0) return <p>No tweets posted yet.</p>; // Eğer hiç tweet yoksa

  return (
    <div className="p-4 max-w-xl mx-auto">
      <h2 className="text-xl font-bold mb-4">Home Feed</h2>
      <ul>
        {tweets.map((tweet) => (
          <li key={tweet.id} className="border p-4 rounded-lg mb-2 shadow-sm">
            <p className="font-semibold">@{tweet.user.username}</p>
            <p>{tweet.content}</p>
            <div className="text-sm text-gray-500 mt-2 flex justify-between">
              <span>❤️ {tweet.likesCount}</span>
              <span>💬 {tweet.commentsCount}</span>
              <span>🔁 {tweet.retweetsCount}</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HomeFeed;
