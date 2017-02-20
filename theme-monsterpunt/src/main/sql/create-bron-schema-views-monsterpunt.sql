-- View: bron.vw_monsterpunt

-- DROP VIEW bron.vw_monsterpunt;

CREATE OR REPLACE VIEW bron.vw_monsterpunt AS 
 SELECT spatial_datasetinfo.code AS ds_code, spatial_datasetinfo.namespace AS ds_namespace, monsterpunt.id, monsterpunt.job_id, monsterpunt.gfid, monsterpunt.id_property, monsterpunt.meting_omschrijving, monsterpunt.zwemwaterlocatie_id, monsterpunt.waterbeheerder_code, monsterpunt.waterbeheerder_omschrijving, monsterpunt.type_code, monsterpunt.type_omschrijving, monsterpunt.locatie
   FROM bron.monsterpunt, metadata.spatial_datasetinfo
   JOIN metadata.service ON spatial_datasetinfo.service_id = service.id
  WHERE spatial_datasetinfo.type::text = 'WFS'::text AND service.name = 'download_EF'::text AND spatial_datasetinfo.name::text = 'app:Monsterpunt'::text;

ALTER TABLE bron.vw_monsterpunt
  OWNER TO inspire;
