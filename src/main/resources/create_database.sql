CREATE TABLE public."format" (
	id serial4 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT format_name_key UNIQUE (name),
	CONSTRAINT format_pkey PRIMARY KEY (id)
);

CREATE TABLE public.status (
	id serial4 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT status_name_key UNIQUE (name),
	CONSTRAINT status_pkey PRIMARY KEY (id)
);

CREATE TABLE public.book (
	id serial4 NOT NULL,
	author varchar(255) NOT NULL,
	title varchar(255) NOT NULL,
	pages int4 NULL,
	date_started date NULL,
	date_finished date NULL,
	format_id int4 DEFAULT 2 NOT NULL,
	status_id int4 DEFAULT 3 NOT NULL,
	rating int4 NULL,
	notes text NULL,
	description text NULL,
	CONSTRAINT book_pkey PRIMARY KEY (id),
	CONSTRAINT book_rating_check CHECK (((rating >= 0) AND (rating <= 10))),
	CONSTRAINT book_format_id_fkey FOREIGN KEY (format_id) REFERENCES public."format"(id) ON DELETE CASCADE,
	CONSTRAINT book_status_id_fkey FOREIGN KEY (status_id) REFERENCES public.status(id) ON DELETE CASCADE
);

INSERT INTO public.status ("name") VALUES
	 ('прочитано'),
	 ('буду читать'),
	 ('отложено'),
	 ('читаю сейчас');

INSERT INTO public."format" ("name") VALUES
	 ('аудиокнига'),
	 ('бумажная книга'),
	 ('электронная книга');

INSERT INTO public.book (author,title,pages,date_started,date_finished,format_id,status_id,rating,notes,description) VALUES
	 ('Рэй Брэдбери','Вино из одуванчиков',NULL,NULL,NULL,3,3,NULL,NULL,NULL),
	 ('Рэй Брэдбери','Путешествие во времени',NULL,'2024-05-05','2024-05-25',3,1,8,NULL,NULL),
	 ('Александр Сергеевич Пушкин','Евгений Онегин',800,NULL,NULL,2,3,5,'CСкучное произведение',NULL);

