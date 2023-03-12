alter table tasks
add user_id int not null references users(id);