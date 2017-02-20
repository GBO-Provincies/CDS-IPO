-- View: bron.vw_resultaat

-- DROP VIEW bron.vw_resultaat;

CREATE OR REPLACE VIEW bron.vw_resultaat AS 
 SELECT spatial_datasetinfo.code AS ds_code, spatial_datasetinfo.namespace AS ds_namespace, resultaat.id, resultaat.job_id, resultaat.gfid, resultaat.id_property, resultaat.type, resultaat.object_begin_tijd, resultaat.datum_invoeren, resultaat.datum_gepubliceerd, resultaat.publiceren, resultaat.risico_niveau, resultaat.numerieke_waarde, resultaat.monster_id, resultaat.eenheid_code, resultaat.eenheid_omschrijving, resultaat.taxon_code, resultaat.taxon_omschrijving, resultaat.kwaliteits_oordeel_code, resultaat.kwaliteits_oordeel_omschrijving, resultaat.waarde_bewerkings_methode_code, resultaat.waarde_bewerkings_methode_omschrijving, resultaat.object_code, resultaat.object_omschrijving, resultaat.typering_waarneming_code, resultaat.typering_waarneming_omschrijving, resultaat.limiet_symbool_code, resultaat.limiet_symbool_omschrijving, resultaat.hoedanigheid_code, resultaat.hoedanigheid_omschrijving, resultaat.waarde_bepalings_methode_code, resultaat.waarde_bepalings_methode_omschrijving, resultaat.chemische_stof_code, resultaat.chemische_stof_omschrijving, resultaat.grootheid_code, resultaat.grootheid_omschrijving
   FROM bron.resultaat, metadata.spatial_datasetinfo
   JOIN metadata.service ON spatial_datasetinfo.service_id = service.id
  WHERE spatial_datasetinfo.type::text = 'WFS'::text AND service.name = 'download_EF'::text AND spatial_datasetinfo.name::text = 'app:Monsterresultaat'::text;

ALTER TABLE bron.vw_resultaat
  OWNER TO inspire;
