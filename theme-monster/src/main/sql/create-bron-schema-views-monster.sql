-- View: bron.vw_monster

-- DROP VIEW bron.vw_monster;

CREATE OR REPLACE VIEW bron.vw_monster AS 
 SELECT spatial_datasetinfo.code AS ds_code, spatial_datasetinfo.namespace AS ds_namespace, monster.id, monster.job_id, monster.gfid, monster.id_property, monster.monster_status, monster.naam, monster.object_begin_tijd, monster.compartiment_code, monster.compartiment_omschrijving, monster.monsterpunt_id
   FROM bron.monster, metadata.spatial_datasetinfo
   JOIN metadata.service ON spatial_datasetinfo.service_id = service.id
  WHERE spatial_datasetinfo.type::text = 'WFS'::text AND service.name = 'download_EF'::text AND spatial_datasetinfo.name::text = 'app:Monster'::text;

ALTER TABLE bron.vw_monster
  OWNER TO inspire;
