create database amazon_info;
use amazon_info;
create table `amazon_info`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `task_id` varchar(50) NOT NULL,
    `start_stamp` varchar(20),
    `end_stamp` varchar(20),
    `url` text,
    `ASIN` varchar(10) NOT NULL DEFAULT '',
    `product_star` varchar(5) NOT NULL DEFAULT '',
    `product_rank` varchar(50) NOT NULL DEFAULT '',
    `task_state` int(1) NOT NULL DEFAULT -1,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
)