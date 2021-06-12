-- SQL data
INSERT INTO Payroll VALUES('1b684b46-6058-452c-96b6-f782f1d0ba72','June');
INSERT INTO Jet VALUES('ee509169-7394-4427-8762-eabd551ea75c','Airbus',120);
INSERT INTO employee VALUES('52809b05-b847-48ac-96b2-48081ef549a0','Jill','supervisor');
INSERT INTO employee VALUES('74227894-2140-4c4c-9e0d-8524d379e743','Jack','technician');
INSERT INTO TheChild VALUES('47be6cfa-7c02-45de-bd4a-5d865aa25fe5','Animal');
INSERT INTO TheChild VALUES('66392c1c-39f5-4319-bd78-7b24306d3491','Man');
INSERT INTO TheParent VALUES('3139c92f-8748-45ec-9165-3440e6502614','LivingCreature');
INSERT INTO TheParent VALUES('51c09141-25ee-4acc-b306-71ed4b60d337','LivingCreature');
INSERT INTO Car VALUES('0a6329b8-5489-41ed-8f8a-cef21074b45e',0);
INSERT INTO coOwner VALUES('d58dd483-7b34-44ed-ae16-8a74fda646e8');
INSERT INTO classA VALUES('4ed7dbd1-feff-45f3-baae-f0deb0e1b2c0');
INSERT INTO expiration VALUES('8b61e742-b894-4f70-ad67-c8af4852922d',2023);
INSERT INTO Aircraft VALUES('c7206bca-d9f5-4b13-8fd8-6d5a1aa06057','EI-123');
INSERT INTO Aircraft VALUES('d5df8018-9fe2-40ff-a575-5bfca037a92a','1055W');
INSERT INTO payElement VALUES('98f5a460-3458-4597-b641-83e27f14b331','bonus',50,0);
INSERT INTO payElement VALUES('9abce529-d2cc-45c3-a27b-87936181c81e','salary',400,90);
INSERT INTO payElement VALUES('9b64cd51-8285-4ccb-9f6c-daabbb180bd1','',0,0);
INSERT INTO payElement VALUES('ae980762-7660-4f9c-951c-2c03d7396209','',0,0);
INSERT INTO payElement VALUES('1a3a4d80-f0e0-4db1-9ebb-afb728c2b86a','',0,0);
INSERT INTO thingmajig VALUES('90285b53-62cc-46a6-b86b-51b652fae793',1001);
INSERT INTO thingmajig VALUES('a6085e1b-0cf7-4f71-a23c-12635d0eca5c',1002);
INSERT INTO classB VALUES('b697a040-ac1d-43e6-a4d1-6c90eae80194');
INSERT INTO Driver VALUES('93a7b669-e641-49e1-bbfc-f67ee514f9ac');
INSERT INTO City VALUES('a32f16a3-5cef-412e-8aea-34d765e1f973','wicklow');
INSERT INTO City VALUES('c0e70ef4-26ff-4658-b4ad-1741811f6f67','belfast');
INSERT INTO City VALUES('d89a3b68-7b8b-4369-8f52-a13adba07e13','dublin');
INSERT INTO City VALUES('19e324f9-4b01-4554-bda5-27cb60cdf661','limerick');
INSERT INTO payment VALUES('fb2c220e-1d96-4fb3-a50c-a4e8e4297f4b',0);
INSERT INTO payment VALUES('570b9787-4c77-4afc-9f54-29c0d36dc9eb',0);
INSERT INTO License VALUES('b4b509c2-e5bb-4a0c-a1c7-d8bb4dfe2e16');
INSERT INTO Registration VALUES('be085afa-99aa-4dd9-a9a1-62a01c71230c',50);
INSERT INTO Glider VALUES('15b71e8a-5f9a-4382-a06b-3ba5674ddf40',15);
INSERT INTO Route VALUES('a9138e55-3583-414b-b938-505214fdaca4',30);
INSERT INTO Route VALUES('b29b1789-a620-4c0e-8095-84fe6293ee57',90);
INSERT INTO Route VALUES('3ec37e8b-aaaf-45d9-bc26-ea3b183b5f80',0);
INSERT INTO Route VALUES('6a4313da-fe04-4e5b-a463-e646bcebfaa7',110);
LINK2  13 IDS('47be6cfa-7c02-45de-bd4a-5d865aa25fe5','51c09141-25ee-4acc-b306-71ed4b60d337');
LINK2  13 IDS('66392c1c-39f5-4319-bd78-7b24306d3491','3139c92f-8748-45ec-9165-3440e6502614');
LINK2  4 IDS('98f5a460-3458-4597-b641-83e27f14b331','570b9787-4c77-4afc-9f54-29c0d36dc9eb');
LINK2  4 IDS('9abce529-d2cc-45c3-a27b-87936181c81e','570b9787-4c77-4afc-9f54-29c0d36dc9eb');
LINK2  4 IDS('9b64cd51-8285-4ccb-9f6c-daabbb180bd1','fb2c220e-1d96-4fb3-a50c-a4e8e4297f4b');
LINK2  4 IDS('ae980762-7660-4f9c-951c-2c03d7396209','fb2c220e-1d96-4fb3-a50c-a4e8e4297f4b');
LINK2  4 IDS('1a3a4d80-f0e0-4db1-9ebb-afb728c2b86a','570b9787-4c77-4afc-9f54-29c0d36dc9eb');
LINK3  6 IDS('be085afa-99aa-4dd9-a9a1-62a01c71230c','0a6329b8-5489-41ed-8f8a-cef21074b45e','93a7b669-e641-49e1-bbfc-f67ee514f9ac');
LINK2  3 IDS('fb2c220e-1d96-4fb3-a50c-a4e8e4297f4b','52809b05-b847-48ac-96b2-48081ef549a0');
LINK2  3 IDS('570b9787-4c77-4afc-9f54-29c0d36dc9eb','74227894-2140-4c4c-9e0d-8524d379e743');
LINK3  11 IDS('a9138e55-3583-414b-b938-505214fdaca4','d89a3b68-7b8b-4369-8f52-a13adba07e13','a32f16a3-5cef-412e-8aea-34d765e1f973');
LINK3  11 IDS('b29b1789-a620-4c0e-8095-84fe6293ee57','c0e70ef4-26ff-4658-b4ad-1741811f6f67','d89a3b68-7b8b-4369-8f52-a13adba07e13');
LINK3  11 IDS('3ec37e8b-aaaf-45d9-bc26-ea3b183b5f80','19e324f9-4b01-4554-bda5-27cb60cdf661','c0e70ef4-26ff-4658-b4ad-1741811f6f67');
LINK3  11 IDS('6a4313da-fe04-4e5b-a463-e646bcebfaa7','19e324f9-4b01-4554-bda5-27cb60cdf661','d89a3b68-7b8b-4369-8f52-a13adba07e13');
LINK2  5 IDS('98f5a460-3458-4597-b641-83e27f14b331','1b684b46-6058-452c-96b6-f782f1d0ba72');
LINK2  5 IDS('9abce529-d2cc-45c3-a27b-87936181c81e','1b684b46-6058-452c-96b6-f782f1d0ba72');
LINK2  5 IDS('9b64cd51-8285-4ccb-9f6c-daabbb180bd1','1b684b46-6058-452c-96b6-f782f1d0ba72');
LINK2  5 IDS('ae980762-7660-4f9c-951c-2c03d7396209','1b684b46-6058-452c-96b6-f782f1d0ba72');
LINK2  5 IDS('1a3a4d80-f0e0-4db1-9ebb-afb728c2b86a','1b684b46-6058-452c-96b6-f782f1d0ba72');
LINK3  7 IDS('8b61e742-b894-4f70-ad67-c8af4852922d','93a7b669-e641-49e1-bbfc-f67ee514f9ac','b4b509c2-e5bb-4a0c-a1c7-d8bb4dfe2e16');
LINK2  8 IDS('d58dd483-7b34-44ed-ae16-8a74fda646e8','90285b53-62cc-46a6-b86b-51b652fae793');
LINK2  8 IDS('d58dd483-7b34-44ed-ae16-8a74fda646e8','a6085e1b-0cf7-4f71-a23c-12635d0eca5c');
LINK2  12 IDS('15b71e8a-5f9a-4382-a06b-3ba5674ddf40','d5df8018-9fe2-40ff-a575-5bfca037a92a');
LINK2  12 IDS('ee509169-7394-4427-8762-eabd551ea75c','c7206bca-d9f5-4b13-8fd8-6d5a1aa06057');
LINK2  14 IDS('b697a040-ac1d-43e6-a4d1-6c90eae80194','4ed7dbd1-feff-45f3-baae-f0deb0e1b2c0');
