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

create table cart
(
    id     bigint primary key,
    status varchar(255) not null default 'AVAILABLE'
);

create table cart_gift
(
    cart_id bigint not null,
    gift_id bigint not null,

    primary key (cart_id, gift_id),

    constraint fk_cart_gift_cart
        foreign key (cart_id)
            references cart (id),

    constraint fk_cart_gift_gift
        foreign key (gift_id)
            references gifts (id)
);

create table categories
(
    id    bigint primary key,
    title varchar(255) not null,
);

create table category_gift
(
    category_id bigint not null,
    gift_id     bigint not null,

    primary key (category_id, gift_id),

    constraint fk_category_gift_category
        foreign key (category_id)
            references category (id),

    constraint fk_category_gift_gift
        foreign key (gift_id)
            references gifts (id)
);
