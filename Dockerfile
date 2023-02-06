#образ взятый за основу
FROM openjdk:18
#Записываем в переменную путь до WAR файла
ARG jarFile=target/MyPocketPurse-1.war
#Куда мы перемещаем варник внутри контейнера
WORKDIR /opt/app
#копируем jar внутрь контейнера
COPY ${jarFile} MyPocketPurse.war
#открываем порт
EXPOSE 5055
#команда для запуска
ENTRYPOINT ["java", "-jar", "MyPocketPurse.war"]
