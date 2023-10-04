package com.bs14.plugins

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UserService(private val database: Database) {
    object User : Table() {
        val id = uuid("id")
        val email = varchar("name", length = 256).isNotNull()
        val institute = varchar("institute", length = 256).isNotNull()
        val activated = bool("activated").default(false)
        val passwordHash = varchar("password_hash", length = 60).isNotNull()

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            //SchemaUtils.create(User)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
            newSuspendedTransaction(Dispatchers.IO) { block() }
    /*
            suspend fun create(user: ExposedUser): Int = dbQuery {
            val passwordHash = varchar("password_hash", length = 60).isNotNull()
                Users.insert {
                    it[name] = user.name
                    it[age] = user.age
                }[Users.id]
            }

            suspend fun read(id: Int): ExposedUser? {
                return dbQuery {
                    Users.select { Users.id eq id }
                            .map { ExposedUser(it[Users.name], it[Users.age]) }
                            .singleOrNull()
                }
            }

            suspend fun update(id: Int, user: ExposedUser) {
                dbQuery {
                    Users.update({ Users.id eq id }) {
                        it[name] = user.name
                        it[age] = user.age
                    }
                }
            }

            suspend fun delete(id: Int) {
                dbQuery {
                    Users.deleteWhere { Users.id.eq(id) }
                }
            }

         */
}
