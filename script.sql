select * from student
where age>21 and age<25;

select name from student;

select * from student
where name like ('%о%');

select * from student
where age<student.id;

select * from student
ORDER BY age;