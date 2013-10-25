-- SHOP++ INIT SQL
-- http://www.shopxx.net
-- Copyright (c) 2011 shopxx.net All Rights Reserved.
-- Revision: 2.0.5
-- Date: 2011-05

-- 初始化数据库表结构 --

create table [{shopxx_table_prefix}admin] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    department varchar2(255),
    email varchar2(255) not null,
    is_account_enabled number(1,0) not null,
    is_account_expired number(1,0) not null,
    is_account_locked number(1,0) not null,
    is_credentials_expired number(1,0) not null,
    locked_date date,
    login_date date,
    login_failure_count number(10,0) not null,
    login_ip varchar2(255),
    name varchar2(255),
    password varchar2(255) not null,
    username varchar2(255) not null unique,
    primary key (id)
);

create table [{shopxx_table_prefix}admin_role] (
    admin_set_id varchar2(32) not null,
    role_set_id varchar2(32) not null,
    primary key (admin_set_id, role_set_id)
);

create table [{shopxx_table_prefix}area] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    display_name varchar2(3000) not null,
    grade number(10,0) not null,
    name varchar2(255) not null,
    order_list number(10,0),
    path varchar2(3000) not null,
    parent_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}article] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    author varchar2(255),
    content clob not null,
    hits number(10,0) not null,
    html_path varchar2(255) not null,
    is_publication number(1,0) not null,
    is_recommend number(1,0) not null,
    is_top number(1,0) not null,
    meta_description varchar2(3000),
    meta_keywords varchar2(3000),
    page_count number(10,0) not null,
    title varchar2(255) not null,
    article_category_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}article_category] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    grade number(10,0) not null,
    meta_description varchar2(3000),
    meta_keywords varchar2(3000),
    name varchar2(255) not null,
    order_list number(10,0),
    path varchar2(3000) not null,
    sign varchar2(255) not null unique,
    parent_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}brand] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    introduction varchar2(3000),
    logo_path varchar2(255),
    name varchar2(255) not null,
    order_list number(10,0),
    url varchar2(255),
    primary key (id)
);

create table [{shopxx_table_prefix}cart_item] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    quantity number(10,0) not null,
    member_id varchar2(32) not null,
    product_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}comment] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    contact varchar2(255),
    content clob not null,
    ip varchar2(255) not null,
    is_admin_reply number(1,0) not null,
    is_show number(1,0) not null,
    username varchar2(255),
    for_comment_id varchar2(32),
    goods_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_center] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    address varchar2(255) not null,
    area_store varchar2(3000) not null,
    consignor varchar2(255) not null,
    is_default number(1,0) not null,
    memo varchar2(255),
    mobile varchar2(255),
    name varchar2(255) not null,
    phone varchar2(255),
    zip_code varchar2(255),
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_corp] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    name varchar2(255) not null,
    order_list number(10,0),
    url varchar2(255),
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_item] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    delivery_quantity number(10,0) not null,
    goods_html_path varchar2(255) not null,
    product_name varchar2(255) not null,
    product_sn varchar2(255) not null,
    reship_id varchar2(32),
    shipping_id varchar2(32),
    product_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_template] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    content varchar2(3000) not null,
    is_default number(1,0) not null,
    memo varchar2(255),
    name varchar2(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}delivery_type] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    continue_weight number(10,0) not null,
    continue_weight_price number(15,5) not null,
    delivery_method number(10,0) not null,
    description varchar2(3000),
    first_weight number(10,0) not null,
    first_weight_price number(15,5) not null,
    name varchar2(255) not null,
    order_list number(10,0),
    default_delivery_corp_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}deposit] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    balance number(19,2) not null,
    credit number(19,2) not null,
    debit number(19,2) not null,
    deposit_type number(10,0) not null,
    member_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}friend_link] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    logo_path varchar2(255),
    name varchar2(255) not null,
    order_list number(10,0),
    url varchar2(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    cost number(15,5),
    freeze_store number(10,0) not null,
    goods_attribute_value0 varchar2(255),
    goods_attribute_value1 varchar2(255),
    goods_attribute_value10 varchar2(255),
    goods_attribute_value11 varchar2(255),
    goods_attribute_value12 varchar2(255),
    goods_attribute_value13 varchar2(255),
    goods_attribute_value14 varchar2(255),
    goods_attribute_value15 varchar2(255),
    goods_attribute_value16 varchar2(255),
    goods_attribute_value17 varchar2(255),
    goods_attribute_value18 varchar2(255),
    goods_attribute_value19 varchar2(255),
    goods_attribute_value2 varchar2(255),
    goods_attribute_value3 varchar2(255),
    goods_attribute_value4 varchar2(255),
    goods_attribute_value5 varchar2(255),
    goods_attribute_value6 varchar2(255),
    goods_attribute_value7 varchar2(255),
    goods_attribute_value8 varchar2(255),
    goods_attribute_value9 varchar2(255),
    goods_image_store varchar2(3000),
    goods_parameter_value_store varchar2(3000),
    goods_sn varchar2(255) not null unique,
    html_path varchar2(255) not null,
    introduction clob,
    is_best number(1,0) not null,
    is_hot number(1,0) not null,
    is_marketable number(1,0) not null,
    is_new number(1,0) not null,
    is_specification_enabled number(1,0) not null,
    market_price number(15,5) not null,
    meta_description varchar2(3000),
    meta_keywords varchar2(3000),
    name varchar2(255) not null,
    price number(15,5) not null,
    score number(10,0) not null,
    store number(10,0),
    store_place varchar2(255),
    weight number(10,0) not null,
    goods_type_id varchar2(32),
    brand_id varchar2(32),
    goods_category_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_attribute] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    attribute_type number(10,0) not null,
    name varchar2(255) not null,
    option_store varchar2(255),
    order_list number(10,0),
    property_index number(10,0) not null,
    goods_type_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_category] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    grade number(10,0) not null,
    meta_description varchar2(3000),
    meta_keywords varchar2(3000),
    name varchar2(255) not null,
    order_list number(10,0),
    path varchar2(3000) not null,
    sign varchar2(255) not null unique,
    goods_type_id varchar2(32),
    parent_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}goods_notify] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    email varchar2(255) not null,
    is_sent number(1,0) not null,
    send_date date,
    product_id varchar2(32) not null,
    member_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}goods_specification] (
    goods_set_id varchar2(32) not null,
    specification_set_id varchar2(32) not null,
    primary key (goods_set_id, specification_set_id)
);

create table [{shopxx_table_prefix}goods_type] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    goods_parameter_store varchar2(3000),
    name varchar2(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}goods_type_brand] (
    goods_type_set_id varchar2(32) not null,
    brand_set_id varchar2(32) not null,
    primary key (goods_type_set_id, brand_set_id)
);

create table [{shopxx_table_prefix}instant_messaging] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    instant_messaging_type number(10,0) not null,
    order_list number(10,0),
    title varchar2(255) not null,
    value varchar2(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}leave_message] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    contact varchar2(255),
    content clob not null,
    ip varchar2(255) not null,
    title varchar2(255) not null,
    username varchar2(255),
    for_leave_message_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}log] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    action_class varchar2(255) not null,
    action_method varchar2(255) not null,
    info varchar2(3000),
    ip varchar2(255) not null,
    operation varchar2(255) not null,
    operator varchar2(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}member] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    address varchar2(255),
    area_store varchar2(255),
    birth date,
    deposit number(15,5) not null,
    email varchar2(255) not null,
    gender number(10,0),
    is_account_enabled number(1,0) not null,
    is_account_locked number(1,0) not null,
    locked_date date,
    login_date date,
    login_failure_count number(10,0) not null,
    login_ip varchar2(255),
    member_attribute_value0 varchar2(255),
    member_attribute_value1 varchar2(255),
    member_attribute_value10 varchar2(255),
    member_attribute_value11 varchar2(255),
    member_attribute_value12 varchar2(255),
    member_attribute_value13 varchar2(255),
    member_attribute_value14 varchar2(255),
    member_attribute_value15 varchar2(255),
    member_attribute_value16 varchar2(255),
    member_attribute_value17 varchar2(255),
    member_attribute_value18 varchar2(255),
    member_attribute_value19 varchar2(255),
    member_attribute_value2 varchar2(255),
    member_attribute_value3 varchar2(255),
    member_attribute_value4 varchar2(255),
    member_attribute_value5 varchar2(255),
    member_attribute_value6 varchar2(255),
    member_attribute_value7 varchar2(255),
    member_attribute_value8 varchar2(255),
    member_attribute_value9 varchar2(255),
    mobile varchar2(255),
    name varchar2(255),
    password varchar2(255) not null,
    password_recover_key varchar2(255),
    phone varchar2(255),
    register_ip varchar2(255) not null,
    safe_answer varchar2(255),
    safe_question varchar2(255),
    score number(10,0) not null,
    username varchar2(255) not null unique,
    zip_code varchar2(255),
    member_rank_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}member_attribute] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    attribute_type number(10,0),
    is_enabled number(1,0) not null,
    is_required number(1,0) not null,
    name varchar2(255) not null,
    option_store varchar2(255),
    order_list number(10,0),
    property_index number(10,0) not null,
    system_attribute_type number(10,0),
    primary key (id)
);

create table [{shopxx_table_prefix}member_goods] (
    favorite_member_set_id varchar2(32) not null,
    favorite_goods_set_id varchar2(32) not null,
    primary key (favorite_member_set_id, favorite_goods_set_id)
);

create table [{shopxx_table_prefix}member_rank] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    is_default number(1,0) not null,
    name varchar2(255) not null unique,
    preferential_scale double precision not null,
    score number(10,0) not null unique,
    primary key (id)
);

create table [{shopxx_table_prefix}message] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    content clob not null,
    delete_status number(10,0) not null,
    is_read number(1,0) not null,
    is_save_draftbox number(1,0) not null,
    title varchar2(255) not null,
    to_member_id varchar2(32),
    from_member_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}navigation] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    is_blank_target number(1,0) not null,
    is_visible number(1,0) not null,
    name varchar2(255) not null,
    navigation_position number(10,0) not null,
    order_list number(10,0),
    url varchar2(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}order] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    delivery_fee number(15,5) not null,
    delivery_type_name varchar2(255) not null,
    goods_id_list_store varchar2(3000),
    memo varchar2(3000),
    order_sn varchar2(255) not null unique,
    order_status number(10,0) not null,
    paid_amount number(15,5) not null,
    payment_config_name varchar2(255) not null,
    payment_fee number(15,5) not null,
    payment_status number(10,0) not null,
    ship_address varchar2(255) not null,
    ship_area_store varchar2(3000) not null,
    ship_mobile varchar2(255),
    ship_name varchar2(255) not null,
    ship_phone varchar2(255),
    ship_zip_code varchar2(255) not null,
    shipping_status number(10,0) not null,
    total_amount number(15,5) not null,
    total_product_price number(15,5) not null,
    total_product_quantity number(10,0) not null,
    total_product_weight number(10,0) not null,
    delivery_type_id varchar2(32),
    member_id varchar2(32),
    payment_config_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}order_item] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    delivery_quantity number(10,0) not null,
    goods_html_path varchar2(255) not null,
    product_name varchar2(255) not null,
    product_price number(15,5) not null,
    product_quantity number(10,0) not null,
    product_sn varchar2(255) not null,
    product_id varchar2(32),
    order_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}order_log] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    info varchar2(3000),
    operator varchar2(255),
    order_log_type number(10,0) not null,
    order_sn varchar2(255) not null,
    order_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}payment] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    bank_account varchar2(255),
    bank_name varchar2(255),
    memo varchar2(3000),
    operator varchar2(255),
    payer varchar2(255),
    payment_config_name varchar2(255) not null,
    payment_fee number(15,5) not null,
    payment_sn varchar2(255) not null unique,
    payment_status number(10,0) not null,
    payment_type number(10,0) not null,
    total_amount number(15,5) not null,
    order_id varchar2(32),
    payment_config_id varchar2(32),
    deposit_id varchar2(32),
    member_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}payment_config] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    bargainor_id varchar2(255),
    bargainor_key varchar2(255),
    description varchar2(3000),
    name varchar2(255) not null,
    order_list number(10,0),
    payment_config_type number(10,0) not null,
    payment_fee number(15,5) not null,
    payment_fee_type number(10,0) not null,
    payment_product_id varchar2(255),
    primary key (id)
);

create table [{shopxx_table_prefix}product] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    cost number(15,5),
    freeze_store number(10,0) not null,
    is_default number(1,0) not null,
    is_marketable number(1,0) not null,
    market_price number(15,5) not null,
    name varchar2(255) not null,
    price number(15,5) not null,
    product_sn varchar2(255) not null unique,
    specification_value_store varchar2(3000),
    store number(10,0),
    store_place varchar2(255),
    weight number(10,0) not null,
    goods_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}receiver] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    address varchar2(3000) not null,
    area_store varchar2(3000) not null,
    is_default number(1,0) not null,
    mobile varchar2(255),
    name varchar2(255) not null,
    phone varchar2(255),
    zip_code varchar2(255) not null,
    member_id varchar2(32) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}refund] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    bank_account varchar2(255),
    bank_name varchar2(255),
    memo varchar2(3000),
    operator varchar2(255),
    payee varchar2(255),
    payment_config_name varchar2(255) not null,
    refund_sn varchar2(255) not null unique,
    refund_type number(10,0) not null,
    total_amount number(15,5) not null,
    payment_config_id varchar2(32),
    deposit_id varchar2(32),
    order_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}reship] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    delivery_corp_name varchar2(255) not null,
    delivery_fee number(19,2) not null,
    delivery_sn varchar2(255),
    delivery_type_name varchar2(255) not null,
    memo varchar2(255),
    reship_address varchar2(255) not null,
    reship_area_store varchar2(3000) not null,
    reship_mobile varchar2(255),
    reship_name varchar2(255) not null,
    reship_phone varchar2(255),
    reship_sn varchar2(255) not null unique,
    reship_zip_code varchar2(255) not null,
    delivery_type_id varchar2(32),
    order_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}role] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    authority_list_store varchar2(3000),
    description varchar2(3000),
    is_system number(1,0) not null,
    name varchar2(255) not null,
    primary key (id)
);

create table [{shopxx_table_prefix}shipping] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    delivery_corp_name varchar2(255) not null,
    delivery_corp_url varchar2(255),
    delivery_fee number(19,2) not null,
    delivery_sn varchar2(255),
    delivery_type_name varchar2(255) not null,
    memo varchar2(255),
    ship_address varchar2(255) not null,
    ship_area_store varchar2(3000) not null,
    ship_mobile varchar2(255),
    ship_name varchar2(255) not null,
    ship_phone varchar2(255),
    ship_zip_code varchar2(255) not null,
    shipping_sn varchar2(255) not null unique,
    delivery_type_id varchar2(32),
    order_id varchar2(32),
    primary key (id)
);

create table [{shopxx_table_prefix}specification] (
    id varchar2(32) not null,
    create_date date,
    modify_date date,
    memo varchar2(255),
    name varchar2(255) not null,
    order_list number(10,0),
    specification_type number(10,0) not null,
    specification_value_store varchar2(3000) not null,
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

INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000001', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '姓名', NULL, 1, 0, 0);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000002', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '性别', NULL, 2, 1, 1);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000003', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '出生日期', NULL, 3, 2, 2);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000004', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '地区', NULL, 4, 3, 3);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000005', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '地址', NULL, 5, 4, 4);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000006', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '邮编', NULL, 6, 5, 5);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000007', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '电话', NULL, 7, 6, 6);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000008', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, 1, 0, '手机', NULL, 8, 7, 7);

INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('0731dcsoft2010031200000000000010', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 1, '普通会员', 100, 0);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d08bed0001', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, '一级会员', 98, 2000);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d0bf800002', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, '二级会员', 95, 5000);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d103ec0003', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, '三级会员', 90, 10000);

INSERT INTO [{shopxx_table_prefix}role] (id, create_date, modify_date, authority_list_store, description, is_system, name) VALUES ('0731dcsoft2010031200000000000016', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), '["ROLE_GOODS","ROLE_GOODS_NOTIFY","ROLE_GOODS_CATEGORY","ROLE_GOODS_TYPE","ROLE_SPECIFICATION","ROLE_BRAND","ROLE_ORDER","ROLE_PAYMENT","ROLE_REFUND","ROLE_SHIPPING","ROLE_RESHIP","ROLE_MEMBER","ROLE_MEMBER_RANK","ROLE_MEMBER_ATTRIBUTE","ROLE_COMMENT","ROLE_LEAVE_MESSAGE","ROLE_NAVIGATION","ROLE_ARTICLEE","ROLE_ARTICLE_CATEGORY","ROLE_FRIEND_LINK","ROLE_PAGE_TEMPLATE","ROLE_MAIL_TEMPLATE","ROLE_PRINT_TEMPLATE","ROLE_CACHE","ROLE_BUILD_HTML","ROLE_ADMIN","ROLE_ROLE","ROLE_MESSAGE","ROLE_LOG","ROLE_SETTING","ROLE_INSTANT_MESSAGING","ROLE_PAYMENT_CONFIG","ROLE_DELIVERY_TYPE","ROLE_AREA","ROLE_DELIVERY_CORP","ROLE_DELIVERY_CENTER","ROLE_DELIVERY_TEMPLATE","ROLE_BASE"]', '拥有后台管理最高权限', 1, '超级管理员');

INSERT INTO [{shopxx_table_prefix}admin] (id, create_date, modify_date, department, email, is_account_enabled, is_account_expired, is_account_locked, is_credentials_expired, locked_date, login_date, login_failure_count, login_ip, name, password, username) VALUES ('0731dcsoft2010031200000000000017', to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), '技术部', 'admin@shopxx.net', 1, 0, 0, 0, NULL, to_date('2011-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 0, '127.0.0.1', 'ADMIN', '{shopxx_admin_password}', '{shopxx_admin_username}');

INSERT INTO [{shopxx_table_prefix}admin_role] (admin_set_id, role_set_id) VALUES ('0731dcsoft2010031200000000000017', '0731dcsoft2010031200000000000016');