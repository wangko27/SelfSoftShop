-- SHOP++ INIT SQL
-- http://www.shopxx.net
-- Copyright (c) 2011 shopxx.net All Rights Reserved.
-- Revision: 2.0.5
-- Date: 2011-05

-- 初始化数据库表结构 --

DROP TABLE IF EXISTS [{shopxx_table_prefix}area];
DROP TABLE IF EXISTS [{shopxx_table_prefix}friend_link];
DROP TABLE IF EXISTS [{shopxx_table_prefix}navigation];
DROP TABLE IF EXISTS [{shopxx_table_prefix}instant_messaging];
DROP TABLE IF EXISTS [{shopxx_table_prefix}leave_message];
DROP TABLE IF EXISTS [{shopxx_table_prefix}admin_role];
DROP TABLE IF EXISTS [{shopxx_table_prefix}admin];
DROP TABLE IF EXISTS [{shopxx_table_prefix}role];
DROP TABLE IF EXISTS [{shopxx_table_prefix}article];
DROP TABLE IF EXISTS [{shopxx_table_prefix}article_category];
DROP TABLE IF EXISTS [{shopxx_table_prefix}payment];
DROP TABLE IF EXISTS [{shopxx_table_prefix}refund];
DROP TABLE IF EXISTS [{shopxx_table_prefix}delivery_item];
DROP TABLE IF EXISTS [{shopxx_table_prefix}shipping];
DROP TABLE IF EXISTS [{shopxx_table_prefix}reship];
DROP TABLE IF EXISTS [{shopxx_table_prefix}deposit];
DROP TABLE IF EXISTS [{shopxx_table_prefix}receiver];
DROP TABLE IF EXISTS [{shopxx_table_prefix}cart_item];
DROP TABLE IF EXISTS [{shopxx_table_prefix}goods_notify];
DROP TABLE IF EXISTS [{shopxx_table_prefix}message];
DROP TABLE IF EXISTS [{shopxx_table_prefix}order_item];
DROP TABLE IF EXISTS [{shopxx_table_prefix}order_log];
DROP TABLE IF EXISTS [{shopxx_table_prefix}order];
DROP TABLE IF EXISTS [{shopxx_table_prefix}member_attribute];
DROP TABLE IF EXISTS [{shopxx_table_prefix}member_goods];
DROP TABLE IF EXISTS [{shopxx_table_prefix}member];
DROP TABLE IF EXISTS [{shopxx_table_prefix}member_rank];
DROP TABLE IF EXISTS [{shopxx_table_prefix}payment_config];
DROP TABLE IF EXISTS [{shopxx_table_prefix}comment];
DROP TABLE IF EXISTS [{shopxx_table_prefix}product];
DROP TABLE IF EXISTS [{shopxx_table_prefix}goods_specification];
DROP TABLE IF EXISTS [{shopxx_table_prefix}goods];
DROP TABLE IF EXISTS [{shopxx_table_prefix}specification];
DROP TABLE IF EXISTS [{shopxx_table_prefix}goods_attribute];
DROP TABLE IF EXISTS [{shopxx_table_prefix}goods_category];
DROP TABLE IF EXISTS [{shopxx_table_prefix}goods_type_brand];
DROP TABLE IF EXISTS [{shopxx_table_prefix}goods_type];
DROP TABLE IF EXISTS [{shopxx_table_prefix}brand];
DROP TABLE IF EXISTS [{shopxx_table_prefix}delivery_type];
DROP TABLE IF EXISTS [{shopxx_table_prefix}delivery_corp];
DROP TABLE IF EXISTS [{shopxx_table_prefix}delivery_center];
DROP TABLE IF EXISTS [{shopxx_table_prefix}delivery_template];
DROP TABLE IF EXISTS [{shopxx_table_prefix}log];

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}admin] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  department varchar(255) default NULL,
  email varchar(255) NOT NULL,
  is_account_enabled bit(1) NOT NULL,
  is_account_expired bit(1) NOT NULL,
  is_account_locked bit(1) NOT NULL,
  is_credentials_expired bit(1) NOT NULL,
  locked_date datetime default NULL,
  login_date datetime default NULL,
  login_failure_count int(11) NOT NULL,
  login_ip varchar(255) default NULL,
  name varchar(255) default NULL,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username (username)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}admin_role] (
  admin_set_id varchar(32) NOT NULL,
  role_set_id varchar(32) NOT NULL,
  PRIMARY KEY  (admin_set_id,role_set_id),
  KEY [{shopxx_table_prefix}role_admin_set] (role_set_id),
  KEY [{shopxx_table_prefix}admin_role_set] (admin_set_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}area] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  display_name text NOT NULL,
  grade int(11) NOT NULL,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  path text NOT NULL,
  parent_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}area_parent] (parent_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}article] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  author varchar(255) default NULL,
  content text NOT NULL,
  hits int(11) NOT NULL,
  html_path varchar(255) NOT NULL,
  is_publication bit(1) NOT NULL,
  is_recommend bit(1) NOT NULL,
  is_top bit(1) NOT NULL,
  meta_description text,
  meta_keywords text,
  page_count int(11) NOT NULL,
  title varchar(255) NOT NULL,
  article_category_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}article_article_category] (article_category_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}article_category] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  grade int(11) NOT NULL,
  meta_description text,
  meta_keywords text,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  path text NOT NULL,
  sign varchar(255) NOT NULL,
  parent_id varchar(32) default NULL,
  PRIMARY KEY (id),
  UNIQUE KEY sign (sign),
  KEY [{shopxx_table_prefix}article_category_parent] (parent_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}brand] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  introduction text,
  logo_path varchar(255) default NULL,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  url varchar(255) default NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}cart_item] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  quantity int(11) NOT NULL,
  product_id varchar(32) NOT NULL,
  member_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}cart_item_member] (member_id),
  KEY [{shopxx_table_prefix}cart_item_product] (product_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}comment] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  contact varchar(255) default NULL,
  content text NOT NULL,
  ip varchar(255) NOT NULL,
  is_admin_reply bit(1) NOT NULL,
  is_show bit(1) NOT NULL,
  username varchar(255) default NULL,
  goods_id varchar(32) NOT NULL,
  for_comment_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}comment_goods] (goods_id),
  KEY [{shopxx_table_prefix}comment_for_comment] (for_comment_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}delivery_center] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  address varchar(255) NOT NULL,
  area_store text NOT NULL,
  consignor varchar(255) NOT NULL,
  is_default bit(1) NOT NULL,
  memo varchar(255) default NULL,
  mobile varchar(255) default NULL,
  name varchar(255) NOT NULL,
  phone varchar(255) default NULL,
  zip_code varchar(255) default NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}delivery_corp] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  url varchar(255) default NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}delivery_item] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  delivery_quantity int(11) NOT NULL,
  goods_html_path varchar(255) NOT NULL,
  product_name varchar(255) NOT NULL,
  product_sn varchar(255) NOT NULL,
  reship_id varchar(32) default NULL,
  shipping_id varchar(32) default NULL,
  product_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}delivery_item_shipping] (shipping_id),
  KEY [{shopxx_table_prefix}delivery_item_reship] (reship_id),
  KEY [{shopxx_table_prefix}delivery_item_product] (product_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}delivery_template] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  content text NOT NULL,
  is_default bit(1) NOT NULL,
  memo varchar(255) default NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}delivery_type] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  continue_weight int(11) NOT NULL,
  continue_weight_price decimal(15,5) NOT NULL,
  delivery_method int(11) NOT NULL,
  description text,
  first_weight int(11) NOT NULL,
  first_weight_price decimal(15,5) NOT NULL,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  default_delivery_corp_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}delivery_item_delivery_corp] (default_delivery_corp_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}deposit] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  balance decimal(19,2) NOT NULL,
  credit decimal(19,2) NOT NULL,
  debit decimal(19,2) NOT NULL,
  deposit_type int(11) NOT NULL,
  member_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}deposit_member] (member_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}friend_link] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  logo_path varchar(255) default NULL,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  url varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}goods] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  cost decimal(15,5) default NULL,
  freeze_store int(11) NOT NULL,
  goods_attribute_value0 varchar(255) default NULL,
  goods_attribute_value1 varchar(255) default NULL,
  goods_attribute_value10 varchar(255) default NULL,
  goods_attribute_value11 varchar(255) default NULL,
  goods_attribute_value12 varchar(255) default NULL,
  goods_attribute_value13 varchar(255) default NULL,
  goods_attribute_value14 varchar(255) default NULL,
  goods_attribute_value15 varchar(255) default NULL,
  goods_attribute_value16 varchar(255) default NULL,
  goods_attribute_value17 varchar(255) default NULL,
  goods_attribute_value18 varchar(255) default NULL,
  goods_attribute_value19 varchar(255) default NULL,
  goods_attribute_value2 varchar(255) default NULL,
  goods_attribute_value3 varchar(255) default NULL,
  goods_attribute_value4 varchar(255) default NULL,
  goods_attribute_value5 varchar(255) default NULL,
  goods_attribute_value6 varchar(255) default NULL,
  goods_attribute_value7 varchar(255) default NULL,
  goods_attribute_value8 varchar(255) default NULL,
  goods_attribute_value9 varchar(255) default NULL,
  goods_image_store text,
  goods_parameter_value_store text,
  goods_sn varchar(255) NOT NULL,
  html_path varchar(255) NOT NULL,
  introduction text,
  is_best bit(1) NOT NULL,
  is_hot bit(1) NOT NULL,
  is_marketable bit(1) NOT NULL,
  is_new bit(1) NOT NULL,
  is_specification_enabled bit(1) NOT NULL,
  market_price decimal(15,5) NOT NULL,
  meta_description text,
  meta_keywords text,
  name varchar(255) NOT NULL,
  price decimal(15,5) NOT NULL,
  score int(11) NOT NULL,
  store int(11) default NULL,
  store_place varchar(255) default NULL,
  weight int(11) NOT NULL,
  goods_type_id varchar(32) default NULL,
  brand_id varchar(32) default NULL,
  goods_category_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY goods_sn (goods_sn),
  KEY [{shopxx_table_prefix}goods_goods_category] (goods_category_id),
  KEY [{shopxx_table_prefix}goods_goods_type] (goods_type_id),
  KEY [{shopxx_table_prefix}goods_brand] (brand_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}goods_attribute] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  attribute_type int(11) NOT NULL,
  name varchar(255) NOT NULL,
  option_store varchar(255) default NULL,
  order_list int(11) default NULL,
  property_index int(11) NOT NULL,
  goods_type_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}goods_attribute_goods_type] (goods_type_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}goods_category] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  grade int(11) NOT NULL,
  meta_description text,
  meta_keywords text,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  path text NOT NULL,
  sign varchar(255) NOT NULL,
  goods_type_id varchar(32) default NULL,
  parent_id varchar(32) default NULL,
  PRIMARY KEY (id),
  UNIQUE KEY sign (sign),
  KEY [{shopxx_table_prefix}goods_category_goods_type] (goods_type_id),
  KEY [{shopxx_table_prefix}goods_category_parent] (parent_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}goods_notify] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  email varchar(255) NOT NULL,
  is_sent bit(1) NOT NULL,
  send_date datetime default NULL,
  product_id varchar(32) NOT NULL,
  member_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}goods_notify_member] (member_id),
  KEY [{shopxx_table_prefix}goods_notify_product] (product_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}goods_specification] (
  goods_set_id varchar(32) NOT NULL,
  specification_set_id varchar(32) NOT NULL,
  PRIMARY KEY  (goods_set_id,specification_set_id),
  KEY [{shopxx_table_prefix}specification_set] (goods_set_id),
  KEY [{shopxx_table_prefix}specification_goods_set] (specification_set_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}goods_type] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  goods_parameter_store text,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}goods_type_brand] (
  goods_type_set_id varchar(32) NOT NULL,
  brand_set_id varchar(32) NOT NULL,
  PRIMARY KEY  (goods_type_set_id,brand_set_id),
  KEY [{shopxx_table_prefix}goods_type_brand_set] (goods_type_set_id),
  KEY [{shopxx_table_prefix}brand_goods_type_set] (brand_set_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}instant_messaging] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  instant_messaging_type int(11) NOT NULL,
  order_list int(11) default NULL,
  title varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}leave_message] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  contact varchar(255) default NULL,
  content text NOT NULL,
  ip varchar(255) NOT NULL,
  title varchar(255) NOT NULL,
  username varchar(255) default NULL,
  for_leave_message_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}leave_message_for_message] (for_leave_message_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}log] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  action_class varchar(255) NOT NULL,
  action_method varchar(255) NOT NULL,
  info text,
  ip varchar(255) NOT NULL,
  operation varchar(255) NOT NULL,
  operator varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}member] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  address varchar(255) default NULL,
  area_store varchar(255) default NULL,
  birth datetime default NULL,
  deposit decimal(15,5) NOT NULL,
  email varchar(255) NOT NULL,
  gender int(11) default NULL,
  is_account_enabled bit(1) NOT NULL,
  is_account_locked bit(1) NOT NULL,
  locked_date datetime default NULL,
  login_date datetime default NULL,
  login_failure_count int(11) NOT NULL,
  login_ip varchar(255) default NULL,
  member_attribute_value0 varchar(255) default NULL,
  member_attribute_value1 varchar(255) default NULL,
  member_attribute_value10 varchar(255) default NULL,
  member_attribute_value11 varchar(255) default NULL,
  member_attribute_value12 varchar(255) default NULL,
  member_attribute_value13 varchar(255) default NULL,
  member_attribute_value14 varchar(255) default NULL,
  member_attribute_value15 varchar(255) default NULL,
  member_attribute_value16 varchar(255) default NULL,
  member_attribute_value17 varchar(255) default NULL,
  member_attribute_value18 varchar(255) default NULL,
  member_attribute_value19 varchar(255) default NULL,
  member_attribute_value2 varchar(255) default NULL,
  member_attribute_value3 varchar(255) default NULL,
  member_attribute_value4 varchar(255) default NULL,
  member_attribute_value5 varchar(255) default NULL,
  member_attribute_value6 varchar(255) default NULL,
  member_attribute_value7 varchar(255) default NULL,
  member_attribute_value8 varchar(255) default NULL,
  member_attribute_value9 varchar(255) default NULL,
  mobile varchar(255) default NULL,
  name varchar(255) default NULL,
  password varchar(255) NOT NULL,
  password_recover_key varchar(255) default NULL,
  phone varchar(255) default NULL,
  register_ip varchar(255) NOT NULL,
  safe_answer varchar(255) default NULL,
  safe_question varchar(255) default NULL,
  score int(11) NOT NULL,
  username varchar(255) NOT NULL,
  zip_code varchar(255) default NULL,
  member_rank_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username (username),
  KEY [{shopxx_table_prefix}member_member_rank] (member_rank_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}member_attribute] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  attribute_type int(11) default NULL,
  is_enabled bit(1) NOT NULL,
  is_required bit(1) NOT NULL,
  name varchar(255) NOT NULL,
  option_store varchar(255) default NULL,
  order_list int(11) default NULL,
  property_index int(11) NOT NULL,
  system_attribute_type int(11) default NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}member_goods] (
  favorite_member_set_id varchar(32) NOT NULL,
  favorite_goods_set_id varchar(32) NOT NULL,
  PRIMARY KEY  (favorite_member_set_id,favorite_goods_set_id),
  KEY [{shopxx_table_prefix}member_favorite_goods_set] (favorite_member_set_id),
  KEY [{shopxx_table_prefix}goods_favorite_member_set] (favorite_goods_set_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}member_rank] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  is_default bit(1) NOT NULL,
  name varchar(255) NOT NULL,
  preferential_scale double NOT NULL,
  score int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  UNIQUE KEY score (score)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}message] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  content text NOT NULL,
  delete_status int(11) NOT NULL,
  is_read bit(1) NOT NULL,
  is_save_draftbox bit(1) NOT NULL,
  title varchar(255) NOT NULL,
  from_member_id varchar(32) default NULL,
  to_member_id varchar(32) default NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}message_from_member] (from_member_id),
  KEY [{shopxx_table_prefix}message_to_member] (to_member_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}navigation] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  is_blank_target bit(1) NOT NULL,
  is_visible bit(1) NOT NULL,
  name varchar(255) NOT NULL,
  navigation_position int(11) NOT NULL,
  order_list int(11) default NULL,
  url varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}order] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  delivery_fee decimal(15,5) NOT NULL,
  delivery_type_name varchar(255) NOT NULL,
  goods_id_list_store text,
  memo text,
  order_sn varchar(255) NOT NULL,
  order_status int(11) NOT NULL,
  paid_amount decimal(15,5) NOT NULL,
  payment_config_name varchar(255) NOT NULL,
  payment_fee decimal(15,5) NOT NULL,
  payment_status int(11) NOT NULL,
  ship_address varchar(255) NOT NULL,
  ship_area_store text NOT NULL,
  ship_mobile varchar(255) default NULL,
  ship_name varchar(255) NOT NULL,
  ship_phone varchar(255) default NULL,
  ship_zip_code varchar(255) NOT NULL,
  shipping_status int(11) NOT NULL,
  total_amount decimal(15,5) NOT NULL,
  total_product_price decimal(15,5) NOT NULL,
  total_product_quantity int(11) NOT NULL,
  total_product_weight int(11) NOT NULL,
  member_id varchar(32) default NULL,
  delivery_type_id varchar(32) default NULL,
  payment_config_id varchar(32) default NULL,
  PRIMARY KEY (id),
  UNIQUE KEY order_sn (order_sn),
  KEY [{shopxx_table_prefix}order_delivery_type] (delivery_type_id),
  KEY [{shopxx_table_prefix}order_member] (member_id),
  KEY [{shopxx_table_prefix}order_payment_config] (payment_config_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}order_item] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  delivery_quantity int(11) NOT NULL,
  goods_html_path varchar(255) NOT NULL,
  product_name varchar(255) NOT NULL,
  product_price decimal(15,5) NOT NULL,
  product_quantity int(11) NOT NULL,
  product_sn varchar(255) NOT NULL,
  product_id varchar(32) default NULL,
  order_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}order_item_order] (order_id),
  KEY [{shopxx_table_prefix}order_item_product] (product_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}order_log] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  info text,
  operator varchar(255) default NULL,
  order_log_type int(11) NOT NULL,
  order_sn varchar(255) NOT NULL,
  order_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}order_log_order] (order_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}payment] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  bank_account varchar(255) default NULL,
  bank_name varchar(255) default NULL,
  memo text,
  operator varchar(255) default NULL,
  payer varchar(255) default NULL,
  payment_config_name varchar(255) NOT NULL,
  payment_fee decimal(15,5) NOT NULL,
  payment_sn varchar(255) NOT NULL,
  payment_status int(11) NOT NULL,
  payment_type int(11) NOT NULL,
  total_amount decimal(15,5) NOT NULL,
  member_id varchar(32) default NULL,
  order_id varchar(32) default NULL,
  payment_config_id varchar(32) default NULL,
  deposit_id varchar(32) default NULL,
  PRIMARY KEY (id),
  UNIQUE KEY payment_sn (payment_sn),
  KEY [{shopxx_table_prefix}payment_member] (member_id),
  KEY [{shopxx_table_prefix}payment_deposit] (deposit_id),
  KEY [{shopxx_table_prefix}payment_payment_config] (payment_config_id),
  KEY [{shopxx_table_prefix}payment_order] (order_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}payment_config] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  bargainor_id varchar(255) default NULL,
  bargainor_key varchar(255) default NULL,
  description text,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  payment_config_type int(11) NOT NULL,
  payment_fee decimal(15,5) NOT NULL,
  payment_fee_type int(11) NOT NULL,
  payment_product_id varchar(255) default NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}product] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  cost decimal(15,5) default NULL,
  freeze_store int(11) NOT NULL,
  is_default bit(1) NOT NULL,
  is_marketable bit(1) NOT NULL,
  market_price decimal(15,5) NOT NULL,
  name varchar(255) NOT NULL,
  price decimal(15,5) NOT NULL,
  product_sn varchar(255) NOT NULL,
  specification_value_store text,
  store int(11) default NULL,
  store_place varchar(255) default NULL,
  weight int(11) NOT NULL,
  goods_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY product_sn (product_sn),
  KEY [{shopxx_table_prefix}product_goods] (goods_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}receiver] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  address text NOT NULL,
  area_store text NOT NULL,
  is_default bit(1) NOT NULL,
  mobile varchar(255) default NULL,
  name varchar(255) NOT NULL,
  phone varchar(255) default NULL,
  zip_code varchar(255) NOT NULL,
  member_id varchar(32) NOT NULL,
  PRIMARY KEY (id),
  KEY [{shopxx_table_prefix}receiver_member] (member_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}refund] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  bank_account varchar(255) default NULL,
  bank_name varchar(255) default NULL,
  memo text,
  operator varchar(255) default NULL,
  payee varchar(255) default NULL,
  payment_config_name varchar(255) NOT NULL,
  refund_sn varchar(255) NOT NULL,
  refund_type int(11) NOT NULL,
  total_amount decimal(15,5) NOT NULL,
  order_id varchar(32) default NULL,
  payment_config_id varchar(32) default NULL,
  deposit_id varchar(32) default NULL,
  PRIMARY KEY (id),
  UNIQUE KEY refund_sn (refund_sn),
  KEY [{shopxx_table_prefix}refund_deposit] (deposit_id),
  KEY [{shopxx_table_prefix}refund_payment_config] (payment_config_id),
  KEY [{shopxx_table_prefix}refund_order] (order_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}reship] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  delivery_corp_name varchar(255) NOT NULL,
  delivery_fee decimal(19,2) NOT NULL,
  delivery_sn varchar(255) default NULL,
  delivery_type_name varchar(255) NOT NULL,
  memo varchar(255) default NULL,
  reship_address varchar(255) NOT NULL,
  reship_area_store text NOT NULL,
  reship_mobile varchar(255) default NULL,
  reship_name varchar(255) NOT NULL,
  reship_phone varchar(255) default NULL,
  reship_sn varchar(255) NOT NULL,
  reship_zip_code varchar(255) NOT NULL,
  delivery_type_id varchar(32) default NULL,
  order_id varchar(32) default NULL,
  PRIMARY KEY (id),
  UNIQUE KEY reship_sn (reship_sn),
  KEY [{shopxx_table_prefix}reship_delivery_type] (delivery_type_id),
  KEY [{shopxx_table_prefix}reship_order] (order_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}role] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  authority_list_store text,
  description text,
  is_system bit(1) NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}shipping] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  delivery_corp_name varchar(255) NOT NULL,
  delivery_corp_url varchar(255) default NULL,
  delivery_fee decimal(19,2) NOT NULL,
  delivery_sn varchar(255) default NULL,
  delivery_type_name varchar(255) NOT NULL,
  memo varchar(255) default NULL,
  ship_address varchar(255) NOT NULL,
  ship_area_store text NOT NULL,
  ship_mobile varchar(255) default NULL,
  ship_name varchar(255) NOT NULL,
  ship_phone varchar(255) default NULL,
  ship_zip_code varchar(255) NOT NULL,
  shipping_sn varchar(255) NOT NULL,
  delivery_type_id varchar(32) default NULL,
  order_id varchar(32) default NULL,
  PRIMARY KEY (id),
  UNIQUE KEY shipping_sn (shipping_sn),
  KEY [{shopxx_table_prefix}shipping_delivery_type] (delivery_type_id),
  KEY [{shopxx_table_prefix}shipping_order] (order_id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS [{shopxx_table_prefix}specification] (
  id varchar(32) NOT NULL,
  create_date datetime default NULL,
  modify_date datetime default NULL,
  memo varchar(255) default NULL,
  name varchar(255) NOT NULL,
  order_list int(11) default NULL,
  specification_type int(11) NOT NULL,
  specification_value_store text NOT NULL,
  PRIMARY KEY (id)
) ENGINE={shopxx_engine} DEFAULT CHARSET=utf8;

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

ALTER TABLE [{shopxx_table_prefix}admin_role]
  ADD CONSTRAINT [{shopxx_table_prefix}admin_role_set] FOREIGN KEY (admin_set_id) REFERENCES [{shopxx_table_prefix}admin] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}role_admin_set] FOREIGN KEY (role_set_id) REFERENCES [{shopxx_table_prefix}role] (id);

ALTER TABLE [{shopxx_table_prefix}area]
  ADD CONSTRAINT [{shopxx_table_prefix}area_parent] FOREIGN KEY (parent_id) REFERENCES [{shopxx_table_prefix}area] (id);

ALTER TABLE [{shopxx_table_prefix}article]
  ADD CONSTRAINT [{shopxx_table_prefix}article_article_category] FOREIGN KEY (article_category_id) REFERENCES [{shopxx_table_prefix}article_category] (id);

ALTER TABLE [{shopxx_table_prefix}article_category]
  ADD CONSTRAINT [{shopxx_table_prefix}article_category_parent] FOREIGN KEY (parent_id) REFERENCES [{shopxx_table_prefix}article_category] (id);

ALTER TABLE [{shopxx_table_prefix}cart_item]
  ADD CONSTRAINT [{shopxx_table_prefix}cart_item_product] FOREIGN KEY (product_id) REFERENCES [{shopxx_table_prefix}product] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}cart_item_member] FOREIGN KEY (member_id) REFERENCES [{shopxx_table_prefix}member] (id);

ALTER TABLE [{shopxx_table_prefix}comment]
  ADD CONSTRAINT [{shopxx_table_prefix}comment_for_comment] FOREIGN KEY (for_comment_id) REFERENCES [{shopxx_table_prefix}comment] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}comment_goods] FOREIGN KEY (goods_id) REFERENCES [{shopxx_table_prefix}goods] (id);

ALTER TABLE [{shopxx_table_prefix}delivery_item]
  ADD CONSTRAINT [{shopxx_table_prefix}delivery_item_product] FOREIGN KEY (product_id) REFERENCES [{shopxx_table_prefix}product] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}delivery_item_reship] FOREIGN KEY (reship_id) REFERENCES [{shopxx_table_prefix}reship] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}delivery_item_shipping] FOREIGN KEY (shipping_id) REFERENCES [{shopxx_table_prefix}shipping] (id);

ALTER TABLE [{shopxx_table_prefix}delivery_type]
  ADD CONSTRAINT [{shopxx_table_prefix}delivery_item_delivery_corp] FOREIGN KEY (default_delivery_corp_id) REFERENCES [{shopxx_table_prefix}delivery_corp] (id);

ALTER TABLE [{shopxx_table_prefix}deposit]
  ADD CONSTRAINT [{shopxx_table_prefix}deposit_member] FOREIGN KEY (member_id) REFERENCES [{shopxx_table_prefix}member] (id);

ALTER TABLE [{shopxx_table_prefix}goods]
  ADD CONSTRAINT [{shopxx_table_prefix}goods_brand] FOREIGN KEY (brand_id) REFERENCES [{shopxx_table_prefix}brand] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}goods_goods_category] FOREIGN KEY (goods_category_id) REFERENCES [{shopxx_table_prefix}goods_category] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}goods_goods_type] FOREIGN KEY (goods_type_id) REFERENCES [{shopxx_table_prefix}goods_type] (id);

ALTER TABLE [{shopxx_table_prefix}goods_attribute]
  ADD CONSTRAINT [{shopxx_table_prefix}goods_attribute_goods_type] FOREIGN KEY (goods_type_id) REFERENCES [{shopxx_table_prefix}goods_type] (id);

ALTER TABLE [{shopxx_table_prefix}goods_category]
  ADD CONSTRAINT [{shopxx_table_prefix}goods_category_parent] FOREIGN KEY (parent_id) REFERENCES [{shopxx_table_prefix}goods_category] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}goods_category_goods_type] FOREIGN KEY (goods_type_id) REFERENCES [{shopxx_table_prefix}goods_type] (id);

ALTER TABLE [{shopxx_table_prefix}goods_notify]
  ADD CONSTRAINT [{shopxx_table_prefix}goods_notify_product] FOREIGN KEY (product_id) REFERENCES [{shopxx_table_prefix}product] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}goods_notify_member] FOREIGN KEY (member_id) REFERENCES [{shopxx_table_prefix}member] (id);

ALTER TABLE [{shopxx_table_prefix}goods_specification]
  ADD CONSTRAINT [{shopxx_table_prefix}specification_goods_set] FOREIGN KEY (specification_set_id) REFERENCES [{shopxx_table_prefix}specification] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}specification_set] FOREIGN KEY (goods_set_id) REFERENCES [{shopxx_table_prefix}goods] (id);

ALTER TABLE [{shopxx_table_prefix}goods_type_brand]
  ADD CONSTRAINT [{shopxx_table_prefix}brand_goods_type_set] FOREIGN KEY (brand_set_id) REFERENCES [{shopxx_table_prefix}brand] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}goods_type_brand_set] FOREIGN KEY (goods_type_set_id) REFERENCES [{shopxx_table_prefix}goods_type] (id);

ALTER TABLE [{shopxx_table_prefix}leave_message]
  ADD CONSTRAINT [{shopxx_table_prefix}leave_message_for_message] FOREIGN KEY (for_leave_message_id) REFERENCES [{shopxx_table_prefix}leave_message] (id);

ALTER TABLE [{shopxx_table_prefix}member]
  ADD CONSTRAINT [{shopxx_table_prefix}member_member_rank] FOREIGN KEY (member_rank_id) REFERENCES [{shopxx_table_prefix}member_rank] (id);

ALTER TABLE [{shopxx_table_prefix}member_goods]
  ADD CONSTRAINT [{shopxx_table_prefix}goods_favorite_member_set] FOREIGN KEY (favorite_goods_set_id) REFERENCES [{shopxx_table_prefix}goods] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}member_favorite_goods_set] FOREIGN KEY (favorite_member_set_id) REFERENCES [{shopxx_table_prefix}member] (id);

ALTER TABLE [{shopxx_table_prefix}message]
  ADD CONSTRAINT [{shopxx_table_prefix}message_to_member] FOREIGN KEY (to_member_id) REFERENCES [{shopxx_table_prefix}member] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}message_from_member] FOREIGN KEY (from_member_id) REFERENCES [{shopxx_table_prefix}member] (id);

ALTER TABLE [{shopxx_table_prefix}order]
  ADD CONSTRAINT [{shopxx_table_prefix}order_payment_config] FOREIGN KEY (payment_config_id) REFERENCES [{shopxx_table_prefix}payment_config] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}order_delivery_type] FOREIGN KEY (delivery_type_id) REFERENCES [{shopxx_table_prefix}delivery_type] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}order_member] FOREIGN KEY (member_id) REFERENCES [{shopxx_table_prefix}member] (id);

ALTER TABLE [{shopxx_table_prefix}order_item]
  ADD CONSTRAINT [{shopxx_table_prefix}order_item_product] FOREIGN KEY (product_id) REFERENCES [{shopxx_table_prefix}product] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}order_item_order] FOREIGN KEY (order_id) REFERENCES [{shopxx_table_prefix}order] (id);

ALTER TABLE [{shopxx_table_prefix}order_log]
  ADD CONSTRAINT [{shopxx_table_prefix}order_log_order] FOREIGN KEY (order_id) REFERENCES [{shopxx_table_prefix}order] (id);

ALTER TABLE [{shopxx_table_prefix}payment]
  ADD CONSTRAINT [{shopxx_table_prefix}payment_order] FOREIGN KEY (order_id) REFERENCES [{shopxx_table_prefix}order] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}payment_deposit] FOREIGN KEY (deposit_id) REFERENCES [{shopxx_table_prefix}deposit] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}payment_member] FOREIGN KEY (member_id) REFERENCES [{shopxx_table_prefix}member] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}payment_payment_config] FOREIGN KEY (payment_config_id) REFERENCES [{shopxx_table_prefix}payment_config] (id);

ALTER TABLE [{shopxx_table_prefix}product]
  ADD CONSTRAINT [{shopxx_table_prefix}product_goods] FOREIGN KEY (goods_id) REFERENCES [{shopxx_table_prefix}goods] (id);

ALTER TABLE [{shopxx_table_prefix}receiver]
  ADD CONSTRAINT [{shopxx_table_prefix}receiver_member] FOREIGN KEY (member_id) REFERENCES [{shopxx_table_prefix}member] (id);

ALTER TABLE [{shopxx_table_prefix}refund]
  ADD CONSTRAINT [{shopxx_table_prefix}refund_order] FOREIGN KEY (order_id) REFERENCES [{shopxx_table_prefix}order] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}refund_deposit] FOREIGN KEY (deposit_id) REFERENCES [{shopxx_table_prefix}deposit] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}refund_payment_config] FOREIGN KEY (payment_config_id) REFERENCES [{shopxx_table_prefix}payment_config] (id);

ALTER TABLE [{shopxx_table_prefix}reship]
  ADD CONSTRAINT [{shopxx_table_prefix}reship_order] FOREIGN KEY (order_id) REFERENCES [{shopxx_table_prefix}order] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}reship_delivery_type] FOREIGN KEY (delivery_type_id) REFERENCES [{shopxx_table_prefix}delivery_type] (id);

ALTER TABLE [{shopxx_table_prefix}shipping]
  ADD CONSTRAINT [{shopxx_table_prefix}shipping_order] FOREIGN KEY (order_id) REFERENCES [{shopxx_table_prefix}order] (id),
  ADD CONSTRAINT [{shopxx_table_prefix}shipping_delivery_type] FOREIGN KEY (delivery_type_id) REFERENCES [{shopxx_table_prefix}delivery_type] (id);

-- 初始化数据库基本数据 --

INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000001', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '姓名', NULL, 1, 0, 0);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000002', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '性别', NULL, 2, 1, 1);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000003', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '出生日期', NULL, 3, 2, 2);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000004', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '地区', NULL, 4, 3, 3);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000005', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '地址', NULL, 5, 4, 4);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000006', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '邮编', NULL, 6, 5, 5);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000007', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '电话', NULL, 7, 6, 6);
INSERT INTO [{shopxx_table_prefix}member_attribute] (id, create_date, modify_date, attribute_type, is_enabled, is_required, name, option_store, order_list, property_index, system_attribute_type) VALUES('0731dcsoft2010031200000000000008', '2011-01-01 00:00:00', '2011-01-01 00:00:00', 0, b'1', b'0', '手机', NULL, 8, 7, 7);

INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('0731dcsoft2010031200000000000010', '2011-01-01 00:00:00', '2011-01-01 00:00:00', b'1', '普通会员', 100, 0);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d08bed0001', '2011-01-01 00:00:00', '2011-01-01 00:00:00', b'0', '一级会员', 98, 2000);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d0bf800002', '2011-01-01 00:00:00', '2011-01-01 00:00:00', b'0', '二级会员', 95, 5000);
INSERT INTO [{shopxx_table_prefix}member_rank] (id, create_date, modify_date, is_default, name, preferential_scale, score) VALUES('402881833054c381013054d103ec0003', '2011-01-01 00:00:00', '2011-01-01 00:00:00', b'0', '三级会员', 90, 10000);

INSERT INTO [{shopxx_table_prefix}role] (id, create_date, modify_date, authority_list_store, description, is_system, name) VALUES ('0731dcsoft2010031200000000000016', '2011-01-01 00:00:00', '2011-01-01 00:00:00', '["ROLE_GOODS","ROLE_GOODS_NOTIFY","ROLE_GOODS_CATEGORY","ROLE_GOODS_TYPE","ROLE_SPECIFICATION","ROLE_BRAND","ROLE_ORDER","ROLE_PAYMENT","ROLE_REFUND","ROLE_SHIPPING","ROLE_RESHIP","ROLE_MEMBER","ROLE_MEMBER_RANK","ROLE_MEMBER_ATTRIBUTE","ROLE_COMMENT","ROLE_LEAVE_MESSAGE","ROLE_NAVIGATION","ROLE_ARTICLEE","ROLE_ARTICLE_CATEGORY","ROLE_FRIEND_LINK","ROLE_PAGE_TEMPLATE","ROLE_MAIL_TEMPLATE","ROLE_PRINT_TEMPLATE","ROLE_CACHE","ROLE_BUILD_HTML","ROLE_ADMIN","ROLE_ROLE","ROLE_MESSAGE","ROLE_LOG","ROLE_SETTING","ROLE_INSTANT_MESSAGING","ROLE_PAYMENT_CONFIG","ROLE_DELIVERY_TYPE","ROLE_AREA","ROLE_DELIVERY_CORP","ROLE_DELIVERY_CENTER","ROLE_DELIVERY_TEMPLATE","ROLE_BASE"]', '拥有后台管理最高权限', b'1', '超级管理员');

INSERT INTO [{shopxx_table_prefix}admin] (id, create_date, modify_date, department, email, is_account_enabled, is_account_expired, is_account_locked, is_credentials_expired, locked_date, login_date, login_failure_count, login_ip, name, password, username) VALUES ('0731dcsoft2010031200000000000017', '2011-01-01 00:00:00', '2011-01-01 00:00:00', '技术部', 'admin@shopxx.net', b'1', b'0', b'0', b'0', NULL, '2011-01-01 00:00:00', 0, '127.0.0.1', 'ADMIN', '{shopxx_admin_password}', '{shopxx_admin_username}');

INSERT INTO [{shopxx_table_prefix}admin_role] (admin_set_id, role_set_id) VALUES ('0731dcsoft2010031200000000000017', '0731dcsoft2010031200000000000016');