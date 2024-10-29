// App.js
import React from 'react';
import TeacherList from './components/TeacherList';
import './App.css';


function App() {


    return (
        <div className="App">
            <header className="app-header">
                <h1>Список учителей</h1>
            </header>
            <TeacherList />
        </div>
    );
}

export default App;