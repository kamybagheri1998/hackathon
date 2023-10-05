import com.bs14.plugins.UserService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class LoanService(private val database: Database) {
    object LoanStatus : Table("loan_status") {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 12)

        override val primaryKey = PrimaryKey(id)
    }

    object Loan : Table() {
        val id = integer("id")
        val userid = reference("user_id", UserService.User.id)
        val createDate = datetime("create_date")
        val startDate = datetime("start_date")
        val endDate = datetime("end_date")
        val status = reference("status", LoanStatus.id)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(LoanStatus)

            LoanStatus.deleteAll()
            LoanStatus.insert {
                it[name] = "IN_PROCESS"
                it[id] = 1
            }
            LoanStatus.insert {
                it[name] = "ACCEPTED"
                it[id] = 2
            }
            LoanStatus.insert {
                it[name] = "DECLINED"
                it[id] = 3
            }

            SchemaUtils.create(Loan)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}