create table chats (id int identity primary key, user_id int references users(id) not null, message_id int references messages(id) not null)