INSERT INTO sources(sourceID, mime, type, url)
VALUES (1, 'mime1', 'type1', 'url1'),
       (2, 'mime2', 'type2', 'url2'),
       (3, 'mime3', 'type3', 'url3');

INSERT INTO video(videoID, episode, title, subtitle, thumb, image480x270, image780x1200, duration, sourceID)
VALUES (1, 'episode1', 'title1','subtitle1', 'thumb1', 'image480x270_1', 'image780x1200_1', 1, 1),
       (2, 'episode2', 'title2','subtitle2', 'thumb2', 'image480x270_2', 'image780x1200_2', 2, 2),
       (3, 'episode3', 'title3','subtitle3', 'thumb3', 'image480x270_3', 'image780x1200_3', 3, 3);



