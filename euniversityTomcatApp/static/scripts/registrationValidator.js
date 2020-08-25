document.addEventListener('DOMContentLoaded', () => {
    'use strict'

    // Получаем форму и все ее содержимое
    const form = document.querySelector("form");

    // получаем данные из формы по классу. Нужно для проверки на совпадение
    const login = form.querySelector("#loginInput");
    const loginRepeat = form.querySelector("#loginRepeatInput");

    const email = form.querySelector("#emailInput");
    const emailRepeat = form.querySelector("#emailRepeatInput");

    const password = form.querySelector("#passwordInput");
    const passwordRepeat = form.querySelector("#passwordRepeatInput");

    // Регулярные выражения для валидации
    const regExpLogin = /^[a-zA-Z][a-zA-Z0-9-_\.]{3,15}$/; // минимум 4 символа, максимум 16; допускаются строчные и заглавные латинские буквы и цифры; первый символ обязательно буква
    const regExpEmail = /^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$/i; // минимум 2 символа после "@"; минимум 2 символа после ".";
    const regExpPassword = /^[0-9a-zA-Z]{6,}$/; // минимум 6 символов; допускаются строчные и заглавные латинские буквы и цифры;

    let isValidate = false;
    let isValidateLogin = false;
    let isValidateLoginRepeat = false;
    let isValidateEmail = false;
    let isValidateEmailRepeat = false;
    let isValidatePassword = false;
    let isValidatePasswordRepeat = false;

    for (let elem of form.elements) { // Валидация для каждого из input
        if (!elem.classList.contains('form-check-input') && elem.tagName !== 'BUTTON') { // Отсекаем кнопки
            elem.addEventListener('blur', () => {
                validateElem(elem);
            }); // тип листенера blur - вызывает валидацию, когда пользователь убирает фокус из инпута
        }
    }

    // Когда нажата кнопка с type="submit" - перехватываем это действие и обрабатываем прежде, чем форма отправит запрос
    form.addEventListener('submit', (event) => {
        event.preventDefault(); // Временно блокируем отправку формы на сервер
        for (let elem of form.elements) {
            if (elem.tagName !== 'BUTTON') { // Отсекаем кнопки
                if (elem.value === "") { // Если поля пустые
                    elem.nextElementSibling.textContent = "Заполните данное поле!";
                    isValidate = false;
                } else { // Если поле заполнено
                    elem.nextElementSibling.textContent = "";
                    isValidate = true;
                }
            }
        }

        if (isValidate && isValidateLogin && isValidateLoginRepeat && isValidateEmail && isValidateEmailRepeat && isValidatePassword && isValidatePasswordRepeat) {
            submit();
        }
    });

    const validateElem = (elem) => {
        if (elem.name === "login") {
            removeEmptyFieldError(elem);
            if (login.value !== loginRepeat.value && loginRepeat.value !== "") {
                //login.nextElementSibling.textContent = "Логины не совпадают!"
                loginRepeat.nextElementSibling.textContent = "Логины не совпадают!"
                isValidateLogin = false;
            } else { // Если введенные значения совпадают - сбрасываем ошибки
                login.nextElementSibling.textContent = "";
                loginRepeat.nextElementSibling.textContent = "";
                isValidateLogin = true;
            }
            if (!regExpLogin.test(elem.value) && elem.value !== "") { // Если введенное значение не проходит регулярку
                elem.nextElementSibling.textContent = "Введите корректный логин!";
                isValidateLogin = false;
                Toast.fire({ // Всплывающая подсказка
                    icon: 'info',
                    html: '<p align="center"><h5>Логин может содержать:</h5></p>' +
                        '<p align="left" style="margin-left: 3ex">- От 4 до 16 символов</p>' +
                        '<p align="left" style="margin-left: 3ex">- Строчные или заглавные латинские буквы: a-z, A-Z</p>' +
                        '<p align="left" style="margin-left: 3ex">- Цифры (первый символ - обязательно буква)</p>',
                })
            } else { // Если введенное значение прошло регулярку - сбрасываем ошибку
                elem.nextElementSibling.textContent = "";
                isValidateLogin = true;
            }
        }
        if (elem.name === "login_repeat") {
            removeEmptyFieldError(elem);
            if (login.value !== loginRepeat.value && loginRepeat.value !== "") {
                //login.nextElementSibling.textContent = "Логины не совпадают!"
                loginRepeat.nextElementSibling.textContent = "Логины не совпадают!"
                isValidateLoginRepeat = false;
            } else { // Если введенные значения совпадают - сбрасываем ошибки
                loginRepeat.nextElementSibling.textContent = "";
                isValidateLoginRepeat = true;
            }
        }


        if (elem.name === "email") {
            removeEmptyFieldError(elem);
            if (email.value !== emailRepeat.value && emailRepeat.value !== "") {
                //email.nextElementSibling.textContent = "Email'ы не совпадают!"
                emailRepeat.nextElementSibling.textContent = "Email'ы не совпадают!"
                isValidateEmail = false;
            } else { // Если введенные значения совпадают - сбрасываем ошибки
                email.nextElementSibling.textContent = "";
                emailRepeat.nextElementSibling.textContent = "";
                isValidateEmail = true;
            }
            if (!regExpEmail.test(elem.value) && elem.value !== "") { // Если введенное значение не проходит регулярку
                elem.nextElementSibling.textContent = "Введите корректный email!";
                isValidateEmail = false;
                Toast.fire({ // Всплывающая подсказка
                    icon: 'info',
                    html: '<p align="center"><h5>Email должен иметь вид:</h5></p>' +
                        '<p align="left" style="margin-left: 3ex">example@example.com</p>' +
                        '<p align="left" style="margin-left: 3ex">и обязательно содержать символ "@"</p>',
                })
            } else { // Если введенное значение прошло регулярку - сбрасываем ошибку
                elem.nextElementSibling.textContent = "";
                isValidateEmail = true;
            }
        }
        if (elem.name === "email_repeat") {
            removeEmptyFieldError(elem);
            if (email.value !== emailRepeat.value && emailRepeat.value !== "") {
                //email.nextElementSibling.textContent = "Email'ы не совпадают!"
                emailRepeat.nextElementSibling.textContent = "Email'ы не совпадают!"
                isValidateEmailRepeat = false;
            } else { // Если введенные значения совпадают - сбрасываем ошибки
                emailRepeat.nextElementSibling.textContent = "";
                isValidateEmailRepeat = true;
            }
        }


        if (elem.name === "password") {
            removeEmptyFieldError(elem);
            if (password.value !== passwordRepeat.value && passwordRepeat.value !== "") {
                //password.nextElementSibling.textContent = "Пароли не совпадают!"
                passwordRepeat.nextElementSibling.textContent = "Пароли не совпадают!"
                isValidatePassword = false;
            } else { // Если введенные значения совпадают - сбрасываем ошибки
                password.nextElementSibling.textContent = "";
                passwordRepeat.nextElementSibling.textContent = "";
                isValidatePassword = true;
            }
            if (!regExpPassword.test(elem.value) && elem.value !== "") { // Если введенное значение не проходит регулярку
                elem.nextElementSibling.textContent = "Введите корректный пароль!";
                isValidatePassword = false;
                Toast.fire({ // Всплывающая подсказка
                    icon: 'info',
                    html: '<p align="center"><h5>Пароль должен содержать:</h5></p>' +
                        '<p align="left" style="margin-left: 3ex">- Минимум 6 символов</p>' +
                        '<p align="left" style="margin-left: 3ex">- Допускаются строчные и заглавные <br> латинские буквы и цифры</p>',
                })
            } else { // Если введенное значение прошло регулярку - сбрасываем ошибку
                elem.nextElementSibling.textContent = "";
                isValidatePassword = true;
            }
        }
        if (elem.name === "password_repeat") {
            removeEmptyFieldError(elem);
            if (password.value !== passwordRepeat.value && passwordRepeat.value !== "") {
                //password.nextElementSibling.textContent = "Пароли не совпадают!"
                passwordRepeat.nextElementSibling.textContent = "Пароли не совпадают!"
                isValidatePasswordRepeat = false;
            } else { // Если введенные значения совпадают - сбрасываем ошибки
                passwordRepeat.nextElementSibling.textContent = "";
                isValidatePasswordRepeat = true;
            }
        }
    };

    // Убираем ошибку, если пользователь что-то ввел в инпут
    const removeEmptyFieldError = (elem) => {
        if (elem.value !== "") {
            elem.nextElementSibling.textContent = "";
        }
    }

    const submit = () => {
        form.submit();
        alert('Данные отправленны');
        form.reset();
    }

    // Конфигурация всплывающих подсказок
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 4000,
        timerProgressBar: false,
        onOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })
});
