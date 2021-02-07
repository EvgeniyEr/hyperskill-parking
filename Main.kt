package parking

fun main() {
    val userCommand = readLine()!!.split(" ")
    ParkingLot.commandProcessing(userCommand)
}

class Car(val registrationNumber: String = "", val color: String = "")

object ParkingLot {

    enum class Commands {
        PARK,
        LEAVE,
        NULL;

        companion object {
            fun getByName(name: String): Commands {
                for (enum in values()) {
                    if (name.toUpperCase() == enum.name) return enum
                }
                return NULL
            }
        }
    }

    // На первом месте уже стоит машина
    val places = arrayOf(Car(), null)

    fun commandProcessing(userCommand: List<String>) {
        val command = Commands.getByName(userCommand[0])
        if (command == Commands.PARK) {
            val car = Car(userCommand[1], userCommand[2])
            for (i in places.indices) {
                if (places[i] == null) {
                    places[i] = car
                    println("${car.color} car parked in spot ${i + 1}.")
                }
            }
        } else if (command == Commands.LEAVE) {
            val numPlace = userCommand[1].toInt()
            if (places.size > numPlace - 1) {
                if (places[numPlace - 1] != null) {
                    places[numPlace - 1] = null
                    println("Spot $numPlace is free.")
                } else {
                    println("There is no car in spot $numPlace.")
                }
            }
        }
    }
}