# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table documents (
  id                        bigint not null,
  file                      bytea not null,
  file_name                 varchar(255) not null,
  file_hash                 varchar(255) not null,
  file_extension            varchar(255),
  part_version_id           bigint,
  constraint uq_documents_file_hash unique (file_hash),
  constraint pk_documents primary key (id))
;

create table parts (
  id                        bigint not null,
  name                      varchar(255),
  modify_date               timestamp,
  number                    varchar(255) not null,
  create_date               timestamp not null,
  constraint uq_parts_number unique (number),
  constraint pk_parts primary key (id))
;

create table part_versions (
  discriminator             varchar(31) not null,
  id                        bigint not null,
  version                   integer,
  modify_date               timestamp,
  lifecycle_state_name      varchar(10) not null,
  part_id                   bigint not null,
  create_date               timestamp not null,
  diameter                  float,
  comment                   varchar(255),
  width                     float,
  height                    float,
  constraint ck_part_versions_lifecycle_state_name check (lifecycle_state_name in ('IN_WORK','IN_APPROVE','APPROVED','RELEASED','DISPOSED')),
  constraint pk_part_versions primary key (id))
;

create table persons (
  id                        bigint not null,
  name                      varchar(255) not null,
  full_name                 varchar(255),
  password_hash             varchar(255),
  salt                      varchar(255),
  title                     varchar(255),
  constraint uq_persons_name unique (name),
  constraint pk_persons primary key (id))
;

create sequence documents_seq;

create sequence parts_seq;

create sequence part_versions_seq;

create sequence persons_seq;

alter table documents add constraint fk_documents_partVersion_1 foreign key (part_version_id) references part_versions (id);
create index ix_documents_partVersion_1 on documents (part_version_id);
alter table part_versions add constraint fk_part_versions_part_2 foreign key (part_id) references parts (id);
create index ix_part_versions_part_2 on part_versions (part_id);



# --- !Downs

drop table if exists documents cascade;

drop table if exists parts cascade;

drop table if exists part_versions cascade;

drop table if exists persons cascade;

drop sequence if exists documents_seq;

drop sequence if exists parts_seq;

drop sequence if exists part_versions_seq;

drop sequence if exists persons_seq;

