-- Fichier de création de la table authorities de la base de données bankroot

CREATE TABLE 
	authorities(
		authority_id SERIAL PRIMARY KEY USING INDEX TABLESPACE ts_index
		, authority VARCHAR(50) NOT NULL
    )
TABLESPACE 
	ts_data;
	
