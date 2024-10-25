import React, { useEffect, useState } from 'react';

const TeacherList = () => {
    const [teachers, setTeachers] = useState([]);

    // Функция для получения списка учителей с сервера
    useEffect(() => {
        fetch('/api/teachers/getTeachers')
            .then((response) => response.json())
            .then((data) => {
                console.log('Received data:', data); // Отладочный вывод
                setTeachers(data); // Обновление состояния с данными учителей
            })
            .catch((error) => console.error('Error fetching teachers:', error));
    }, []);


    return (
        <div>
            <h1>Teacher List</h1>
            <ul>
                {teachers.map((teacher) => (
                    <li key={teacher.id}>
                        <h2>{`${teacher.firstName} ${teacher.secondName}`}</h2>
                        <p>Email: {teacher.email}</p>
                    </li>

                ))}
            </ul>
        </div>
    );
};

export default TeacherList;
