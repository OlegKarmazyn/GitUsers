package solid.icon.gitusers

/*
• Тестове завдання android:
- витягнути юзерів в список https://api.github.com/users
- при тапі на одного юзера показати його репозиторії
    https://api.github.com/users/{login}/repos
- всі дані повинні кешитись в sqlite бд
- також потрібно реалізувати пагінацію для списку юзерів
    та списку репозиторіїв одного юзера
- спершу повинні відображатись закешовані данні а потім з апі,
    також апп повинен працювати в офлайн режимі

До виконаного завдання потрібно прикріпити відео на декілька секунд з демо проекту.
Дизайн довільний, але він повинен бути зрозумілим та слідувати рекомендаціям google.
Виконати все за допомогою MVVM архітектури з використанням Kotlin та Android Compose обовязково.
Виконане завдання потрібно залити на github.*/

// todo:
//  1. Get users in list
//  1.1 make ipl. Kodein lib +
//  1.2 define App (main) class +
//  1.3 define viewModel for MainActivity +
//  1.4 define Model which get list (async) +
//  1.5 define repository for viewModel +
//  1.6 inject rep, VM using Kodein +
//  1.7 define View's list with scrolling +
//  1.8 set onClick and move to next Activity +
//      using Intent give "login" +
//  ------------------------------------------
//  2. Show User's repositories +
//  2.1 define activity, cell for rep +
//      viewModel and model
//  ------------------------------------------
//  3. Implement pagination + loading
//  ------------------------------------------
//  4. Create database and use it