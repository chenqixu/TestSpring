CREATE TABLE `ACT_ID_GROUP` (
                              `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
                              `REV_` int(11) DEFAULT NULL,
                              `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                              `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                              PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `ACT_ID_USER` (
                             `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
                             `REV_` int(11) DEFAULT NULL,
                             `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                             PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `ACT_ID_MEMBERSHIP` (
                                   `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
                                   `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
                                   PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
                                   KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
                                   CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `ACT_ID_GROUP` (`ID_`),
                                   CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `ACT_ID_USER` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;