-- SHOP++ INIT SQL
-- http://www.shopxx.net
-- Copyright (c) 2011 shopxx.net All Rights Reserved.
-- Revision: 2.0.5
-- Date: 2011-05

-- 初始化数据库表结构 --

create table [{shopxx_table_prefix}admin] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    department varchar(255) null,
    email varchar(255) not null,
    is_account_enabled tinyint not null,
    is_account_expired tinyint not null,
    is_account_locked tinyint not null,
    is_credentials_expired tinyint not null,
    locked_date datetime null,
    login_date datetime null,
    login_failure_count int not null,
    login_ip varchar(255) null,
    name varchar(255) null,
    password varchar(255) not null,
    username varchar(255) not null unique,
    primary key (id)
);

create table [{shopxx_table_prefix}admin_role] (
    admin_set_id varchar(32) not null,
    role_set_id varchar(32) not null,
    primary key (admin_set_id, role_set_id)
);

create table [{shopxx_table_prefix}area] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    display_name varchar(3000) not null,
    grade int not null,
    name varchar(255) not null,
    order_list int null,
    path varchar(3000) not null,
    parent_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}article] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    author varchar(255) null,
    content text not null,
    hits int not null,
    html_path varchar(255) not null,
    is_publication tinyint not null,
    is_recommend tinyint not null,
    is_top tinyint not null,
    meta_description varchar(3000) null,
    meta_keywords varchar(3000) null,
    page_count int not null,
    title varchar(255) not null,
    article_category_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}article_category] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    grade int not null,
    meta_description varchar(3000) null,
    meta_keywords varchar(3000) null,
    name varchar(255) not null,
    order_list int null,
    path varchar(3000) not null,
    sign varchar(255) not null unique,
    parent_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}brand] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    introduction varchar(3000) null,
    logo_path varchar(255) null,
    name varchar(255) not null,
    order_list int null,
    url varchar(255) null,
    primary key (id)
);

create table [{shopxx_table_prefix}cart_item] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    quantity int not null,
    member_id varchar(32) not null,
    product_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}comment] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    contact varchar(255) null,
    content text not null,
    ip varchar(255) not null,
    is_admin_reply tinyint not null,
    is_show tinyint not null,
    username varchar(255) null,
    for_comment_id varchar(32) null,
    goods_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_center] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    address varchar(255) not null,
    area_store varchar(3000) not null,
    consignor varchar(255) not null,
    is_default tinyint not null,
    memo varchar(255) null,
    mobile varchar(255) null,
    name varchar(255) not null,
    phone varchar(255) null,
    zip_code varchar(255) null,
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_corp] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    name varchar(255) not null,
    order_list int null,
    url varchar(255) null,
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_item] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    delivery_quantity int not null,
    goods_html_path varchar(255) not null,
    product_name varchar(255) not null,
    product_sn varchar(255) not null,
    shipping_id varchar(32) null,
    product_id varchar(32) null,
    reship_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_template] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    content varchar(3000) not null,
    is_default tinyint not null,
    memo varchar(255) null,
    name varchar(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_type] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    continue_weight int not null,
    continue_weight_price numeric(15,5) not null,
    delivery_method int not null,
    description varchar(3000) null,
    first_weight int not null,
    first_weight_price numeric(15,5) not null,
    name varchar(255) not null,
    order_list int null,
    default_delivery_corp_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}deposit] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    balance numeric(19,2) not null,
    credit numeric(19,2) not null,
    debit numeric(19,2) not null,
    deposit_type int not null,
    member_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}friend_link] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    logo_path varchar(255) null,
    name varchar(255) not null,
    order_list int null,
    url varchar(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    cost numeric(15,5) null,
    freeze_store int not null,
    goods_attribute_value0 varchar(255) null,
    goods_attribute_value1 varchar(255) null,
    goods_attribute_value10 varchar(255) null,
    goods_attribute_value11 varchar(255) null,
    goods_attribute_value12 varchar(255) null,
    goods_attribute_value13 varchar(255) null,
    goods_attribute_value14 varchar(255) null,
    goods_attribute_value15 varchar(255) null,
    goods_attribute_value16 varchar(255) null,
    goods_attribute_value17 varchar(255) null,
    goods_attribute_value18 varchar(255) null,
    goods_attribute_value19 varchar(255) null,
    goods_attribute_value2 varchar(255) null,
    goods_attribute_value3 varchar(255) null,
    goods_attribute_value4 varchar(255) null,
    goods_attribute_value5 varchar(255) null,
    goods_attribute_value6 varchar(255) null,
    goods_attribute_value7 varchar(255) null,
    goods_attribute_value8 varchar(255) null,
    goods_attribute_value9 varchar(255) null,
    goods_image_store varchar(3000) null,
    goods_parameter_value_store varchar(3000) null,
    goods_sn varchar(255) not null unique,
    html_path varchar(255) not null,
    introduction text null,
    is_best tinyint not null,
    is_hot tinyint not null,
    is_marketable tinyint not null,
    is_new tinyint not null,
    is_specification_enabled tinyint not null,
    market_price numeric(15,5) not null,
    meta_description varchar(3000) null,
    meta_keywords varchar(3000) null,
    name varchar(255) not null,
    price numeric(15,5) not null,
    score int not null,
    store int null,
    store_place varchar(255) null,
    weight int not null,
    brand_id varchar(32) null,
    goods_category_id varchar(32) not null,
    goods_type_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_attribute] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    attribute_type int not null,
    name varchar(255) not null,
    option_store varchar(255) null,
    order_list int null,
    property_index int not null,
    goods_type_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_category] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    grade int not null,
    meta_description varchar(3000) null,
    meta_keywords varchar(3000) null,
    name varchar(255) not null,
    order_list int null,
    path varchar(3000) not null,
    sign varchar(255) not null unique,
    parent_id varchar(32) null,
    goods_type_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_notify] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    email varchar(255) not null,
    is_sent tinyint not null,
    send_date datetime null,
    product_id varchar(32) not null,
    member_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_specification] (
    goods_set_id varchar(32) not null,
    specification_set_id varchar(32) not null,
    primary key (goods_set_id, specification_set_id)
);

create table [{shopxx_table_prefix}goods_type] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    goods_parameter_store varchar(3000) null,
    name varchar(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_type_brand] (
    goods_type_set_id varchar(32) not null,
    brand_set_id varchar(32) not null,
    primary key (goods_type_set_id, brand_set_id)
);

create table [{shopxx_table_prefix}instant_messaging] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    instant_messaging_type int not null,
    order_list int null,
    title varchar(255) not null,
    value varchar(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}leave_message] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    contact varchar(255) null,
    content text not null,
    ip varchar(255) not null,
    title varchar(255) not null,
    username varchar(255) null,
    for_leave_message_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}log] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    action_class varchar(255) not null,
    action_method varchar(255) not null,
    info varchar(3000) null,
    ip varchar(255) not null,
    operation varchar(255) not null,
    operator varchar(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}member] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    address varchar(255) null,
    area_store varchar(255) null,
    birth datetime null,
    deposit numeric(15,5) not null,
    email varchar(255) not null,
    gender int null,
    is_account_enabled tinyint not null,
    is_account_locked tinyint not null,
    locked_date datetime null,
    login_date datetime null,
    login_failure_count int not null,
    login_ip varchar(255) null,
    member_attribute_value0 varchar(255) null,
    member_attribute_value1 varchar(255) null,
    member_attribute_value10 varchar(255) null,
    member_attribute_value11 varchar(255) null,
    member_attribute_value12 varchar(255) null,
    member_attribute_value13 varchar(255) null,
    member_attribute_value14 varchar(255) null,
    member_attribute_value15 varchar(255) null,
    member_attribute_value16 varchar(255) null,
    member_attribute_value17 varchar(255) null,
    member_attribute_value18 varchar(255) null,
    member_attribute_value19 varchar(255) null,
    member_attribute_value2 varchar(255) null,
    member_attribute_value3 varchar(255) null,
    member_attribute_value4 varchar(255) null,
    member_attribute_value5 varchar(255) null,
    member_attribute_value6 varchar(255) null,
    member_attribute_value7 varchar(255) null,
    member_attribute_value8 varchar(255) null,
    member_attribute_value9 varchar(255) null,
    mobile varchar(255) null,
    name varchar(255) null,
    password varchar(255) not null,
    password_recover_key varchar(255) null,
    phone varchar(255) null,
    register_ip varchar(255) not null,
    safe_answer varchar(255) null,
    safe_question varchar(255) null,
    score int not null,
    username varchar(255) not null unique,
    zip_code varchar(255) null,
    member_rank_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}member_attribute] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    attribute_type int null,
    is_enabled tinyint not null,
    is_required tinyint not null,
    name varchar(255) not null,
    option_store varchar(255) null,
    order_list int null,
    property_index int not null,
    system_attribute_type int null,
    primary key (id)
);

create table [{shopxx_table_prefix}member_goods] (
    favorite_member_set_id varchar(32) not null,
    favorite_goods_set_id varchar(32) not null,
    primary key (favorite_member_set_id, favorite_goods_set_id)
);

create table [{shopxx_table_prefix}member_rank] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    is_default tinyint not null,
    name varchar(255) not null unique,
    preferential_scale double precision not null,
    score int not null unique,
    primary key (id)
);

create table [{shopxx_table_prefix}message] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    content text not null,
    delete_status int not null,
    is_read tinyint not null,
    is_save_draftbox tinyint not null,
    title varchar(255) not null,
    to_member_id varchar(32) null,
    from_member_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}navigation] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    is_blank_target tinyint not null,
    is_visible tinyint not null,
    name varchar(255) not null,
    navigation_position int not null,
    order_list int null,
    url varchar(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}order] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    delivery_fee numeric(15,5) not null,
    delivery_type_name varchar(255) not null,
    goods_id_list_store varchar(3000) null,
    memo varchar(3000) null,
    order_sn varchar(255) not null unique,
    order_status int not null,
    paid_amount numeric(15,5) not null,
    payment_config_name varchar(255) not null,
    payment_fee numeric(15,5) not null,
    payment_status int not null,
    ship_address varchar(255) not null,
    ship_area_store varchar(3000) not null,
    ship_mobile varchar(255) null,
    ship_name varchar(255) not null,
    ship_phone varchar(255) null,
    ship_zip_code varchar(255) not null,
    shipping_status int not null,
    total_amount numeric(15,5) not null,
    total_product_price numeric(15,5) not null,
    total_product_quantity int not null,
    total_product_weight int not null,
    member_id varchar(32) null,
    payment_config_id varchar(32) null,
    delivery_type_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}order_item] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    delivery_quantity int not null,
    goods_html_path varchar(255) not null,
    product_name varchar(255) not null,
    product_price numeric(15,5) not null,
    product_quantity int not null,
    product_sn varchar(255) not null,
    order_id varchar(32) not null,
    product_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}order_log] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    info varchar(3000) null,
    operator varchar(255) null,
    order_log_type int not null,
    order_sn varchar(255) not null,
    order_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}payment] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    bank_account varchar(255) null,
    bank_name varchar(255) null,
    memo varchar(3000) null,
    operator varchar(255) null,
    payer varchar(255) null,
    payment_config_name varchar(255) not null,
    payment_fee numeric(15,5) not null,
    payment_sn varchar(255) not null unique,
    payment_status int not null,
    payment_type int not null,
    total_amount numeric(15,5) not null,
    payment_config_id varchar(32) null,
    order_id varchar(32) null,
    member_id varchar(32) null,
    deposit_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}payment_config] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    bargainor_id varchar(255) null,
    bargainor_key varchar(255) null,
    description varchar(3000) null,
    name varchar(255) not null,
    order_list int null,
    payment_config_type int not null,
    payment_fee numeric(15,5) not null,
    payment_fee_type int not null,
    payment_product_id varchar(255) null,
    primary key (id)
);

create table [{shopxx_table_prefix}product] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    cost numeric(15,5) null,
    freeze_store int not null,
    is_default tinyint not null,
    is_marketable tinyint not null,
    market_price numeric(15,5) not null,
    name varchar(255) not null,
    price numeric(15,5) not null,
    product_sn varchar(255) not null unique,
    specification_value_store varchar(3000) null,
    store int null,
    store_place varchar(255) null,
    weight int not null,
    goods_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}receiver] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    address varchar(3000) not null,
    area_store varchar(3000) not null,
    is_default tinyint not null,
    mobile varchar(255) null,
    name varchar(255) not null,
    phone varchar(255) null,
    zip_code varchar(255) not null,
    member_id varchar(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}refund] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    bank_account varchar(255) null,
    bank_name varchar(255) null,
    memo varchar(3000) null,
    operator varchar(255) null,
    payee varchar(255) null,
    payment_config_name varchar(255) not null,
    refund_sn varchar(255) not null unique,
    refund_type int not null,
    total_amount numeric(15,5) not null,
    deposit_id varchar(32) null,
    order_id varchar(32) null,
    payment_config_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}reship] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    delivery_corp_name varchar(255) not null,
    delivery_fee numeric(19,2) not null,
    delivery_sn varchar(255) null,
    delivery_type_name varchar(255) not null,
    memo varchar(255) null,
    reship_address varchar(255) not null,
    reship_area_store varchar(3000) not null,
    reship_mobile varchar(255) null,
    reship_name varchar(255) not null,
    reship_phone varchar(255) null,
    reship_sn varchar(255) not null unique,
    reship_zip_code varchar(255) not null,
    order_id varchar(32) null,
    delivery_type_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}role] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    authority_list_store varchar(3000) null,
    description varchar(3000) null,
    is_system tinyint not null,
    name varchar(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}shipping] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    delivery_corp_name varchar(255) not null,
    delivery_corp_url varchar(255) null,
    delivery_fee numeric(19,2) not null,
    delivery_sn varchar(255) null,
    delivery_type_name varchar(255) not null,
    memo varchar(255) null,
    ship_address varchar(255) not null,
    ship_area_store varchar(3000) not null,
    ship_mobile varchar(255) null,
    ship_name varchar(255) not null,
    ship_phone varchar(255) null,
    ship_zip_code varchar(255) not null,
    shipping_sn varchar(255) not null unique,
    order_id varchar(32) null,
    delivery_type_id varchar(32) null,
    primary key (id)
);

create table [{shopxx_table_prefix}specification] (
    id varchar(32) not null,
    create_date datetime null,
    modify_date datetime null,
    memo varchar(255) null,
    name varchar(255) not null,
    order_list int null,
    specification_type int not null,
    specification_value_store varchar(3000) not null,
    primary key (id)
);

create index [{shopxx_table_prefix}area_grade] on [{shopxx_table_prefix}area](grade);
create index [{shopxx_table_prefix}area_order_list] on [{shopxx_table_prefix}area](order_list);
create index [{shopxx_table_prefix}article_create_date] on [{shopxx_table_prefix}article](create_date);
create index [{shopxx_table_prefix}article_hits] on [{shopxx_table_prefix}article](hits);
create index [{shopxx_table_prefix}article_category_grade] on [{shopxx_table_prefix}article_category](grade);
create index [{shopxx_table_prefix}article_category_order_list] on [{shopxx_table_prefix}article_category](order_list);
create index [{shopxx_table_prefix}brand_order_list] on [{shopxx_table_prefix}brand](order_list);
create index [{shopxx_table_prefix}comment_create_date] on [{shopxx_table_prefix}comment](create_date);
create index [{shopxx_table_prefix}delivery_corp_order_list] on [{shopxx_table_prefix}delivery_corp](order_list);
create index [{shopxx_table_prefix}delivery_type_order_list] on [{shopxx_table_prefix}delivery_type](order_list);
create index [{shopxx_table_prefix}deposit_create_date] on [{shopxx_table_prefix}deposit](create_date);
create index [{shopxx_table_prefix}friend_link_order_list] on [{shopxx_table_prefix}friend_link](order_list);
create index [{shopxx_table_prefix}goods_create_date] on [{shopxx_table_prefix}goods](create_date);
create index [{shopxx_table_prefix}goods_name] on [{shopxx_table_prefix}goods](name);
create index [{shopxx_table_prefix}goods_price] on [{shopxx_table_prefix}goods](price);
create index [{shopxx_table_prefix}goods_attribute_order_list] on [{shopxx_table_prefix}goods_attribute](order_list);
create index [{shopxx_table_prefix}goods_category_grade] on [{shopxx_table_prefix}goods_category](grade);
create index [{shopxx_table_prefix}goods_category_order_list] on [{shopxx_table_prefix}goods_category](order_list);
create index [{shopxx_table_prefix}goods_notify_create_date] on [{shopxx_table_prefix}goods_notify](create_date);
create index [{shopxx_table_prefix}instant_messaging_order_list] on [{shopxx_table_prefix}instant_messaging](order_list);
create index [{shopxx_table_prefix}leave_message_create_date] on [{shopxx_table_prefix}leave_message](create_date);
create index [{shopxx_table_prefix}log_create_date] on [{shopxx_table_prefix}log](create_date);
create index [{shopxx_table_prefix}member_attribute_order_list] on [{shopxx_table_prefix}member_attribute](order_list);
create index [{shopxx_table_prefix}message_create_date] on [{shopxx_table_prefix}message](create_date);
create index [{shopxx_table_prefix}navigation_order_list] on [{shopxx_table_prefix}navigation](order_list);
create index [{shopxx_table_prefix}order_create_date] on [{shopxx_table_prefix}order](create_date);
create index [{shopxx_table_prefix}order_log_create_date] on [{shopxx_table_prefix}order_log](create_date);
create index [{shopxx_table_prefix}payment_create_date] on [{shopxx_table_prefix}payment](create_date);
create index [{shopxx_table_prefix}payment_config_order_list] on [{shopxx_table_prefix}payment_config](order_list);
create index [{shopxx_table_prefix}refund_create_date] on [{shopxx_table_prefix}refund](create_date);
create index [{shopxx_table_prefix}reship_create_date] on [{shopxx_table_prefix}reship](create_date);
create index [{shopxx_table_prefix}shipping_create_date] on [{shopxx_table_prefix}shipping](create_date);
create index [{shopxx_table_prefix}specification_order_list] on [{shopxx_table_prefix}specification](order_list);

alter table [{shopxx_table_prefix}admin_role]
    add constraint [{shopxx_table_prefix}role_admin_set]
    foreign key (role_set_id)
    references [{shopxx_table_prefix}role];

alter table [{shopxx_table_prefix}admin_role]
    add constraint [{shopxx_table_prefix}admin_role_set]
    foreign key (admin_set_id)
    references [{shopxx_table_prefix}admin];

alter table [{shopxx_table_prefix}area]
    add constraint [{shopxx_table_prefix}area_parent]
    foreign key (parent_id)
    references [{shopxx_table_prefix}area];

alter table [{shopxx_table_prefix}article]
    add constraint [{shopxx_table_prefix}article_article_category]
    foreign key (article_category_id)
    references [{shopxx_table_prefix}article_category];

alter table [{shopxx_table_prefix}article_category]
    add constraint [{shopxx_table_prefix}article_category_parent]
    foreign key (parent_id)
    references [{shopxx_table_prefix}article_category];

alter table [{shopxx_table_prefix}cart_item]
    add constraint [{shopxx_table_prefix}cart_item_member]
    foreign key (member_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}cart_item]
    add constraint [{shopxx_table_prefix}cart_item_product]
    foreign key (product_id)
    references [{shopxx_table_prefix}product];

alter table [{shopxx_table_prefix}comment]
    add constraint [{shopxx_table_prefix}comment_goods]
    foreign key (goods_id)
    references [{shopxx_table_prefix}goods];

alter table [{shopxx_table_prefix}comment]
    add constraint [{shopxx_table_prefix}comment_for_comment]
    foreign key (for_comment_id)
    references [{shopxx_table_prefix}comment];

alter table [{shopxx_table_prefix}delivery_item]
    add constraint [{shopxx_table_prefix}delivery_item_shipping]
    foreign key (shipping_id)
    references [{shopxx_table_prefix}shipping];

alter table [{shopxx_table_prefix}delivery_item]
    add constraint [{shopxx_table_prefix}delivery_item_reship]
    foreign key (reship_id)
    references [{shopxx_table_prefix}reship];

alter table [{shopxx_table_prefix}delivery_item]
    add constraint [{shopxx_table_prefix}delivery_item_product]
    foreign key (product_id)
    references [{shopxx_table_prefix}product];

alter table [{shopxx_table_prefix}delivery_type]
    add constraint [{shopxx_table_prefix}delivery_item_delivery_corp]
    foreign key (default_delivery_corp_id)
    references [{shopxx_table_prefix}delivery_corp];

alter table [{shopxx_table_prefix}deposit]
    add constraint [{shopxx_table_prefix}deposit_member]
    foreign key (member_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}goods]
    add constraint [{shopxx_table_prefix}goods_goods_category]
    foreign key (goods_category_id)
    references [{shopxx_table_prefix}goods_category];

alter table [{shopxx_table_prefix}goods]
    add constraint [{shopxx_table_prefix}goods_goods_type]
    foreign key (goods_type_id)
    references [{shopxx_table_prefix}goods_type];

alter table [{shopxx_table_prefix}goods]
    add constraint [{shopxx_table_prefix}goods_brand]
    foreign key (brand_id)
    references [{shopxx_table_prefix}brand];

alter table [{shopxx_table_prefix}goods_attribute]
    add constraint [{shopxx_table_prefix}goods_attribute_goods_type]
    foreign key (goods_type_id)
    references [{shopxx_table_prefix}goods_type];

alter table [{shopxx_table_prefix}goods_category]
    add constraint [{shopxx_table_prefix}goods_category_goods_type]
    foreign key (goods_type_id)
    references [{shopxx_table_prefix}goods_type];

alter table [{shopxx_table_prefix}goods_category]
    add constraint [{shopxx_table_prefix}goods_category_parent]
    foreign key (parent_id)
    references [{shopxx_table_prefix}goods_category];

alter table [{shopxx_table_prefix}goods_notify]
    add constraint [{shopxx_table_prefix}goods_notify_member]
    foreign key (member_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}goods_notify]
    add constraint [{shopxx_table_prefix}goods_notify_product]
    foreign key (product_id)
    references [{shopxx_table_prefix}product];

alter table [{shopxx_table_prefix}goods_specification]
    add constraint [{shopxx_table_prefix}specification_set]
    foreign key (goods_set_id)
    references [{shopxx_table_prefix}goods];

alter table [{shopxx_table_prefix}goods_specification]
    add constraint [{shopxx_table_prefix}specification_goods_set]
    foreign key (specification_set_id)
    references [{shopxx_table_prefix}specification];

alter table [{shopxx_table_prefix}goods_type_brand]
    add constraint [{shopxx_table_prefix}goods_type_brand_set]
    foreign key (goods_type_set_id)
    references [{shopxx_table_prefix}goods_type];

alter table [{shopxx_table_prefix}goods_type_brand]
    add constraint [{shopxx_table_prefix}brand_goods_type_set]
    foreign key (brand_set_id)
    references [{shopxx_table_prefix}brand];

alter table [{shopxx_table_prefix}leave_message]
    add constraint [{shopxx_table_prefix}leave_message_for_message]
    foreign key (for_leave_message_id)
    references [{shopxx_table_prefix}leave_message];

alter table [{shopxx_table_prefix}member]
    add constraint [{shopxx_table_prefix}member_member_rank]
    foreign key (member_rank_id)
    references [{shopxx_table_prefix}member_rank];

alter table [{shopxx_table_prefix}member_goods]
    add constraint [{shopxx_table_prefix}member_favorite_goods_set]
    foreign key (favorite_member_set_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}member_goods]
    add constraint [{shopxx_table_prefix}goods_favorite_member_set]
    foreign key (favorite_goods_set_id)
    references [{shopxx_table_prefix}goods];

alter table [{shopxx_table_prefix}message]
    add constraint [{shopxx_table_prefix}message_from_member]
    foreign key (from_member_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}message]
    add constraint [{shopxx_table_prefix}message_to_member]
    foreign key (to_member_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}order]
    add constraint [{shopxx_table_prefix}order_delivery_type]
    foreign key (delivery_type_id)
    references [{shopxx_table_prefix}delivery_type];

alter table [{shopxx_table_prefix}order]
    add constraint [{shopxx_table_prefix}order_member]
    foreign key (member_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}order]
    add constraint [{shopxx_table_prefix}order_payment_config]
    foreign key (payment_config_id)
    references [{shopxx_table_prefix}payment_config];

alter table [{shopxx_table_prefix}order_item]
    add constraint [{shopxx_table_prefix}order_item_order]
    foreign key (order_id)
    references [{shopxx_table_prefix}order];

alter table [{shopxx_table_prefix}order_item]
    add constraint [{shopxx_table_prefix}order_item_product]
    foreign key (product_id)
    references [{shopxx_table_prefix}product];

alter table [{shopxx_table_prefix}order_log]
    add constraint [{shopxx_table_prefix}order_log_order]
    foreign key (order_id)
    references [{shopxx_table_prefix}order];

alter table [{shopxx_table_prefix}payment]
    add constraint [{shopxx_table_prefix}payment_member]
    foreign key (member_id) 
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}payment]
    add constraint [{shopxx_table_prefix}payment_deposit]
    foreign key (deposit_id)
    references [{shopxx_table_prefix}deposit];

alter table [{shopxx_table_prefix}payment]
    add constraint [{shopxx_table_prefix}payment_payment_config]
    foreign key (payment_config_id)
    references [{shopxx_table_prefix}payment_config];

alter table [{shopxx_table_prefix}payment]
    add constraint [{shopxx_table_prefix}payment_order]
    foreign key (order_id)
    references [{shopxx_table_prefix}order];

alter table [{shopxx_table_prefix}product]
    add constraint [{shopxx_table_prefix}product_goods]
    foreign key (goods_id)
    references [{shopxx_table_prefix}goods];

alter table [{shopxx_table_prefix}receiver]
    add constraint [{shopxx_table_prefix}receiver_member]
    foreign key (member_id)
    references [{shopxx_table_prefix}member];

alter table [{shopxx_table_prefix}refund]
    add constraint [{shopxx_table_prefix}refund_deposit]
    foreign key (deposit_id)
    references [{shopxx_table_prefix}deposit];

alter table [{shopxx_table_prefix}refund]
    add constraint [{shopxx_table_prefix}refund_payment_config]
    foreign key (payment_config_id)
    references [{shopxx_table_prefix}payment_config];

alter table [{shopxx_table_prefix}refund]
    add constraint [{shopxx_table_prefix}refund_order]
    foreign key (order_id)
    references [{shopxx_table_prefix}order];

alter table [{shopxx_table_prefix}reship]
    add constraint [{shopxx_table_prefix}reship_delivery_type]
    foreign key (delivery_type_id)
    references [{shopxx_table_prefix}delivery_type];

alter table [{shopxx_table_prefix}reship]
    add constraint [{shopxx_table_prefix}reship_order]
    foreign key (order_id)
    references [{shopxx_table_prefix}order];

alter table [{shopxx_table_prefix}shipping]
    add constraint [{shopxx_table_prefix}shipping_delivery_type]
    foreign key (delivery_type_id)
    references [{shopxx_table_prefix}delivery_type];

alter table [{shopxx_table_prefix}shipping]
    add constraint [{shopxx_table_prefix}shipping_order]
    foreign key (order_id)
    references [{shopxx_table_prefix}order];


-- 初始化数据库基本数据 --

INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000001', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '姓名', NULL, 1, 0, 0);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000002', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '性别', NULL, 2, 1, 1);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000003', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '出生日期', NULL, 3, 2, 2);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000004', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '地区', NULL, 4, 3, 3);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000005', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '地址', NULL, 5, 4, 4);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000006', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '邮编', NULL, 6, 5, 5);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000007', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '电话', NULL, 7, 6, 6);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000008', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, 1, 0, '手机', NULL, 8, 7, 7);

INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('0731dcsoft2010031200000000000010', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 1, '普通会员', 100, 0);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d08bed0001', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, '一级会员', 98, 2000);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d0bf800002', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, '二级会员', 95, 5000);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d103ec0003', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, '三级会员', 90, 10000);

INSERT INTO [{shopxx_table_prefix}role] (id, create_date, modify_date, authority_list_store, description, is_system, name) VALUES ('0731dcsoft2010031200000000000016', '2011-01-01 00:00:00', '2011-01-01 00:00:00', '["ROLE_GOODS","ROLE_GOODS_NOTIFY","ROLE_GOODS_CATEGORY","ROLE_GOODS_TYPE","ROLE_SPECIFICATION","ROLE_BRAND","ROLE_ORDER","ROLE_PAYMENT","ROLE_REFUND","ROLE_SHIPPING","ROLE_RESHIP","ROLE_MEMBER","ROLE_MEMBER_RANK","ROLE_MEMBER_ATTRIBUTE","ROLE_COMMENT","ROLE_LEAVE_MESSAGE","ROLE_NAVIGATION","ROLE_ARTICLEE","ROLE_ARTICLE_CATEGORY","ROLE_FRIEND_LINK","ROLE_PAGE_TEMPLATE","ROLE_MAIL_TEMPLATE","ROLE_PRINT_TEMPLATE","ROLE_CACHE","ROLE_BUILD_HTML","ROLE_ADMIN","ROLE_ROLE","ROLE_MESSAGE","ROLE_LOG","ROLE_SETTING","ROLE_INSTANT_MESSAGING","ROLE_PAYMENT_CONFIG","ROLE_DELIVERY_TYPE","ROLE_AREA","ROLE_DELIVERY_CORP","ROLE_DELIVERY_CENTER","ROLE_DELIVERY_TEMPLATE","ROLE_BASE"]', '拥有后台管理最高权限', 1, '超级管理员');

INSERT INTO [{shopxx_table_prefix}admin] (id, create_date, modify_date, department, email, is_account_enabled, is_account_expired, is_account_locked, is_credentials_expired, locked_date, login_date, login_failure_count, login_ip, name, password, username) VALUES ('0731dcsoft2010031200000000000017', '2011-01-01 00:00:00', '2011-01-01 00:00:00', '技术部', 'admin@shopxx.net', 1, 0, 0, 0, NULL, '2011-01-01 00:00:00', 0, '127.0.0.1', 'ADMIN', '{shopxx_admin_password}', '{shopxx_admin_username}');

INSERT INTO [{shopxx_table_prefix}admin_role] (admin_set_id, role_set_id) VALUES ('0731dcsoft2010031200000000000017', '0731dcsoft2010031200000000000016');