create table song
(
  id                            bigserial    not null,
  song_name                     varchar      not null,
  insert_dt                     timestamp,
  primary key (id)
);

create table slide
(
  id                            bigserial    not null,
  song_id                       bigint       not null references song (id),
  slide_text                    varchar      not null,
  insert_dt                     timestamp,
  primary key (id)
);