// src/components/LogoutForm.js
import React from 'react';
import { api } from '../service/axios';

const LogoutForm = () => {
    const handleLogout = async () => {
        try {
            await api.post('auth/logout', {}, {
            });
            console.log('Çıkış başarılı');
            // Çıkış işlemi sonrası yapılacak işlemler (örneğin, kullanıcıyı anasayfaya yönlendirme)
        } catch (err) {
            console.error('Çıkış hatası:', err);
        }
    };

    return (
        <div>
            <h2>Logout</h2>
            <button onClick={handleLogout}>Çıkış Yap</button>
        </div>
    );
};

export default LogoutForm;