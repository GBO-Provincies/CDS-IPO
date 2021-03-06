--
-- Monsterpunt
--
-- Names must match constants defined corresponding ThemeConfig
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'Monsterpunt');
	
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'Zwemwater monsterpunt', (select id from manager.thema t where t.naam = 'Monsterpunt'));

insert into manager.bronhouder (id, code, contact_naam, contact_adres, contact_postcode, contact_plaats, naam, contact_emailadres, contact_telefoonnummer, common_name) select nextval('manager.hibernate_sequence'), 'zwr', 'ZwemwaterRegister', '', '', '', 'ZwemwaterRegister', 'inspire@gbo-provincies.nl', '', 'zwemwaterregister' where not exists (select * from manager.bronhouder where code = 'zwr');

insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'Monsterpunt'), (select id from manager.bronhouder where code = 'zwr') );
--
