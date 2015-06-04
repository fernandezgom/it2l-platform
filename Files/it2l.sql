USE italk2learn;

CREATE TABLE `user` (
  `id_user` int(10) NOT NULL DEFAULT '0',
  `user` varchar(50) DEFAULT NULL,
  `idView` int(10) DEFAULT NULL,
  `sequencer` varchar(2) DEFAULT NULL,
  `cond` int(10) DEFAULT NULL,
  `idSequencerView` varchar(30) DEFAULT 'MA_GBR_0775CAx0100',
  `lastScore` int(10) DEFAULT '0',
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (0,'tludmetal',101,'SM',1,'MA_GBR_0775CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (1,'wayne',101,'SM',1,'MA_GBR_0775CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (2,'alice',101,'SM',1,'MA_GBR_0775CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (3,'manolis',101,'SM',1,'task2.2',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (4,'student1',101,'SM',1,'MA_GBR_1150CAp0100',4);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (5,'student2',101,'SM',1,'MA_GBR_1150CAp0100',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (6,'student3',101,'SM',1,'MA_GBR_1150CAp0100',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (7,'student4',101,'SM',1,'task2.6.setB',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (8,'student5',101,'SM',1,'MA_GBR_1150CAp0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (9,'claudia',101,'SM',1,'MA_GBR_0775CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (10,'student6',101,'SM',1,'task2.6.setA',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (11,'student7',101,'SM',1,'task2.4.setA.sets',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (12,'student8',101,'SM',1,'MA_GBR_1150CAx0100',4);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (13,'student9',101,'SM',1,'task2.2',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (14,'student10',101,'SM',1,'MA_GBR_1150CAp0100',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (15,'student11',101,'SM',1,'MA_GBR_1150CAp0100',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (16,'student12',101,'SM',1,'task2.7.setA',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (17,'student13',101,'SM',1,'MA_GBR_1150CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (18,'student14',101,'SM',1,'task2.4.setA.area',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (19,'student15',101,'SM',1,'task2.4.setB.sets',2);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (20,'student16',101,'SM',1,'MA_GBR_0950CAp0100',3);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (21,'student17',101,'SM',1,'MA_GBR_1150CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (22,'student18',101,'SM',1,'task2.6.setA',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (23,'student19',101,'SM',1,'task2.4.setB.numb',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (24,'student20',101,'SM',1,'MA_GBR_0825CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (25,'student21',106,'SM',3,'MA_GBR_0775CAx0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (26,'student22',108,'SM',3,'MA_GBR_0775CAx0100',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (27,'student23',110,'SM',3,'MA_GBR_0775CAx0100',2);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (28,'student24',108,'SM',3,'MA_GBR_0775CAx0100',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (29,'student25',113,'SM',3,'MA_GBR_0775CAx0100',5);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (30,'student26',101,'SM',2,'MA_GBR_0950CAp0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (31,'student27',101,'SM',2,'MA_GBR_1150CAp0100',4);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (32,'student28',101,'SM',2,'MA_GBR_0950CAx0100',3);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (33,'student29',101,'SM',2,'MA_GBR_1150CAp0100',0);
insert into `user`(`id_user`,`user`,`idView`,`sequencer`,`cond`,`idSequencerView`,`lastScore`) values (34,'student30',101,'SM',2,'task2.4.setB.sets',0);

CREATE TABLE `exercises` (
  `id_exercise` int(10) NOT NULL DEFAULT '0',
  `exercise` varchar(50) DEFAULT NULL,
  `view` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `fl_task` varchar(20) DEFAULT NULL,
  `id_sequencer` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_exercise`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (0,'WhizzHTML5','views',null,null,'WH0');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (1,'WhizzFlash','whizzViews',null,null,'MA_GBR_0500AAx0100');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (2,'FractionsTutor','fractionsTutorViews',null,null,'FT2');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (3,'FractionsLab1','fractionsLabViews',null,null,'FL3');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (4,'WhizzFlash2','whizzViews',null,null,'MA_GBR_0775CAx0100');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (5,'WhizzFlash3','whizzViews',null,null,'MA_GBR_0850CAx0100');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (6,'WhizzFlash4','whizzViews',null,null,'MA_GBR_0950CAx0100');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (7,'WhizzFlash5','whizzViews',null,null,'MA_GBR_1125CAx0100');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (8,'WhizzFlash6','whizzViews',null,null,'MA_GBR_1150CAx0300');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (9,'FractionsLab2','fractionsLabViews',null,null,'FL9');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (10,'FractionsLab3','fractionsLabViews',null,null,'FL10');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (11,'FractionsLab4','fractionsLabViews',null,null,'FL11');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (12,'AudioRecorderFlash','views',null,null,'AR12');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (13,'equivalentFractionsProceduralIdea1','fractionsTutorViews',null,null,'FT13');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (14,'equivalentFractionsProceduralIdea2','fractionsTutorViews',null,null,'FT14');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (15,'equivalentFractionsProceduralIdea3','fractionsTutorViews',null,null,'FT15');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (16,'equivalentFractionsProceduralIdea4','fractionsTutorViews',null,null,'FT16');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (17,'equivalentFractionsProcedural1_1_4','fractionsTutorViews',null,null,'FT17');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (18,'equivalentFractionsProcedural2_9_12','fractionsTutorViews',null,null,'FT18');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (19,'equivalentFractionsProcedural3_1_3','fractionsTutorViews',null,null,'FT19');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (20,'equivalentFractionsProcedural4_3_5','fractionsTutorViews',null,null,'FT20');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (21,'equivalentFractionsProcedural1_3_7','fractionsTutorViews',null,null,'FT21');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (22,'equivalentFractionsProcedural2_9_18','fractionsTutorViews',null,null,'FT22');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (23,'equivalentFractionsProcedural3_2_5','fractionsTutorViews',null,null,'FT23');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (24,'equivalentFractionsProcedural4_5_8','fractionsTutorViews',null,null,'FT24');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (25,'equivalentFractionsProcedural1_3_8','fractionsTutorViews',null,null,'FT25');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (26,'equivalentFractionsProcedural2_10_16','fractionsTutorViews',null,null,'FT26');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (27,'equivalentFractionsProcedural3_3_7','fractionsTutorViews',null,null,'FT27');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (28,'equivalentFractionsProcedural4_7_11','fractionsTutorViews',null,null,'FT28');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (29,'FractionsLab5','fractionsLabViews',null,null,'FL29');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (30,'FractionsLab6','fractionsLabViews',null,null,'FL30');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (31,'FractionsLab7','fractionsLabViews',null,null,'FL31');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (32,'FractionsLab8','fractionsLabViews',null,null,'FL32');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (33,'WhizzHTML5-2','views',null,null,'WH33');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (34,'WhizzFlash7','whizzViews',null,null,'MA_GBR_1275CAx0100');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (35,'FractionsLabGerman1','fractionsLabViews','description',null,'FL35');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (36,'FractionsLabGerman2','fractionsLabViews','description',null,'FL36');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (37,'FractionsLabGerman3','fractionsLabViews','description',null,'FL37');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (38,'FractionsLabGerman4','fractionsLabViews','description',null,'FL38');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (39,'FractionsLabGerman5','fractionsLabViews','description',null,'FL39');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (40,'FractionsLabGerman6','fractionsLabViews','description',null,'FL40');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (41,'FractionsLabGerman7','fractionsLabViews','description',null,'FL41');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (42,'FractionsLabGerman8','fractionsLabViews','description',null,'FL42');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (43,'FractionsLabGerman9','fractionsLabViews','description',null,'FL43');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (44,'FractionsLabGerman10','fractionsLabViews','description',null,'FL44');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (45,'FractionsLabGerman11','fractionsLabViews','description',null,'FL45');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (46,'FractionsLabGerman12','fractionsLabViews','description',null,'FL46');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (47,'FractionsLabGerman13','fractionsLabViews','description',null,'FL47');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (48,'FractionsLabGerman14','fractionsLabViews','description',null,'FL48');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (49,'FractionsLabGerman15','fractionsLabViews','description',null,'FL49');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (50,'FractionsLabGerman16','fractionsLabViews','description',null,'FL50');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (51,'FractionsLabGerman17','fractionsLabViews','description',null,'FL51');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (52,'FractionsLabGerman18','fractionsLabViews','description',null,'FL52');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (53,'FractionsLabGerman19','fractionsLabViews','description',null,'FL53');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (54,'FractionsLabGerman20','fractionsLabViews','description',null,'FL54');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (55,'FractionsLabGerman21','fractionsLabViews','description',null,'FL55');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (56,'FractionsLab9','fractionsLabViews','description',null,'FL56');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (57,'FractionsLab10','fractionsLabViews',null,null,'FL57');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (58,'FractionsLab11','fractionsLabViews',null,null,'FL58');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (59,'FractionsLab12','fractionsLabViews',null,null,'FL59');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (60,'FractionsLab13','fractionsLabViews',null,null,'FL60');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (61,'task2graph_8-12','fractionsTutorViews','description',null,'FT61');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (62,'task1graph_1-4','fractionsTutorViews','description',null,'FT62');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (63,'task3graph_1-2','fractionsTutorViews','description',null,'FT63');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (64,'task8graph_1-5','fractionsTutorViews','description',null,'FT64');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (65,'task5graph_3-4','fractionsTutorViews','description',null,'FT65');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (66,'task10graph_1-2','fractionsTutorViews','description',null,'FT66');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (67,'task2graph_9-12','fractionsTutorViews','description',null,'FT67');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (68,'task1graph_3-4','fractionsTutorViews','description',null,'FT68');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (69,'task3graph_1-3','fractionsTutorViews','description',null,'FT69');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (70,'task9graph_4-5','fractionsTutorViews','description',null,'FT70');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (71,'task6graph_1-5','fractionsTutorViews','description',null,'FT71');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (72,'task11graph_2-3','fractionsTutorViews','description',null,'FT72');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (73,'task2graph_9-18','fractionsTutorViews','description',null,'FT73');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (74,'task1graph_3-7','fractionsTutorViews','description',null,'FT74');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (75,'task3graph_2-5','fractionsTutorViews','description',null,'FT75');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (76,'task7graph_1-6','fractionsTutorViews','description',null,'FT76');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (77,'task2graph_10-16','fractionsTutorViews','description',null,'FT77');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (78,'task1graph_3-8','fractionsTutorViews','description',null,'FT78');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (79,'task3graph_3-7','fractionsTutorViews','description',null,'FT79');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (80,'task7graph_3-8','fractionsTutorViews','description',null,'FT80');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (81,'task2graph_10-17','fractionsTutorViews','description',null,'FT81');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (82,'FractionsLab14','fractionsLabViews','description',null,'FL82');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (83,'FractionsLab15','fractionsLabViews','description',null,'FL83');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (84,'FractionsLab16','fractionsLabViews','description',null,'FL84');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (85,'FractionsLab17','fractionsLabViews','description',null,'FL85');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (86,'FractionsLabGerman1_el','fractionsLabViews','description',null,'FL86');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (87,'FractionsLabGerman2_el','fractionsLabViews','description',null,'FL87');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (88,'FractionsLabGerman3_el','fractionsLabViews','description',null,'FL88');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (89,'FractionsLabGerman4_el','fractionsLabViews','description',null,'FL89');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (90,'FractionsLabGerman5_el','fractionsLabViews','description',null,'FL90');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (91,'FractionsLabGerman6_el','fractionsLabViews','description',null,'FL91');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (92,'FractionsLabGerman7_el','fractionsLabViews','description',null,'FL92');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (93,'FractionsLabGerman8_el','fractionsLabViews','description',null,'FL93');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (94,'FractionsLab18','fractionsLabViews','description',null,'FL94');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (95,'FractionsLab19','fractionsLabViews','description',null,'FL95');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (96,'FractionsLab20','fractionsLabViews','description',null,'FL96');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (97,'FractionsLab21','fractionsLabViews',null,null,'FL97');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (98,'FractionsLab22','fractionsLabViews',null,null,'FL98');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (99,'FractionsLab23','fractionsLabViews',null,null,'FL99');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (100,'FractionsLab24','fractionsLabViews',null,null,'FL100');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (101,'FractionsLab25','fractionsLabViews',null,null,'FL101');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (102,'WhizzFlashTest1','whizzViewsTest','desc',null,'WT102');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (103,'MA_GBR_0775CAx0100','whizzViews','description','fl_task','WZ103');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (104,'MA_GBR_0800CAx0100','whizzViews','description','fl_task','WZ104');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (105,'MA_GBR_0850CAx0100','whizzViews','description','fl_task','WZ105');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (106,'MA_GBR_0825CAx0100','whizzViews','description','fl_task','WZ106');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (107,'MA_GBR_0950CAx0100','whizzViews','description','fl_task','WZ107');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (108,'MA_GBR_0900CAx0100','whizzViews','description','fl_task','WZ108');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (109,'MA_GBR_1200CAx0100','whizzViews','description','fl_task','WZ109');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (110,'MA_GBR_1300CAx0200','whizzViews','description','fl_task','WZ110');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (111,'MA_GBR_1125CAx0100','whizzViews','description','fl_task','WZ111');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (112,'MA_GBR_1025CAx0200','whizzViews','description','fl_task','WZ112');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (113,'MA_GBR_1150CAx0100','whizzViews','description','fl_task','WZ113');
insert into `exercises`(`id_exercise`,`exercise`,`view`,`description`,`fl_task`,`id_sequencer`) values (114,'MA_GBR_1150CAx0300','whizzViews','description','fl_task','WZ114');

CREATE TABLE `sequence` (
  `id_user` int(10) DEFAULT NULL,
  `id_exercise` int(10) DEFAULT NULL,
  `id_sequence` int(10) NOT NULL AUTO_INCREMENT,
  `id_nextexercise` int(10) DEFAULT NULL,
  `feedback` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id_sequence`),
  KEY `sequence_ibfk_1` (`id_user`),
  KEY `Unnamed2` (`id_exercise`),
  CONSTRAINT `sequence_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
  CONSTRAINT `Unnamed2` FOREIGN KEY (`id_exercise`) REFERENCES `exercises` (`id_exercise`)
) ENGINE=InnoDB AUTO_INCREMENT=8960 DEFAULT CHARSET=latin1;

CREATE TABLE `ctatexercise` (
  `id_ctatexercise` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `loganswer` varchar(20000) DEFAULT NULL,
  `id_exercise` int(10) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_ctatexercise`),
  KEY `id_user` (`id_user`),
  KEY `ctatexercise_exercise_fk` (`id_exercise`),
  CONSTRAINT `ctatexercise_exercise_fk` FOREIGN KEY (`id_exercise`) REFERENCES `exercises` (`id_exercise`),
  CONSTRAINT `ctatexercise_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=21980 DEFAULT CHARSET=latin1;

CREATE TABLE `exercisequiz` (
  `id_exercisequiz` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `jsonval` varchar(2000) DEFAULT NULL,
  `exView` varchar(50) DEFAULT NULL,
  `exName` varchar(50) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `typeQuiz` int(11) NOT NULL,
  PRIMARY KEY (`id_exercisequiz`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `exercisequiz_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=2142 DEFAULT CHARSET=latin1;

CREATE TABLE `flexercise` (
  `id_fltask` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `event` varchar(1000) DEFAULT NULL,
  `id_exercise` int(10) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_fltask`),
  KEY `id_user` (`id_user`),
  KEY `flexercise_exercise_fk` (`id_exercise`),
  CONSTRAINT `flexercise_exercise_fk` FOREIGN KEY (`id_exercise`) REFERENCES `exercises` (`id_exercise`),
  CONSTRAINT `flexercise_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=700531 DEFAULT CHARSET=latin1;

CREATE TABLE `snalog` (
  `id_snalog` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `snakey` varchar(50) DEFAULT NULL,
  `snavalue` varchar(1000) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_snalog`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `snalog_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=32826 DEFAULT CHARSET=latin1;


CREATE TABLE `studentmodel` (
  `id_studentmodel` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `isExploratoryExercise` tinyint(4) NOT NULL,
  `studentChallenge` int(10) NOT NULL,
  `currentExercise` varchar(100) NOT NULL,
  `unstructuredCounter` int(10) NOT NULL,
  `structuredCounter` int(10) NOT NULL,
  `lastExploratoryExercise` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_studentmodel`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `studentmodel_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;

CREATE TABLE `tislog` (
  `id_tislog` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `tiskey` varchar(50) DEFAULT NULL,
  `tisvalue` varchar(1000) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_tislog`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `tislog_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=21748 DEFAULT CHARSET=latin1;

CREATE TABLE `whizzexercise` (
  `id_whizzexercise` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `score` int(10) DEFAULT NULL,
  `percentage` int(10) DEFAULT NULL,
  `help1` varchar(100) DEFAULT NULL,
  `help2` varchar(100) DEFAULT NULL,
  `help3` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `total_questions` int(10) DEFAULT NULL,
  `id_exercise` int(10) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_whizzexercise`),
  KEY `id_user` (`id_user`),
  KEY `whizzexercise_exercise_fk` (`id_exercise`),
  CONSTRAINT `whizzexercise_exercise_fk` FOREIGN KEY (`id_exercise`) REFERENCES `exercises` (`id_exercise`),
  CONSTRAINT `whizzexercise_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=5513 DEFAULT CHARSET=latin1;

CREATE TABLE `audiostream` (
  `id_audiostream` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(10) NOT NULL,
  `bytearray` longblob NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_audiostream`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `audiostream_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=777 DEFAULT CHARSET=latin1;
