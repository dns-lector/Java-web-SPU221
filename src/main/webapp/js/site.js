document.addEventListener('submit', (e) => {
    const form = e.target;
    if(form.id === 'signup-form') {
        e.preventDefault();
        let name = form.querySelector('[name="userName"]').value;
        let email = form.querySelector('[name="userEmail"]').value;
        let password = form.querySelector('[name="userPassword"]').value;
        let query = `name=${name}&email=${email}&password=${password}`;
        console.log(query);

        fetch(form.action, {
            method: 'POST'
        }).then(r => r.text()).then(console.log);
    }
});

/*
Створити фільтр для CORS інструкцій. Впровадити його для всіх запитів.
Створити DAO метод для видалення таблиці користувачів
Перенести роботу з журналом відвідування сайту (всі звернення до нього)
 до DAL (створити DTO / DAO, інжектувати, перевести на методи)
 */
