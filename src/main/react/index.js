import React from 'react';
import ReactDOM from 'react-dom';
import TeacherList from './components/TeacherList';
import App from './App';
import './components/TeacherCard.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);

ReactDOM.render(<TeacherList />, document.getElementById('root'));
