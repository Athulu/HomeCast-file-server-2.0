INSERT INTO sources(mime, type, url)
VALUES ('videos/mp4', 'mp4', 's02e02.Jujutsu Kaisen.mp4'),
       ('videos/mp4', 'mp4', 's02e01.Jujutsu Kaisen.mp4');

INSERT INTO video(episode, title, subtitle, thumb, image480x270, image780x1200, duration, sourceID, hashcode)
VALUES ('s02e02', 'Jujutsu Kaisen','subtitle1', 's02e02.Jujutsu Kaisen480x270.png', 's02e02.Jujutsu Kaisen480x270.png', 'bbb.png', 100, 1, 1470969704),
       ('s02e01', 'Jujutsu Kaisen','subtitle2', 's02e01.Jujutsu Kaisen480x270.png', 's02e01.Jujutsu Kaisen480x270.png', 'bbb.png', 100, 2, 1879793929);