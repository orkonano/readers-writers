databaseChangeLog = {

    changeSet(author: "orko (generated)", id: "1403280036929-1") {
        createTable(tableName: "facebook_user") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "facebook_userPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "access_token", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "access_token_expires", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-2") {
        createTable(tableName: "follower") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "followerPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "author_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "following_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-3") {
        createTable(tableName: "narrative_genre") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "narrative_genPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "parent_id", type: "bigint")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-4") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-5") {
        createTable(tableName: "telling") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tellingPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "author_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "narrative_genre_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "state", type: "integer")

            column(name: "telling_type_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "text", type: "longtext") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-6") {
        createTable(tableName: "telling_like") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "telling_likePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "reader_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "telling_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-7") {
        createTable(tableName: "telling_type") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "telling_typePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-8") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "facebook_id", type: "bigint")

            column(name: "firstname", type: "varchar(255)")

            column(name: "lastname", type: "varchar(255)")

            column(name: "password", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-9") {
        createTable(tableName: "user_role") {
            column(name: "role_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-10") {
        addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-22") {
        createIndex(indexName: "uid_uniq_1403280036769", tableName: "facebook_user", unique: "true") {
            column(name: "uid")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-23") {
        createIndex(indexName: "FK11FD201E41705030", tableName: "follower") {
            column(name: "author_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-24") {
        createIndex(indexName: "FK11FD201E805B996A", tableName: "follower") {
            column(name: "following_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-25") {
        createIndex(indexName: "unique_author_id", tableName: "follower", unique: "true") {
            column(name: "following_id")

            column(name: "author_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-26") {
        createIndex(indexName: "FK295D97769EE65997", tableName: "narrative_genre") {
            column(name: "parent_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-27") {
        createIndex(indexName: "name_uniq_1403280036787", tableName: "narrative_genre", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-28") {
        createIndex(indexName: "authority_uniq_1403280036791", tableName: "role", unique: "true") {
            column(name: "authority")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-29") {
        createIndex(indexName: "FKAAD0EA9141705030", tableName: "telling") {
            column(name: "author_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-30") {
        createIndex(indexName: "FKAAD0EA9180C76527", tableName: "telling") {
            column(name: "telling_type_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-31") {
        createIndex(indexName: "FKAAD0EA91E5DDC74B", tableName: "telling") {
            column(name: "narrative_genre_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-32") {
        createIndex(indexName: "FKE20D7085A7722DF8", tableName: "telling_like") {
            column(name: "reader_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-33") {
        createIndex(indexName: "FKE20D7085C9491844", tableName: "telling_like") {
            column(name: "telling_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-34") {
        createIndex(indexName: "unique_telling_id", tableName: "telling_like", unique: "true") {
            column(name: "reader_id")

            column(name: "telling_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-35") {
        createIndex(indexName: "name_uniq_1403280036798", tableName: "telling_type", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-36") {
        createIndex(indexName: "FK36EBCBFED1CEFB", tableName: "user") {
            column(name: "facebook_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-37") {
        createIndex(indexName: "username_uniq_1403280036802", tableName: "user", unique: "true") {
            column(name: "username")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-38") {
        createIndex(indexName: "FK143BF46A3B8C9A10", tableName: "user_role") {
            column(name: "role_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-39") {
        createIndex(indexName: "FK143BF46AE0B75DF0", tableName: "user_role") {
            column(name: "user_id")
        }
    }

    changeSet(author: "orko (generated)", id: "1403280036929-11") {
        addForeignKeyConstraint(baseColumnNames: "author_id", baseTableName: "follower", constraintName: "FK11FD201E41705030", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-12") {
        addForeignKeyConstraint(baseColumnNames: "following_id", baseTableName: "follower", constraintName: "FK11FD201E805B996A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-13") {
        addForeignKeyConstraint(baseColumnNames: "parent_id", baseTableName: "narrative_genre", constraintName: "FK295D97769EE65997", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "narrative_genre", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-14") {
        addForeignKeyConstraint(baseColumnNames: "author_id", baseTableName: "telling", constraintName: "FKAAD0EA9141705030", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-15") {
        addForeignKeyConstraint(baseColumnNames: "narrative_genre_id", baseTableName: "telling", constraintName: "FKAAD0EA91E5DDC74B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "narrative_genre", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-16") {
        addForeignKeyConstraint(baseColumnNames: "telling_type_id", baseTableName: "telling", constraintName: "FKAAD0EA9180C76527", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "telling_type", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-17") {
        addForeignKeyConstraint(baseColumnNames: "reader_id", baseTableName: "telling_like", constraintName: "FKE20D7085A7722DF8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-18") {
        addForeignKeyConstraint(baseColumnNames: "telling_id", baseTableName: "telling_like", constraintName: "FKE20D7085C9491844", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "telling", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-19") {
        addForeignKeyConstraint(baseColumnNames: "facebook_id", baseTableName: "user", constraintName: "FK36EBCBFED1CEFB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "facebook_user", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-20") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46A3B8C9A10", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "orko (generated)", id: "1403280036929-21") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK143BF46AE0B75DF0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }
}
