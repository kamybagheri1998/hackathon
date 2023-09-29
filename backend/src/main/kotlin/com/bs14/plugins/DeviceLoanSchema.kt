import com.bs14.plugins.UserService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class DeviceLoanService(private val database: Database) {

    object DeviceLoan : Table() {
        val id = uuid("id")
        val loanId = reference("loan_id", LoanService.Loan.id)
        val deviceId = reference("device_id", DeviceService.Device.id)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(DeviceLoan)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}