document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.change-lang').forEach(button => {
        button.addEventListener('click', event => {
            event.preventDefault(); // Отменяет стандартное поведение кнопки
            const lang = event.target.dataset.lang; // Получаем язык из data-lang
            const currentUrl = new URL(window.location); // Берем текущий URL
            currentUrl.searchParams.set('lang', lang); // Обновляем или добавляем параметр ?lang=...
            fetch(currentUrl.toString(), { method: 'GET' }) // Отправляем запрос
                .then(() => location.reload()); // Обновляем страницу
        });
    });
});
