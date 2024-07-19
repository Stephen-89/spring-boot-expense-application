CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `role` (`id`, `name`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES ('2', 'ROLE_STAFF');
INSERT INTO `role` (`id`, `name`) VALUES ('3', 'ROLE_USER');

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_non_expired` tinyint(1) NOT NULL,
  `account_non_locked` tinyint(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `credentials_non_expired` tinyint(1) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `mfa_enabled` tinyint(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `second_name` varchar(255) DEFAULT NULL,
  `totp` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
);

INSERT INTO `user` (`id`, `account_non_expired`, `account_non_locked`, `created_at`, `credentials_non_expired`, `enabled`, `first_name`, `mfa_enabled`, `password`, `second_name`, `updated_at`, `username`) VALUES ('1', '1', '1', '2024-01-17 15:46:00.227000', '1', '1', 'Admin', '0', '$2a$10$WaR5S5o2lr3mMf/LVbnNl.Eu6UHdQuLSenxUyqi5Ij4/IFwcTXiti', 'Admin', '2024-02-23 16:58:51.261000', 'admin@admin.com');

CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
  KEY `FKgd3iendaoyh04b95ykqise6qh` (`user_id`),
  CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES ('1', '1');

CREATE TABLE `expense_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `expense_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `expense_type` (`id`, `description`, `expense_type_name`) VALUES ('1', '', 'Wedding');

CREATE TABLE `expense` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expense_amount` decimal(19,2) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `date` date NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `expense_name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `expense_type_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf1aksw4hstgv73km4rbl48jvq` (`expense_type_id`),
  KEY `FKaogtcbm8nm6qdgq36q29m947v` (`user_id`),
  CONSTRAINT `FKaogtcbm8nm6qdgq36q29m947v` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKf1aksw4hstgv73km4rbl48jvq` FOREIGN KEY (`expense_type_id`) REFERENCES `expense_type` (`id`) ON DELETE CASCADE
);


