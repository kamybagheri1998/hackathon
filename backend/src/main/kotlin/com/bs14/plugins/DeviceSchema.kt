import com.bs14.plugins.UserService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class DeviceService(private val database: Database) {
    object DeviceType : Table("device_type") {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 12)

        override val primaryKey = PrimaryKey(id)
    }

    object Device : Table() {
        val id = uuid("id")
        val type = reference("type_id", DeviceType.id)
        val donor = varchar("donor", length = 256)
        val model = varchar("model", length = 256)
        val serialNumber = varchar("serialNumber", length = 256)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(DeviceType)

            DeviceType.deleteAll()
            DeviceType.insert {
                it[name] = "LAPTOP"
                it[id] = 1
            }
            DeviceType.insert {
                it[name] = "Tablet"
                it[id] = 2
            }
            DeviceType.insert {
                it[name] = "Desktop"
                it[id] = 3
            }

            SchemaUtils.create(Device)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}