# RuYou1
Тестовое задание от компании "RuYou"
Простой клиент на Android, осуществляющий GET и POST запросы с помощью библиотеки Retrofit.

## Описание
Для начала получим список частей тела и их id с сервера.
Выводим список при помощи RecyclerView.
![Image alt](https://github.com/Nikita-Freedom/RuYou1/blob/master/img_recycler.PNG)

Далее при нажатии на элемент списка открывается след. активность с переданным названием пункта в поле "Target"
![Image alt](https://github.com/Nikita-Freedom/RuYou1/blob/master/imd_descr.PNG)

При нажатии на кнопку "Сделать фото" открывается камера. Сделанное фото сохраняется в папку приложения, а файл с названием фото выводится в поле "Фото"(под отчеством). 
Также у нас есть 3 поля ввода данных для отправки на сервер. 
Если поля пустые, кнопка "Да" не работает. Если поля заполнены то отправляем данные(Имя, Фамилия, Отчество, файл изображения) на сервер и выводим SnackBar с уведомлением об успешной отправке.

При нажатии на кнопку "переснять" открывается заново камера.
При нажатии на кнопку "нет" очищаются поля ввода и закрывается диалоговое окно.

![Image alt](https://github.com/Nikita-Freedom/RuYou1/blob/master/img_dialog.PNG)
![Image alt](https://github.com/Nikita-Freedom/RuYou1/blob/master/img_dialog_server.PNG)

После отправки данных в поле "Response" выводится ответ с сервера.

![Image alt](https://github.com/Nikita-Freedom/RuYou1/blob/master/img_response.PNG)

Мы можем открыть фото в полноэкранном режиме нажав на него. Повторное нажатие на фото вернет его к исходному размеру.
![Image alt](https://github.com/Nikita-Freedom/RuYou1/blob/master/imd_descr.PNG)
![Image alt](https://github.com/Nikita-Freedom/RuYou1/blob/master/img_fullscrn.PNG)

## Построен с помощью
* [Retrofit](https://square.github.io/retrofit/) - Используется Retrofit.
	Архитектура приложения: MVP, MVVM.
  Вью для списка — RecyclerView.
