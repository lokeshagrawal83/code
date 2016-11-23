CREATE TABLE IF NOT EXISTS `counter` (
  `id` int(4) NOT NULL,
  `config_key` varchar(10) NOT NULL,
  `counter` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `counter` (`id`, `config_key`, `counter`) VALUES
(1, 'counter', 0);


ALTER TABLE `counter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `config_key` (`config_key`);

ALTER TABLE `counter`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;