alter table okjsp2014.user change id id bigint(20) not null;
alter table okjsp2014.person change id id bigint(20) not null;
alter table okjsp2014.avatar change id id bigint(20) not null;

insert into okjsp2014.person select sid, 1, null, joindate, mailing, email, name, homepage, now(), null from okjsp.okmember where email is not null   group by email having count(email) = 1;

insert into okjsp2014.avatar select sid, 1, point, id, email, 'gravatar' from okjsp.okmember where email is not null   group by email having count(email) = 1;

insert into okjsp2014.user select sid, 1, false, false, sid, '0.0.0.0', joindate, true, now(), '0.0.0.0', now(), `password`, false, sid, id from okjsp.okmember where email is not null   group by email having count(email) = 1;

insert into okjsp2014.user_role select 2, sid from okjsp.2014.user where email is not null   group by email having count(email) = 1;

insert into okjsp2014.user_role select 2, id from okjsp2014.user;

update avatar set picture = md5(picture);

-- 칼럼
