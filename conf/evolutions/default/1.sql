# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table documents (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  file                      longblob,
  part_version_id           bigint,
  constraint pk_documents primary key (id))
;

create table parts (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  modify_date               datetime,
  number                    varchar(255) not null,
  create_date               datetime not null,
  constraint uq_parts_number unique (number),
  constraint pk_parts primary key (id))
;

create table part_versions (
  discriminator             varchar(31) not null,
  id                        bigint auto_increment not null,
  version                   integer,
  modify_date               datetime,
  lifecycle_state_name      varchar(10) not null,
  part_id                   bigint not null,
  create_date               datetime not null,
  diameter                  double,
  comment                   varchar(255),
  width                     double,
  height                    double,
  constraint ck_part_versions_lifecycle_state_name check (lifecycle_state_name in ('IN_WORK','IN_APPROVE','APPROVED','RELEASED','DISPOSED')),
  constraint pk_part_versions primary key (id))
;

create table persons (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  full_name                 varchar(255),
  password_hash             varchar(255),
  salt                      varchar(255),
  title                     varchar(255),
  constraint uq_persons_name unique (name),
  constraint pk_persons primary key (id))
;

alter table documents add constraint fk_documents_partVersion_1 foreign key (part_version_id) references part_versions (id) on delete restrict on update restrict;
create index ix_documents_partVersion_1 on documents (part_version_id);
alter table part_versions add constraint fk_part_versions_part_2 foreign key (part_id) references parts (id) on delete restrict on update restrict;
create index ix_part_versions_part_2 on part_versions (part_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table documents;

drop table parts;

drop table part_versions;

drop table persons;

SET FOREIGN_KEY_CHECKS=1;

