import React, { useState } from "react";
import { apiWithCredentials } from '../service/axios';

const TweetSender = () => {
  const [content, setContent] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const sendTweet = async () => {
    try {
      const response = await apiWithCredentials.post('/tweet', { content });
      setSuccess("Tweet başarıyla gönderildi!");
      console.log(response.data);
      setContent(""); // Başarılı gönderim sonrası input'u temizle
    } catch (error) {
      setError(error.response?.data?.message || "Tweet gönderilemedi");
      console.error('Tweet gönderme hatası:', error);
    }
  };

  return (
    <div style={{ width: "400px", margin: "20px auto", padding: "20px", border: "1px solid #ccc", borderRadius: "8px", boxShadow: "2px 2px 10px rgba(0,0,0,0.1)" }}>
      {error && (
        <div style={{ color: "red", marginBottom: "10px" }}>{error}</div>
      )}
      {success && (
        <div style={{ color: "green", marginBottom: "10px" }}>{success}</div>
      )}
      <input
        type="text"
        placeholder="Tweetinizi yazın..."
        value={content}
        onChange={(e) => setContent(e.target.value)}
        style={{ width: "100%", padding: "10px", marginBottom: "10px", border: "1px solid #ccc", borderRadius: "4px" }}
      />
      <button
        onClick={sendTweet}
        style={{ width: "100%", padding: "10px", backgroundColor: "#007bff", color: "white", border: "none", borderRadius: "4px", cursor: "pointer" }}
      >
        Gönder
      </button>
    </div>
  );
};

export default TweetSender;
