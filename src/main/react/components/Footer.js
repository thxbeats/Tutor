import React from 'react';

function Footer() {
    let int = new Date().getFullYear();
    return (
        <footer>
            <p>© {int} Платформа Репетиторов. Все права защищены.</p>
        </footer>
    );
}

export default Footer;
