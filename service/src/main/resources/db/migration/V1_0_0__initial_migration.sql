create table book
(
    id         bigint auto_increment
        primary key,
    created_at datetime null,
    updated_at datetime null
) ;

create table book_meta
(
    id           bigint auto_increment
        primary key,
    created_at   datetime     null,
    updated_at   datetime     null,
    author_name  varchar(255) null,
    book_id      bigint       not null,
    description  varchar(255) null,
    name         varchar(255) null,
    no_of_copy   int          null,
    release_date varchar(255) null
);

create table user
(
    id         bigint auto_increment
        primary key,
    created_at datetime     null,
    updated_at datetime     null,
    email      varchar(255) null,
    full_name  varchar(255) null,
    user_name  varchar(255) null,
    password   varchar(255) null
);

create table role
(
    id         bigint auto_increment
        primary key,
    created_at datetime null,
    updated_at datetime null,
    name    varchar(255)   not null
) ;

create table user_book
(
    user_id bigint,
    book_id bigint
);

create index user_book_user_id_fk
    on user_book (user_id);

create index user_book_book_id_fk
    on user_book (book_id);


create table user_role
(
    user_id bigint,
    role_id bigint
);

create index user_role_user_id_fk
    on user_role (user_id);

create index user_book_role_id_fk
    on user_role (role_id);
