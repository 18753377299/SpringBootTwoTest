 drop table if exists t_roles;
    CREATE TABLE
    t_roles
    (
        roleid INTEGER NOT NULL,
        rolename CHARACTER VARYING(30),
        CONSTRAINT t_roles_pkey PRIMARY KEY (roleid)
    );
    
 drop table if exists t_users; 
    CREATE TABLE
    t_users
    (
        name CHARACTER VARYING(20),
        age INTEGER,
        address CHARACTER VARYING(40),
        id INTEGER NOT NULL,
        roles_id INTEGER,
        inserttimeforhis TIME(6) WITHOUT TIME ZONE,
        operatetimeforhis TIME(6) WITHOUT TIME ZONE,
        CONSTRAINT t_users_pkey PRIMARY KEY (id),
        CONSTRAINT tusers_fk1 FOREIGN KEY (roles_id) REFERENCES "t_roles" ("roleid")
    );
    
CREATE TABLE "test_two" (
  "id" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(10) COLLATE "pg_catalog"."default",
  "password" varchar(20) COLLATE "pg_catalog"."default",
  CONSTRAINT "test_two_pkey" PRIMARY KEY ("id")
);
    
