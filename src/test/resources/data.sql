INSERT INTO MPA (name,description) VALUES ('G','у фильма нет возрастных ограничений');
INSERT INTO MPA (name,description) VALUES ('PG','детям рекомендуется смотреть фильм с родителями');
INSERT INTO MPA (name,description) VALUES ('PG-13','детям до 13 лет просмотр не желателен');
INSERT INTO MPA (name,description) VALUES ('R','лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
INSERT INTO MPA (name,description) VALUES ('NC-17','лицам до 18 лет просмотр запрещён');

INSERT INTO GENRES (name) VALUES ('Комедия');
INSERT INTO GENRES (name) VALUES ('Драма');
INSERT INTO GENRES (name) VALUES ('Мультфильм');
INSERT INTO GENRES (name) VALUES ('Триллер');
INSERT INTO GENRES (name) VALUES ('Документальный');
INSERT INTO GENRES (name) VALUES ('Боевик');

-- тестовые данные

INSERT INTO USERS (email, login, name, birthday) VALUES ('user1@mail.ru','user1','user1','2000-05-15');
INSERT INTO USERS (email, login, name, birthday) VALUES ('user2@mail.ru','user2','user2','2001-07-17');
INSERT INTO USERS (email, login, name, birthday) VALUES ('user3@mail.ru','user3','user3','2002-09-19');

INSERT INTO FRIENDS (userId, friendId) VALUES (1,2);
INSERT INTO FRIENDS (userId, friendId) VALUES (1,3);
INSERT INTO FRIENDS (userId, friendId) VALUES (2,3);

INSERT INTO FILMS (name, description, releaseDate, duration) VALUES ('film1','description1','2022-05-21',90);
INSERT INTO FILMS (name, description, releaseDate, duration) VALUES ('film2','description2','2022-03-11',120);

INSERT INTO LIKES (filmId, userId) VALUES (1,2);
INSERT INTO LIKES (filmId, userId) VALUES (2,1);
INSERT INTO LIKES (filmId, userId) VALUES (2,3);

INSERT INTO FILMGENRES (filmId, genreId) VALUES (1, 3);
INSERT INTO FILMGENRES (filmId, genreId) VALUES (1, 1);




