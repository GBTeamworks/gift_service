DELETE from user_friends;
DELETE from user_authority;
DELETE from user_cart;
DELETE from cart_gift;
DELETE from authority;
DELETE from gift;
DELETE from cart;
DELETE from users;


SET NAMES utf8mb4;

INSERT INTO `users` (`id`, `username`, `birthdate`, `password`, `email`) VALUES
                                                                             (1,	'TestUser1',	'2022-12-01',	'$2a$10$U8GO1.tJewWHnOC.OPtpdO/J7O863bWNxYaxpK/a8KAeD7jy9SygC',	'testUser1@mail.ru'),
                                                                             (2,	'TestUser2',	'2022-12-02',	'$2a$10$98jwFG724DciUWXTlBG7gOk3iB1JFNlR3gaLhs6rmrX8DW1PupbOm',	'testUser2@mail.ru');
INSERT INTO `user_friends` (`friend_id`, `channel_id`) VALUES
                                                           (1,	2);

INSERT INTO `gift` (`id`, `title`, `description`, `user_id`) VALUES
    (1,	'Test Gift1',	'\"Test1Test1Test1\"',	1),
    (2,	'Test Gift2',	'\"Test2Test2Test2\"',	2);

INSERT INTO `cart` (`id`) VALUES
                                        (1),
                                        (2);

INSERT INTO `user_cart` (`cart_id`, `user_id`) VALUES
                                                   (1,	1),
                                                   (2,	2);
