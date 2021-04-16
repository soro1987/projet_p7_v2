
--
-- Data for Name: bibliotheque; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO bibliotheque VALUES (1, 'Hôtel de Sens, 1, rue du Figuier', 'MARDI, VENDREDI, SAMEDI :\r\n13H-19H30\r\nMERCREDI, JEUDI : 10H-19H30', 'bibliotheque.forney@paris.fr', 'Bibliothèque Forney', '01 42 78 14 60');
INSERT INTO bibliotheque VALUES (2, '164, rue de Grenelle', 'MARDI, JEUDI, VENDREDI :\r\n13H-19H/16H-19H\r\nMERCREDI : 10H-19H/10H-19H\r\nSAMEDI :10H-18H/10H-18H', 'bibliotheque.amelie@paris.fr', 'Bibliothèque Amelie', '01 47 05 89 66');
INSERT INTO bibliotheque VALUES (3, '11, rue Drouot', 'MARDI, JEUDI, VENDREDI : 12H30-19H\r\nMERCREDI, SAMEDI : 10H-18H', 'bibliotheque.drouot@paris.fr', 'Bibliothèque Drouot', '01 42 46 97 78');


--
-- Data for Name: ouvrage; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ouvrage VALUES (6, 'René Goscinny', 'Bande-Dessiné', '2020-11-11 15:45:23', 'Lucky Luke est une série de bande dessinée belge de western humoristique créée par le dessinateur belge Morris dans l''Almanach 1947, un hors-série du journal Spirou publié en 1946. Morris est aidé, à partir de la neuviÃšme histoire, par plusieurs scénarist', NULL, '../images/Lucky-luke.jpg', 0, 'Lucky Luke');
INSERT INTO ouvrage VALUES (8, 'Jean-Louis Boursin', 'scolaire', '2020-11-11 15:48:38', 'Jeunes frÃšres, enfants ou petits enfants à aider, déclaration de revenus à remplir, emprunts, placements, achat d''une moquette ou de la quantité exacte de peinture, les occasions sont innombrables d''utiliser les mathématiques \" élémentaires \", du niveau d', NULL, '../images/Math.jpg', 12, 'Les Maths pour les Nuls');
INSERT INTO ouvrage VALUES (3, 'Stan Lee', 'Bande-Dessiné', '2020-11-24 15:41:55', 'Peter Parker, alias Spider-Man est un super-héros évoluant dans l''univers Marvel de la maison d''édition Marvel Comics. Créé par le scénariste Stan Lee et le dessinateur Steve Ditko', NULL, '../images/Spiderman.jpg', 10, 'Spiderman');
INSERT INTO ouvrage VALUES (4, 'Jerry Siegel', 'Bande-Dessiné', '2020-11-10 15:41:55', 'Superman est un super-héros de bande dessinée américaine appartenant au monde imaginaire de l''Univers DC. Ce personnage est considéré comme une icÃŽne culturelle américaine', NULL, '../images/Superman.jpg', 4, 'Superman');
INSERT INTO ouvrage VALUES (5, 'Akira Toriyama', 'Bande-Dessiné', '2020-11-16 15:45:23', 'Cinq ans aprÃšs le mariage de Son Goku et de Chichi, Raditz, un mystérieux guerrier de l''espace, frÃšre de Son Goku, arrive sur Terre. Goku apprend qu''il vient d''une planÃšte de guerriers redoutables dont il ne reste plus que quatre survivants', NULL, '../images/Dbz.jpg', 11, 'Dragon Ball Z');
INSERT INTO ouvrage VALUES (2, 'Voltaire', 'roman', '2020-11-09 15:41:41', 'Brutus est une tragédie en cinq actes de Voltaire. Il a commencé à travailler sur la place en 1727 en Angleterre et l''a achevée en 1729. Elle a été créée le 11 décembre 1730 à Paris.', NULL, '../images/Brutus.jpg', 7, 'Brutus');
INSERT INTO ouvrage VALUES (1, 'Voltaire', 'roman', '2020-11-09 15:33:35', 'Candide ou l''Optimisme est un conte philosophique de Voltaire paru à GenÃšve en janvier 1759. Il a été réédité vingt fois du vivant de l''auteur, ce qui en fait l''un des plus grands succÃšs littéraires francophones.', NULL, '../images/Candide.jpg', 9, 'Candide');
INSERT INTO ouvrage VALUES (7, 'Masashi Kishimoto', 'Bande-Dessiné', '2020-11-01 11:26:18', 'Naruto est un manga écrit et dessiné par Masashi Kishimoto. Naruto a été prépublié dans l''hebdomadaire Weekly Jump de l''éditeur ShÅ«eisha entre septembre 1999 et novembre 2014. La série a été compilée en 72 tomes. La version franÃ§aise du mang', NULL, '../images/Naruto-Manga-1.jpg', 4, 'Naruto');
INSERT INTO ouvrage VALUES (9, 'Julaud ', 'scolaire', '2020-11-17 15:51:50', 'Chaque année, des mots naisse, d''autres sans vont aprÃšs avoir vécuent deux cents an, trois cents an, ou l''espasse d''un matin. Le franssais ait une langue vivante !\" Ah, ah ! Vous venez de sursauter, n''est-ce pas ?! Besoin d''un décodeur?', NULL, '../images/Francais.jpg', 5, 'Le franÃ§ais correct');
INSERT INTO ouvrage VALUES (10, 'Gail BRENNER', 'scolaire', '2020-11-04 15:51:50', 'Vous rÃªvez de pouvoir discuter simplement avec votre chÃšre et tendre anglophone sans avoir à mimer chaque mot ? D''impressionner votre boss en rédigeant parfaitement un mail en anglais ? Ou encore de ne plus confondre un haricot ( bean) avec une poubelle (', NULL, '../images/Anglais.jpg', 6, 'Je me mets à l''anglais pour les Nuls');
INSERT INTO ouvrage VALUES (11, 'AGATHA CHRISTIE', 'roman', '2020-11-24 15:55:16', 'Mort sur le Nil est un roman policier d''Agatha Christie publié le 1er novembre 1937 au Royaume-Uni chez Collins Crime Club, mettant en scÃšne une des plus célÃšbres enquÃªtes du détective belge Hercule Poirot. Il est publié l''année suivante aux États-Unis.', NULL, '../images/aff_mort_sur_nil-1.jpg', 4, 'Mort sur le Nil');
INSERT INTO ouvrage VALUES (12, 'AGATHA CHRISTIE', 'roman', '2020-11-24 15:55:16', 'Ils sont dix à avoir recu l''invitation : des vacances d''été sur l''ile du Soldat ! Voilà une proposition à laquelle personne ne saurait résister. Non seulement c''est gratuit, mais l''ile a tant fait parler d''elle ! Chacun se demande qui est son nouveau prop', NULL, '../images/Ils-etaient-dix.jpg', 9, 'Ils étaient dix ');
INSERT INTO ouvrage VALUES (10000, 'Jerry Siegel', 'Bande-Dessiné', '2020-11-09 15:33:35', 'Bruce Wayne, alias Batman, est un super-héros de fiction appartenant à l''univers de DC Comics. Créé par le dessinateur Bob Kane et le scénariste Bill', NULL, '../images/Batman.jpg', 3, 'Batman');


--
-- Data for Name: earliest_return_date; Type: TABLE DATA; Schema: public; Owner: postgres
--

--
-- Data for Name: sys_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO sys_user VALUES (1, 'testmailocr2305@gmail.com', 'amet', '$2a$10$nifo7Gzgyn2QdjpTaqBTZeFCBVKsoefxCKu8J7Cnmh13PpYrFS11m', 'soro', '352646664', 1, 'soro1987');
INSERT INTO sys_user VALUES (2, 'testmailocr2305@gmail.com', 'toto', '$2a$10$ACwnLUrnIhpUzcQmLpTx9ulSIXhE9o2YgBWMdI263v.Yowd/TFU7i', 'tata', '45636254646435', 1, 'toto');
INSERT INTO sys_user VALUES (3, 'testmailocr2305@gmail.com', 'dupond', '$2a$10$.AxJF8I8ON6sbVqYvacj8ON4h/oTICeApuCtSF71qTEI8ocs9Z2Wu', 'jean', '456456567', 1, 'jeanno');
INSERT INTO sys_user VALUES (4, 'testmailocr2305@gmail.com', 'macron', '$2a$10$Dcxs5ZmU2yhj.E75m1CgneIpibfq7dmiPSqWHxgJq/pO7w6/Hp2IS', 'manu', '5436576745', 1, 'manu');


--
-- Data for Name: emprunt; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (10, '2021-04-03 16:17:33.38', '2021-05-29 16:17:33.38', 0, true, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (8, '2021-04-03 16:16:07.614', '2021-06-26 16:16:07.614', 0, false, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (2, '2021-04-03 14:17:05.838', '2021-07-24 14:17:05.838', 0, false, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (3, '2021-04-03 14:30:21.085', '2021-07-24 14:30:21.085', 0, false, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (4, '2021-04-03 15:57:30.1', '2021-07-24 15:57:30.1', 0, false, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (7, '2021-04-03 16:12:45.948', '2021-07-24 16:12:45.948', 0, false, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (9, '2021-04-03 16:16:49.174', '2021-07-24 16:16:49.174', 0, false, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (5, '2021-04-03 16:05:58.928', '2021-03-26 16:05:58.928', 0, true, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (6, '2021-04-03 16:08:20.282', '2021-03-26 16:08:20.282', 0, false, 1);
INSERT INTO emprunt (id,date_debut,date_echeance,depassement,prolongation,user_id) VALUES (1, '2021-04-03 13:55:32.914', '2021-08-21 13:55:32.914', 0, true, 1);
--
-- Data for Name: exemplaire; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (5, true, NULL, 1, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (6, true, NULL, 1, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (7, true, NULL, 1, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (8, true, NULL, 1, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (9, true, NULL, 1, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (10, true, NULL, 1, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (11, true, NULL, 1, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (12, true, NULL, 1, NULL, 4);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (13, true, NULL, 1, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (14, true, NULL, 1, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (15, true, NULL, 1, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (16, true, NULL, 1, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (17, true, NULL, 1, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (18, true, NULL, 1, NULL, 7);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (19, true, NULL, 1, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (20, true, NULL, 1, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (21, true, NULL, 1, NULL, 9);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (22, true, NULL, 1, NULL, 10);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (23, true, NULL, 1, NULL, 11);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (24, true, NULL, 1, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (27, true, NULL, 1, NULL, 10000);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (28, true, NULL, 2, NULL, 10000);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (29, true, NULL, 3, NULL, 10000);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (30, true, NULL, 3, NULL, 1);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (31, true, NULL, 2, NULL, 1);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (32, true, NULL, 3, NULL, 1);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (33, true, NULL, 3, NULL, 1);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (34, true, NULL, 2, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (35, true, NULL, 2, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (36, true, NULL, 2, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (37, true, NULL, 3, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (38, true, NULL, 3, NULL, 2);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (39, true, NULL, 3, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (40, true, NULL, 3, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (41, true, NULL, 2, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (42, true, NULL, 2, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (43, true, NULL, 2, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (44, true, NULL, 2, NULL, 3);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (45, true, NULL, 2, NULL, 4);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (46, true, NULL, 2, NULL, 4);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (47, true, NULL, 3, NULL, 4);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (48, true, NULL, 3, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (49, true, NULL, 3, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (50, true, NULL, 3, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (51, true, NULL, 3, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (52, true, NULL, 3, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (53, true, NULL, 2, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (54, true, NULL, 2, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (55, true, NULL, 2, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (56, true, NULL, 2, NULL, 5);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (57, true, NULL, 2, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (58, true, NULL, 2, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (59, true, NULL, 2, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (60, true, NULL, 2, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (61, true, NULL, 3, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (62, true, NULL, 3, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (63, true, NULL, 3, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (64, true, NULL, 3, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (65, true, NULL, 3, NULL, 6);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (66, true, NULL, 3, NULL, 7);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (67, true, NULL, 3, NULL, 7);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (68, true, NULL, 2, NULL, 7);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (69, true, NULL, 2, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (70, true, NULL, 2, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (71, true, NULL, 2, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (72, true, NULL, 2, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (73, true, NULL, 3, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (74, true, NULL, 3, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (75, true, NULL, 3, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (76, true, NULL, 3, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (77, true, NULL, 3, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (78, true, NULL, 3, NULL, 8);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (79, true, NULL, 3, NULL, 9);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (80, true, NULL, 3, NULL, 9);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (81, true, NULL, 2, NULL, 9);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (82, true, NULL, 2, NULL, 9);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (83, true, NULL, 2, NULL, 10);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (84, true, NULL, 2, NULL, 10);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (85, true, NULL, 2, NULL, 10);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (86, true, NULL, 2, NULL, 10);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (87, true, NULL, 3, NULL, 10);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (88, true, NULL, 3, NULL, 11);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (89, true, NULL, 3, NULL, 11);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (90, true, NULL, 2, NULL, 11);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (91, true, NULL, 2, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (92, true, NULL, 2, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (93, true, NULL, 2, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (94, true, NULL, 3, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (95, true, NULL, 3, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (96, true, NULL, 3, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (97, true, NULL, 3, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (98, true, NULL, 3, NULL, 12);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (1, false, NULL, 1, NULL, 1);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (2, false, NULL, 1, 7, 1);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (3, false, NULL, 1, 8, 1);
INSERT INTO exemplaire (id,disponible,sys_user,bibliotheque,emprunt_id,ouvrage) VALUES (4, false, NULL, 1, 10, 1);






--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO reservation (id,date_reservation,ouvrage_id,user_id,mail_send_time) VALUES (153, '2021-04-07 20:54:22.693',2, 1, '2021-04-07 20:54:25.11');
INSERT INTO reservation (id,date_reservation,ouvrage_id,user_id,mail_send_time)VALUES (155, '2021-04-08 20:22:01.435',1, 1, '2021-04-08 20:22:05.225');
INSERT INTO reservation (id,date_reservation,ouvrage_id,user_id,mail_send_time)VALUES (156, '2021-04-08 20:24:37.983',8, 1, '2021-04-08 20:24:40.278');


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_roles VALUES (1, 'ROLE_USER');
INSERT INTO user_roles VALUES (2, 'ROLE_USER');
INSERT INTO user_roles VALUES (3, 'ROLE_USER');
INSERT INTO user_roles VALUES (4, 'ROLE_USER');


