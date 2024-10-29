// components/TeacherCard.js
import React from 'react';
import './TeacherCard.css';

const TeacherCard = ({ firstName, lastName, email, color }) => {
    return (
        <div className="teacher-card" style={{ backgroundColor: color }}>
            <h2>{firstName} {lastName}</h2>
            <p>{email}</p>
            <span className="subject-tag">Математика</span>
        </div>
    );
};

export default TeacherCard;