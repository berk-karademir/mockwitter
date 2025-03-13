import React, { useState } from "react";
import axios from "axios";

const TweetSender = () => {
  const [content, setContent] = useState("");  // Başlangıçta boş bir içerik

  // Kullanıcı adı ve şifre
  const username = 'berk123';
  const password = 'SecurePass@2023';
  
  // Authorization başlığı için Base64 encoding (btoa kullanarak)
  const auth = 'Basic ' + btoa(username + ':' + password);
  
  // API'ye gönderilecek veri
  const data = {
    content: content // state'deki içeriği gönderiyoruz
  };

  const sendTweet = () => {
    axios.post('http://localhost:3000/tweet', data, {
      headers: {
        'Authorization': auth,
        'Content-Type': 'application/json'  // JSON formatında body gönderiyoruz
      }
    })
    .then(response => {
      console.log('Veri:', response.data);
    })
    .catch(error => {
      console.error('Hata:', error);
    });
  };

  return (
    <div style={{ width: "400px", margin: "20px auto", padding: "20px", border: "1px solid #ccc", borderRadius: "8px", boxShadow: "2px 2px 10px rgba(0,0,0,0.1)" }}>
      <input
        type="text"
        placeholder="Tweetinizi yazın..."
        value={content}  // content'i state'ten alıyoruz
        onChange={(e) => setContent(e.target.value)}  // input alanındaki değeri content state'ine set ediyoruz
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
