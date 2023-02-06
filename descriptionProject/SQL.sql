create table if not exists roles
(
    id          serial primary key,
    name        varchar(255) not null,
    description varchar(255)
);

insert into roles (name, description)
VALUES ('USER', 'Пользователь');
insert into roles (name, description)
VALUES ('SUPPORT', 'Администратор');

create table if not exists users
(
    id         serial primary key,
    login      varchar(255) not null,
    password   varchar(255) not null,
    firstName  varchar(255) not null,
    middleName varchar(255),
    lastName   varchar(255) not null,
    phone      varchar(255) not null,
    email      varchar(255) not null,
    birthday   timestamp    not null,
    role_id    INTEGER REFERENCES roles (id)
);

create table if not exists labels
(
    id          serial primary key,
    name        varchar(255) not null,
    description varchar(255),
    user_id     INTEGER,
    foreign key (user_id) references users (id)
);

create table if not exists categories
(
    id          serial primary key,
    name        varchar(255) not null,
    description varchar(255)
);

create table if not exists accounts
(
    id            serial primary key,
    name          varchar(255) not null,
    description   varchar(255),
    amount        NUMERIC      not null,
    currency_type varchar(255),
    user_id       INTEGER,
    foreign key (user_id) references users (id)
);

create table if not exists actions
(
    id          serial primary key,
    action_type varchar(255),
    amount      NUMERIC,
    date_time   timestamp,
    account_id  INTEGER,
    category_id INTEGER,
    label_id    INTEGER,
    foreign key (account_id) references accounts (id),
    foreign key (category_id) references categories (id),
    foreign key (label_id) references labels (id)
);