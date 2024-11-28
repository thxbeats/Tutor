document.addEventListener("DOMContentLoaded", () => {
    const dropdown = document.querySelector('.language-dropdown');
    const menu = dropdown.querySelector('.dropdown-menu');
    const languageButton = dropdown.querySelector('.dropdown-toggle');
    const flagIcon = languageButton.querySelector('.flag-icon');
    const languageText = languageButton.querySelector('.language-text');

    // Функция для обновления флага и текста на кнопке в зависимости от выбранного языка
    function updateLanguageDisplay(lang) {
        if (lang === 'en') {
            flagIcon.src = '/images/flags/lang.png';

        } else if (lang === 'ru') {
            flagIcon.src = '/images/flags/lang.png';

        }
    }

    // Определяем текущий язык с помощью Thymeleaf переменной
    const currentLang = 'en'; // Эта переменная будет передана сервером

    // Обновляем флаг и текст на кнопке в соответствии с текущим языком
    updateLanguageDisplay(currentLang);

    // Обработчик для показа/скрытия меню при клике
    languageButton.addEventListener('click', (event) => {
        event.stopPropagation(); // Останавливаем событие, чтобы не сработал обработчик на document
        dropdown.classList.toggle('active');
    });

    // Закрываем меню, если клик был за пределами
    document.addEventListener('click', (event) => {
        if (!dropdown.contains(event.target)) {
            dropdown.classList.remove('active');
        }
    });

    // Обработчик смены языка
    menu.querySelectorAll('.dropdown-item').forEach(item => {
        item.addEventListener('click', event => {
            event.preventDefault();
            const lang = item.dataset.lang; // Получаем язык из data-lang
            const currentUrl = new URL(window.location); // Текущий URL
            currentUrl.searchParams.set('lang', lang); // Добавляем параметр lang=...
            fetch(currentUrl.toString(), { method: 'GET' }) // Отправляем запрос
                .then(() => location.reload()); // Перезагружаем страницу
        });
    });
});
