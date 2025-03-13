import { useState } from "react";
import { api } from "../service/axios";

export default function SignUpForm() {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/auth/register", {
        withCredentials: true,

        email,
        username,
        password,
      });
      setSuccess(true);
      console.log(response.data);
    } catch (err) {
      setError(err.response ? err.response.data : "Bir hata oluştu");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
      <h2>Signup</h2>
      <h3>a@b.com</h3>
      <h3>qwe123</h3>
      <h3>SecurePass@2023</h3>
      
        <label>Email:</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Kullanıcı Adı:</label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Şifre:</label>
        <input
          type="text"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
      </div>
      <button type="submit">Kayıt Ol</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {success && <p style={{ color: "green" }}>Kayıt başarılı!</p>}
    </form>
  );
}
