# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table part_versions (
  discriminator             varchar(31) not null,
  id                        bigint auto_increment not null,
  version                   integer,
  modify_date               datetime,
  lifecycle_state_name      varchar(10) not null,
  part_id                   bigint not null,
  create_date               datetime not null,
  constraint ck_part_versions_lifecycle_state_name check (lifecycle_state_name in ('IN_WORK','IN_APPROVE','APPROVED','RELEASED','DISPOSED')),
  constraint pk_part_versions primary key (id))
;

create table parts (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  modify_date               datetime,
  number                    varchar(255),
  create_date               datetime not null,
  constraint pk_parts primary key (id))
;

alter table part_versions add constraint fk_part_versions_part_1 foreign key (part_id) references parts (id) on delete restrict on update restrict;
create index ix_part_versions_part_1 on part_versions (part_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table part_versions;

drop table parts;

SET FOREIGN_KEY_CHECKS=1;

