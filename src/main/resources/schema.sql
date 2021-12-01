
CREATE TABLE track (
	id SERIAL PRIMARY KEY,
	isrc VARCHAR ( 200 ) UNIQUE NOT NULL,
	name VARCHAR ( 500 ) NOT NULL,
	duration NUMERIC ( 12 ) NOT NULL,
	explicit BOOLEAN NOT NULL
);

-- delete from track;
select * from track;