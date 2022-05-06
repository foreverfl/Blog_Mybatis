CREATE TABLE SHOP(
SHOP_NO INT AUTO_INCREMENT,
SHOP_NAME VARCHAR(50),
SHOP_LOCATION VARCHAR(50),
SHOP_STATUS VARCHAR(50),
PRIMARY KEY(SHOP_NO)
);



INSERT INTO SHOP(SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS)
VALUES
(1, 'shop1', 'Korea', 'Y'),
(2, 'shop2', 'Korea', 'Y'),
(3, 'shop3', 'Korea', 'Y'),
(4, 'shop4', 'Japan', 'Y'),
(5, 'shop5', 'Japan', 'Y'),
(6, 'shop6', 'Japan', 'Y')

