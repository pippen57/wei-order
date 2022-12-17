-- 用户表
CREATE TABLE biz_wx_user (
  id bigint NOT NULL COMMENT 'id',
  username varchar(50) NOT NULL COMMENT '用户名',
  mobile varchar(20) NOT NULL COMMENT '手机号',
  open_id varchar(64) COMMENT '密码',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE INDEX (open_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 用户Token表
CREATE TABLE biz_wx_token (
  id bigint NOT NULL COMMENT 'id',
  user_id bigint NOT NULL COMMENT '用户ID',
  token varchar(100) NOT NULL COMMENT 'token',
  expire_date datetime COMMENT '过期时间',
  update_date datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE INDEX (user_id),
  UNIQUE INDEX (token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户Token';
