import React, { useEffect, useState } from 'react';
import TeacherCard from './TeacherCard';

const TeacherList = () => {
    const [teachers, setTeachers] = useState([]);
    const colors = ["#FF6F61", "#6B5B95", "#88B04B", "#FFA07A", "#20B2AA"]; // Массив цветов

    useEffect(() => {
        fetch('/api/teachers/getTeachers')
            .then((response) => response.json())
            .then((data) => setTeachers(data))
            .catch((error) => console.error('Error fetching teachers:', error));
    }, []);

    return (
        <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center' }}>
            {teachers.map((teacher, index) => (
                <TeacherCard
                    key={index}
                    firstName={teacher.firstName}
                    lastName={teacher.lastName}
                    email={teacher.email}
                    color={colors[index % colors.length]} // Чередуем цвета по индексу
                />
            ))}
        </div>
    );
};

export default TeacherList;