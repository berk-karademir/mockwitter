// src/components/LoginForm.js
import React, { useState } from 'react';
import { apiWithCredentials } from '../service/axios';

const LoginForm = () => {
    const [emailOrUsername, setEmailOrUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null); // Hata mesajını sıfırla

        try {
            const response = await apiWithCredentials.post('/auth/login', {
                emailOrUsername,
                password
            }, {
                withCredentials: true // Çerezlerin gönderilmesi için
            });

            
            console.log('Giriş başarılı:', response.data);
            setSuccess("giriş successfull")
            // Burada kullanıcıyı yönlendirebilir veya başka işlemler yapabilirsiniz
        } catch (err) {
            console.error('Giriş hatası:', err.response);
            setError('Kullanıcı adı veya şifre hatalı.');
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email veya Kullanıcı Adı:</label>
                    <input
                        type="text"
                        value={emailOrUsername}
                        onChange={(e) => setEmailOrUsername(e.target.value)}
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
                <button type="submit">Giriş Yap</button>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                {success && <p style={{ color: 'green' }}>{success}</p>}

            </form>
        </div>
    );
};

export default LoginForm;