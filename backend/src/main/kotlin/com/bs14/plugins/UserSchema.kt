package com.bs14.plugins

import DeviceLoanService.DeviceLoan.varchar
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserService(private val database: Database) {
    object User : Table() {
        val id = uuid("id")
        val email = varchar("email", length = 256)
        val institute = varchar("institute", length = 256)
        val activated = bool("activated").default(false)
        val passwordHash = integer("password_hash")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            //SchemaUtils.create(User)
        }
    }



    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

        suspend fun create(userEmail: String, userInstitute: String, password:String): UUID = dbQuery {
            User.insert {
                it[id] = UUID.randomUUID()
                it[email] = userEmail
                it[institute] = userInstitute
                it[passwordHash] = password.hashCode()
            }[User.id]
        }

/*
            suspend fun read(id: UUID): ExposedUser? {
                return dbQuery {
                    User.select { User.id eq id }
                        .map { ExposedUser(it[User.email], it[User.institute]) }
                        .singleOrNull()
                }
            }*/

    /*
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
