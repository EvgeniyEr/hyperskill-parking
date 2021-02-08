package parking

fun main() {
    while (true) {
        val userCommand = readLine()!!.split(" ")
        val command = ParkingLot.commandProcessing(userCommand)
        if (command == ParkingLot.Commands.EXIT) {
            return
        }
    }
}

class Car(val registrationNumber: String = "", val color: String = "")

object ParkingLot {

    enum class Commands {
        PARK,
        LEAVE,
        EXIT,
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

    // Изначально паркова на 20 мест пустая
    val places: Array<Car?> = Array(20) { i -> null }

    fun commandProcessing(userCommand: List<String>): Commands {
        val command = Commands.getByName(userCommand[0])
        when (command) {
            Commands.PARK -> {
                val car = Car(userCommand[1], userCommand[2])
                var isCarParked = false
                for (i in places.indices) {
                    if (places[i] == null) {
                        places[i] = car
                        println("${car.color} car parked in spot ${i + 1}.")
                        isCarParked = true
                        break
                    }
                }
                if (!isCarParked) {
                    println("Sorry, the parking lot is full.")
                }
            }
            Commands.LEAVE -> {
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
        return command
    }
}