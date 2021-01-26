---- db:  -h localhost -p 5443 -U postgres mobiledb

---- Entities
create table if not exists public_user (id text primary key not null,
                                        device_id text);
----
create table if not exists settings (id text primary key not null,
                                     ts timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
                                     resource jsonb);
----
create table if not exists condition (id text primary key not null,
                                      user_id text,
                                      date_time timestamp,
                                      CONSTRAINT fk_user
                                      FOREIGN KEY(user_id)
                                      REFERENCES public_user(id)
                                      ON DELETE SET NULL,
                                      wellbeing text);
----
create table if not exists notification (id text primary key not null,
                                         user_id text,
                                         n_action text,
                                         category text,
                                         notification_rate bigint,
                                         date_time timestamp,
                                         CONSTRAINT fk_user
                                         FOREIGN KEY(user_id)
                                         REFERENCES public_user(id)
                                         ON DELETE SET NULL);
----
create table if not exists notification_result (id text primary key not null,
                                                notification_id text,
                                                date_time timestamp,
                                                category text,
                                                n_result text,
                                                n_action text);
----
create table if not exists settings (id text primary key not null,
                                     user_id text,
                                     enable_achievements boolean,
                                     enable_notifications boolean,
                                     enable_md_sync boolean,
                                     sync_date_time timestamp,
                                     sync_rate bigint,
                                     CONSTRAINT fk_user
                                     FOREIGN KEY(user_id)
                                     REFERENCES public_user(id)
                                     ON DELETE SET NULL);
----

---- Drop every table
drop table condition;
drop table notification_result;
drop table notification;
drop table settings;
drop table achievement;
drop table public_user;
----
select * from notification
----
explain analyze
select count(*) from notification n
left join notification_result n_r on n.id = n_r.notification_id
where n_r.id is not null
----
select * from notification n
where (select ARRAY_AGG(id) from notification_result where notification_id = n.id) is null
----
