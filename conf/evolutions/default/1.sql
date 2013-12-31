# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table parts (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  modify_date               datetime,
  number                    varchar(255),
  create_date               datetime not null,
  constraint pk_parts primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table parts;

SET FOREIGN_KEY_CHECKS=1;

