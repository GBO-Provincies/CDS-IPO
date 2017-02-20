-- View: bron.vw_bvb

-- DROP VIEW bron.vw_bvb;

CREATE OR REPLACE VIEW bron.vw_bvb AS 
 SELECT spatial_datasetinfo.code AS ds_code, spatial_datasetinfo.namespace AS ds_namespace, bvb.id, bvb.job_id, bvb.gfid, bvb.identificatie, bvb.gemeente, bvb.dossier, bvb.bedrijfs_pchn, bvb.gebouwnummer, bvb.emissienummer, bvb.stalnummer, bvb.herkomst_xy, bvb.beschikking, bvb.datum_besluit, bvb.status, bvb.diercategorie, bvb.rav_code, bvb.additionele_techniek_rav, bvb.dieraantal, bvb.nh3_factor, bvb.gebouwhoogte, bvb.diameter, bvb.schoorsteenhoogte, bvb.herkomst_ep_info, bvb.geometry
   FROM bron.bvb, metadata.spatial_datasetinfo
   JOIN metadata.service ON spatial_datasetinfo.service_id = service.id
  WHERE spatial_datasetinfo.type::text = 'WFS'::text AND service.name = 'download_AF'::text AND spatial_datasetinfo.name::text = 'app:Stalgroep'::text;

ALTER TABLE bron.vw_bvb
  OWNER TO inspire;
