-- =========================
-- PAPER STOCK
-- =========================
INSERT INTO paper_stock VALUES
                            (1,'LEAD_PAPER',_binary ''),
                            (2,'MATTE_PAPER',_binary ''),
                            (3,'ART_PAPER',_binary ''),
                            (4,'ART_CARD',_binary ''),
                            (5,'BLEACH_CARD',_binary ''),
                            (6,'CARBONLESS',_binary ''),
                            (7,'COTTON_PAPER',_binary ''),
                            (8,'LINEN_PAPER',_binary ''),
                            (9,'OFFSET',_binary '');

-- =========================
-- BRAND
-- =========================
INSERT INTO brand VALUES
                      (1,'Pindo',_binary '',1),
                      (2,'Mindo',_binary '',1),
                      (3,'Ik',_binary '',1),
                      (4,'Pindo',_binary '',2),
                      (5,'Mindo',_binary '',2),
                      (6,'Ik',_binary '',2),
                      (7,'Pindo',_binary '',3),
                      (8,'Mindo',_binary '',3),
                      (9,'Ik',_binary '',3),
                      (10,'Pindo',_binary '',4),
                      (11,'Ik',_binary '',4),
                      (12,'Pindo',_binary '',5),
                      (13,'Mindo',_binary '',5),
                      (14,'Pindo',_binary '',6),
                      (15,'Mindo',_binary '',6),
                      (16,'Ik',_binary '',6),
                      (17,'Pindo',_binary '',7),
                      (18,'Mindo',_binary '',7),
                      (19,'Ik',_binary '',7),
                      (20,'Mindo',_binary '',8),
                      (21,'Ik',_binary '',8),
                      (22,'Pindo',_binary '',9),
                      (23,'Mindo',_binary '',9),
                      (24,'Ik',_binary '',9);

-- =========================
-- CUSTOMER
-- =========================
INSERT INTO customer VALUES
                         (1,'Tbh','2023-09-12','kanwar','Active'),
                         (2,'Bas','2023-09-20','saad','Active');

-- =========================
-- ROLES
-- =========================
INSERT INTO roles (id, name) VALUES
                                 (1,'ROLE_ADMIN'),
                                 (2,'ROLE_USER');

-- =========================
-- PERMISSIONS
-- =========================
INSERT INTO permissions (id, name, value) VALUES
                                                  (1,'Dashboard',1),
                                                  (3,'Customers',1),
                                                  (4,'Orders',1),
                                                  (5,'Calculator',1),
                                                  (6,'Configuration',1),
                                                  (7,'Process',1),
                                                  (8,'Permissions',1),
                                                  (9,'Configuration_Product_Field',1),
                                                  (10,'Configuration_Paper_Market_Rate',1),
                                                  (11,'Configuration_Paper_Size',1),
                                                  (12,'Configuration_Press_Machine',1),
                                                  (13,'Configuration_Uping',1),
                                                  (14,'Configuration_Vendor',1),
                                                  (15,'Configuration_Product_Process',1),
                                                  (16,'Configuration_Settings',1),
                                                  (17,'Configuration_CTP',1),
                                                  (18,'Configuration_Inventory',1),
                                                  (19,'ProductRule',1),
                                                  (20,'PaperStock',1),
                                                  (21,'User',1);

-- =========================
-- ROLE PERMISSIONS
-- =========================
INSERT INTO role_permissions VALUES
                                 (1,1),(2,1),(1,3),(2,3),(1,4),(2,4),
                                 (1,5),(1,6),(1,7),(1,8),(1,9),(1,10),
                                 (1,11),(1,12),(1,13),(1,14),(1,15),
                                 (1,16),(1,17),(1,18),(1,19),(1,20),(1,21);

-- =========================
-- USERS
-- =========================
INSERT INTO users VALUES
    (1,"123456","2023-10-31 16:58:23.537000","admin@gmail.com","Admin",
     "$2a$10$nWT6vXsQcnwjQJtOqJP/9.q.1YxTNTaaarYuf4i.eTvlBj.nhrGiu",
     "0335331855",_binary '');

-- =========================
-- USER ROLES
-- =========================
INSERT INTO user_roles VALUES
    (1,1);