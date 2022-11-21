create table users
(
    id            bigint primary key,
    user_name     varchar(255),
    birthday      timestamp,
    user_password varchar(255),
    email         varchar(255)
);

create table gifts
(
    id      bigint primary key,
    title   varchar(255),
    user_id bigint,
    foreign key (user_id) references users (id)
);




