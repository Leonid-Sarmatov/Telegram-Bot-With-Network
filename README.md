# Telegram-Bot-With-Network
Телеграм бот с нейросетью. В ответ на присылаемую картинку бот выдает ответ - что на ней.
Нейросеть знает только: автомобиль, самолет, кот.
По этому при загрузки картинки с неизвестным понятием, нейросеть выдаст то, на что это больше всего похоже.

# Для иcпользования необходимо заменить токен и имя бота на ваши.
BOT_NAME = "yourBotName"

BOT_TOKEN = "yourBotToken"

# Загрузка картинки
Для загрузки изображения необходимо его прикреплять к сообщению КАК ФАЙЛ. 
При этом загруженые файлы созраняются в директории /src/main/resources/uploadedFile

# Обучающая выборка
Путь к обучающей выборке /src/main/resources/images
