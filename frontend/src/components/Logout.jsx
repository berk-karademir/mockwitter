import React from 'react';
import { apiWithCredentials } from '../service/axios';

const Logout = () => {

    const handleLogout = async () => {
        try {
            await apiWithCredentials.post('/auth/logout');
            
          
        } catch (error) {
            console.error('Çıkış hatası:', error);
            alert('Çıkış işlemi sırasında bir hata oluştu');
        }
    };

    return (
        <button onClick={handleLogout} className="logout-button">
            Çıkış Yap
        </button>
    );
};

export default Logout;