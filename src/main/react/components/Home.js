import React from 'react';
import image from './img/maxresdefault.jpg';
function Home() {
    return (
        <main>
            <section>
                <h1>Добро пожаловать на платформу репетиторов</h1>
                <p>Здесь вы можете найти лучших преподавателей по любым предметам!</p>
                <img src={image} alt="Добро пожаловать" />
                <p>Наша платформа предлагает удобный способ найти репетитора, ознакомиться с его профилем и записаться на занятия.</p>
            </section>
        </main>
    );
}

export default Home;
